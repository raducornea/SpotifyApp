from models.users_roles_orm import users_roles_relationship as UsersRoles
from base.sql_base import Session


# CREATE
def create_user_role(user_id, role_id):
    session = Session()
    statement = UsersRoles.insert().values({"user_id": user_id, "role_id": role_id})

    try:
        session.execute(statement)
        session.commit()
        return f"Created role {role_id} for user with id {user_id}!"
    except Exception as exc:
        print(f"Failed to add user_role - {exc}")
        return f"Failed to create role {role_id} for user with id {user_id}!"


# READ
def get_users_roles_relationships():
    session = Session()
    users_roles = session.query(UsersRoles).all()
    return users_roles


def get_user_roles(user_id):
    session = Session()
    users_roles = session.query(UsersRoles).where(UsersRoles.c.user_id == user_id)
    return users_roles


# UPDATE
def update_user_role(user_id, old_role_id, new_role_id):
    try:
        # must check current user roles before updating them...
        # else blocking thread :X
        roles = get_user_roles(user_id)
        if (user_id, new_role_id) in roles:
            return f"User already has role {new_role_id}"

        # statement = f"UPDATE users_roles SET role_id={new_role_id} WHERE user_id={user_id} AND role_id={old_role_id};"
        statement = UsersRoles.update().where(UsersRoles.c.user_id == user_id).where(UsersRoles.c.role_id == old_role_id).values(role_id=new_role_id)

        session = Session()
        session.execute(statement)
        session.commit()
        return "Role successfully updated!"
    except Exception as exc:
        print(f"Failed to update user_role - {exc}")
        return "Role failed to update!"


# DELETE
def delete_user_role(user_id, role_id):
    session = Session()
    statement = UsersRoles.delete().where(UsersRoles.c.user_id == user_id).where(UsersRoles.c.role_id == role_id)

    session.execute(statement)
    session.commit()
