import uuid
from datetime import timedelta, datetime

import repositories.users_roles_repository as users_roles_table
from jose import jwt

from jose import jwt, JWTError
import repositories.user_repository as users_table


class JWTOperations(object):
    __secret = "v#$%^<V4256vy$%^>vy@6V@%VY>@%^v>v^@%<p<bu,P13V<&*b_+"
    # ar trebui facut cu o noua tabela tokens...
    # apoi cand invalidez ar trebui sa verific in tokens daca e
    __blocked_tokens = []

    @classmethod
    def generate_user_token(cls, user):
        users_roles = list(users_roles_table.get_user_roles(user.id))
        roles = []
        for user_role in users_roles:
            roles.append(user_role[1])

        expire = datetime.utcnow() + timedelta(minutes=60)
        user_uuid = uuid.uuid1()
        payload = {
            "id": user.id,
            "username": user.username,
            # ar fi ok sa fie hashed parola
            # cu bcrypt de exemplu
            "password": user.password,
            "roles": roles,
            "exp": expire,
            # todo change to uuid
            "jti": str(user_uuid)
        }

        token = jwt.encode(payload, cls.__secret, algorithm="HS256")

        return token

    @classmethod
    def decrypt_token(cls, token):
        decoded = jwt.decode(token, cls.__secret, algorithms=["HS256"])
        return decoded

    @classmethod
    def invalidate_token(cls, token):
        cls.__blocked_tokens.append(token)

    @classmethod
    def is_blocked_token(cls, token):
        return token in cls.__blocked_tokens

    @classmethod
    def get_authorization(cls, token):
        try:
            if JWTOperations.is_blocked_token(token):
                return "Blocked Token"

            # should throw JWTError if it's invalid
            payload = JWTOperations.decrypt_token(token)
            return f"Valid Token"

        except JWTError:
            return "Invalid Token"

    @classmethod
    def get_integrity(cls, token, allowed_roles):
        try:
            payload = JWTOperations.decrypt_token(token)
            user_roles = payload["roles"]

            if allowed_roles == [0] and user_roles == []:
                return "Authorized"

            for user_role in user_roles:
                if user_role in allowed_roles:
                    return "Authorized"

            return "Unauthorized"

        except JWTError:
            return "Invalid Token"

    @classmethod
    def test_token_roles(cls, token, allowed_roles):
        # validare token
        authorization_response = JWTOperations.get_authorization(token)
        if authorization_response != "Valid Token":
            return authorization_response

        # validare rol
        integrity_response = JWTOperations.get_integrity(token, allowed_roles)
        if integrity_response != "Authorized":
            return integrity_response

        return "Passed"
