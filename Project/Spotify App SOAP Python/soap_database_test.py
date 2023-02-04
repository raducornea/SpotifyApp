# python -m pip install suds
from suds.client import Client

from models.user_orm import User

c = Client('http://localhost:8000/?wsdl')

"""
isi extrage metodele de care are nevoie in WSDL
GET e permisa pentru obtinerea WSDL-ului
POST - interactiunea cu metodele

in portType se afla metodele
in binding se afla detaliile despre document (nume, transport protocoale, metode)
"""


# CREATE
# @returns String with Success/Error
def create_user(username, password, token):
    return c.service.create_user(username, password, token)


# READ
# @returns List with Usernames
def get_usersnames(token):
    usernames = c.service.get_usersnames(token)[0]
    return usernames


def get_user_by_id(id, token):
    user_details = c.service.get_user_by_id(id, token)
    # return user_constructor(user_details)
    return user_details


def get_user_by_username(username, token):
    user_details = c.service.get_user_by_username(username, token)
    # return user_constructor(user_details)
    return user_details


# UPDATE
def update_username(id, new_name, token):
    return c.service.update_username(id, new_name, token)


def update_password(id, new_password, token):
    return c.service.update_password(id, new_password, token)


# DELETE
def delete_user_by_id(id, token):
    return c.service.delete_user_by_id(id, token)


def delete_user_by_username(username, token):
    return c.service.delete_user_by_username(username, token)


# ********************************************************
# CREATE
def create_user_role(user_id, role_id, token):
    return c.service.create_user_role(user_id, role_id, token)


# READ
def get_user_roles(user_id, token):
    roles = c.service.get_user_roles(user_id, token)[0]
    return roles


# UPDATE
def update_user_role(user_id, old_role_id, new_role_id, token):
    return c.service.update_user_role(user_id, old_role_id, new_role_id, token)


def user_constructor(user_details):
    id = user_details.split("~")[0].split("=")[1]
    username = user_details.split("~")[1].split("=")[1]
    password = user_details.split("~")[2].split("=")[1]
    return id, username, password


# ********************************************************
# READ
def get_roles():
    app_roles = c.service.get_roles()[0]
    return app_roles


def print_all_user_details():
    usernames = get_usersnames()
    for username in usernames:
        id, username, password = get_user_by_username(username)
        print(id, username, password)

        roles = get_user_roles(id)
        for role in roles:
            print(role) if role != -1 else print("NO ROLES")

        print("*" * 100)


def verify_login_details(username, password):
    return c.service.verify_login_details(username, password)


def verify_role_integrity(token, allowed_roles):
    return c.service.verify_role_integrity(token, allowed_roles)


# PARTEA PENTRU LABORATORUL 10
# ********************************************************
# ********************************************************
# ********************************************************


def authorize(token):
    return c.service.authorize(token)


def logout(token):
    c.service.logout(token)


def is_valid_token(token, role_needed):
    response = authorize(token)
    if response in ["Blocked Token", "Invalid Token", "Unauthorized"]:
        return False
    return True


def main():
    # users = get_usersnames()
    # for user in users:
    #     print(user)
    #
    # create_user("mihaitaesteboss", "123")
    # create_user("johnyxd", "dvorak")
    # create_user("catlover69", "ilovecats")
    # create_user("DorianPopa", "Dino")
    # create_user("DorianPopa32", "DinoHehe")

    # print(update_username(25, "JohnnyXD"))
    # print(update_password(25, "qwertyuiop123"))
    # print(delete_user_by_id(19))
    # print(delete_user_by_username("DorianPopa32"))

    # 2. listarea informatiilor asociate unui singur utilizator + lista de roluri ale lui
    # id, username, password = get_user_by_username("DorianPopa32")
    # id, username, password = get_user_by_id(170)
    # print(id, username, password)
    #
    # roles = get_user_roles(id)
    # for role in roles:
    #     print(role) if role != -1 else print("NO ROLES")

    # 3.listarea utilizatorilor inregistrati si a rolurilor asociate dintre acestia
    # print_all_user_details()

    # 5. verificarea perechii username-password
    # print(verify_login_details("catlover69", "ilovecats"))
    # print(verify_login_details("jdifdfjsdifj", "dgdg"))

    # 6. verificare rol si roluri utilizator
    # print(verify_role_integrity(token, [1]))
    # print(verify_role_integrity(token, [3]))

    # ********************************************************
    # create_user_role(25, 1)
    # create_user_role(25, 4)
    # create_user_role(170, 3)

    # print(update_user_role(25, 4, 2))

    # ********************************************************
    # app_roles = get_roles()
    # for role in app_roles:
    #     print(f"Rol disponibil: {role}")

    # PARTEA PENTRU LABORATORUL 10
    # ********************************************************
    # ********************************************************
    # ********************************************************
    token1 = verify_login_details("JohnnyXD", "qwertyuiop123")
    token2 = verify_login_details("catlover69", "ilovecats")
    token3 = verify_login_details("jdifdfjsdifj", "dgdg")
    expired_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjUsInVzZXJuYW1lIjoiSm9obm55WEQiLCJwYXNzd29yZCI6InF3ZXJ0eXVpb3AxMjMiLCJyb2xlcyI6WzEsMl0sImV4cCI6MTY3MDY5OTI1OH0.ralS_IUUKtnOOaT7wtk-BiTwJAXyt11NN2VzxoSl54c"
    print(token1)
    print(token2)
    print(token3)
    print(expired_token)

    authorized1 = authorize(token1)
    authorized2 = authorize(token2)
    authorized3 = authorize(token3)
    authorized4 = authorize(expired_token)
    print(authorized1)
    print(authorized2)
    print(authorized3)
    print(authorized4)

    # here we logout with token1 and see if it's still valid
    logout(token2)
    authorized2 = authorize(token2)
    print(authorized2)

    # TESTING SOME OPERATIONS FOR USER WITH TOKEN1
    print("*"*50)
    print(create_user("Utilizator Proba", "PAROLA", token1))
    print(get_usersnames(token1))
    print(get_user_by_id(170, token1))
    print(get_user_by_username("DorianPopa32", token1))
    print(update_username(25, "JohnnyXD", token1))
    print(update_password(25, "qwertyuiop123", token1))
    print(delete_user_by_id(19, token1))
    print(delete_user_by_username("DorianPopa32", token1))
    print(create_user_role(170, 3, token1))
    # print(get_user_roles(170, token1))
    print(update_user_role(25, 4, 2, token1))


if __name__ == '__main__':
    main()
