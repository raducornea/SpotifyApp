# python -m pip install lxml spyne
from spyne import Application, rpc, ServiceBase, Boolean, Integer, Double, String, Iterable
from spyne.protocol.soap import Soap11
from spyne.server.wsgi import WsgiApplication

import repositories.user_repository as users_table
import repositories.role_repository as roles_table
import repositories.users_roles_repository as users_roles_table

from jwt_operations import JWTOperations


class IDMService(ServiceBase):
    """
    @rpc -> definim tipuri de data pe care le avem
    in xml sunt input si output pentru asta
    """

    # CREATE
    @rpc(String, String, String, _returns=String)
    def create_user(ctx, username, password, token):
        allowed_roles = [0]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": return tests_result

        # processings
        return users_table.create_user(username, password)

    # READ
    @rpc(String, _returns=Iterable(String))
    def get_usersnames(ctx, token):
        allowed_roles = [4]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": yield tests_result

        users = users_table.get_users()
        for user in users:
            yield user.username

        if len(users) == 0:
            yield "NO USERS"

    @rpc(Integer, String, _returns=String)
    def get_user_by_id(ctx, id, token):
        allowed_roles = [4]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": return tests_result

        user = users_table.get_user_by_id(id)
        if user == "User does not exist!":
            return "User does not exist!"
        return f"id={user.id}~username={user.username}~password={user.password}"

    @rpc(String, String, _returns=String)
    def get_user_by_username(ctx, username, token):
        allowed_roles = [4]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": return tests_result

        user = users_table.get_user_by_username(username)
        if user == "User does not exist!":
            return "User does not exist!"
        return f"id={user.id}~username={user.username}~password={user.password}"

    # UPDATE
    @rpc(Integer, String, String, _returns=String)
    def update_username(ctx, id, new_name, token):
        allowed_roles = [1, 4]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": return tests_result

        return users_table.update_username(id, new_name)

    @rpc(Integer, String, String, _returns=String)
    def update_password(ctx, id, new_password, token):
        allowed_roles = [1, 4]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": return tests_result

        return users_table.update_password(id, new_password)

    # DELETE
    @rpc(Integer, String, _returns=String)
    def delete_user_by_id(ctx, id, token):
        allowed_roles = [1, 4]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": return tests_result

        return users_table.delete_user_by_id(id)

    @rpc(String, String, _returns=String)
    def delete_user_by_username(ctx, username, token):
        allowed_roles = [1, 4]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": return tests_result

        return users_table.delete_user_by_username(username)

    # ********************************************************
    # CREATE
    @rpc(Integer, Integer, String, _returns=String)
    def create_user_role(ctx, user_id, role_id, token):
        allowed_roles = [4]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": return tests_result

        return users_roles_table.create_user_role(user_id, role_id)

    # READ
    @rpc(Integer, String, _returns=Iterable(Integer))
    def get_user_roles(ctx, user_id, token):
        allowed_roles = [4]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": yield tests_result

        roles = list(users_roles_table.get_user_roles(user_id))
        for role in roles:
            yield role[1]  # 3
            # print(role)  # (170, 3)

        if len(roles) == 0:
            yield -1  # f"NO ROLES FOR USER WITH ID {user_id}"

    # UPDATE
    @rpc(Integer, Integer, Integer, String, _returns=String)
    def update_user_role(ctx, user_id, old_role_id, new_role_id, token):
        allowed_roles = [4]

        # validate token and role
        tests_result = JWTOperations.test_token_roles(token, allowed_roles)
        if tests_result != "Passed": return tests_result

        return users_roles_table.update_user_role(user_id, old_role_id, new_role_id)

    # ********************************************************
    # READ
    @rpc(_returns=Iterable(Integer))
    def get_roles(ctx):
        roles = roles_table.get_roles()
        for role in roles:
            yield role.value

        if len(roles) == 0:
            yield "NO ROLES"

    # login
    @rpc(String, String, _returns=String)
    def verify_login_details(ctx, username, password):
        user = users_table.get_user_by_username(username)
        if user == "User does not exist!":
            return "Incorrect Login Details!"

        if user.password != password:
            return "Incorrect Login Details!"

        token = JWTOperations.generate_user_token(user)

        # return "Successfully Logged In!"
        return token

    @rpc(String)
    def logout(ctx, token):
        JWTOperations.invalidate_token(token)

    @rpc(String, _returns=String)
    def authorize(ctx, token):
        authorization_response = JWTOperations.get_authorization(token)
        return authorization_response

    @rpc(String, Iterable(Integer), _returns=String)
    def verify_role_integrity(ctx, token, allowed_roles):
        integrity_response = JWTOperations.get_integrity(token, allowed_roles)
        return integrity_response


application = Application([IDMService], 'services.spotify.soap',
                          in_protocol=Soap11(validator='lxml'),
                          out_protocol=Soap11())

wsgi_application = WsgiApplication(application)

if __name__ == '__main__':
    import logging

    from wsgiref.simple_server import make_server

    logging.basicConfig(level=logging.INFO)
    logging.getLogger('spyne.protocol.xml').setLevel(logging.INFO)

    logging.info("listening to http://127.0.0.1:8000")
    logging.info("wsdl is at: http://127.0.0.1:8000/?wsdl")

    server = make_server('127.0.0.1', 8000, wsgi_application)
    server.serve_forever()
