from models.user_orm import User
from base.sql_base import Session
from sqlalchemy import update, delete, event

from sqlalchemy.schema import DDL


# CREATE
def create_user(username, password):
    session = Session()
    user = User(username, password)
    try:
        session.add(user)
        session.commit()
    except Exception as exc:
        print(f"Failed to add user - {exc}")
        return f"Failed to Create User! Username is already taken!"
    return "Successfully Created User!"
    # return user


# READ
def get_users():
    session = Session()
    users = session.query(User).all()
    return users


def get_user_by_username(username):
    session = Session()
    users = session.query(User).filter_by(username=username)
    if users.count() == 0:
        return "User does not exist!"
    return users[0]


def get_user_by_id(id):
    session = Session()
    users = session.query(User).filter_by(id=id)
    if users.count() == 0:
        return "User does not exist!"
        # return User("N/A", "N/A")
    return users[0]


# UPDATE
def update_username(id, new_name):
    try:
        # unsafe:
        # statement = f"UPDATE users SET username='{new_name}' WHERE id={id};"
        statement = (
            update(User)
            .where(User.id == id)
            .values(username=new_name)
        )
        session = Session()
        session.execute(statement)
        session.commit()
        return "Update Successful!"
    except Exception as exc:
        print(f"Failed to update user - {exc}")
        return f"Failed to Update User!"


def update_password(id, new_password):
    try:
        # unsafe:
        # statement = f"UPDATE users SET username='{new_name}' WHERE id={id};"
        statement = (
            update(User)
            .where(User.id == id)
            .values(password=new_password)
        )
        session = Session()
        session.execute(statement)
        session.commit()
        return "Update Successful!"
    except Exception as exc:
        print(f"Failed to update user - {exc}")
        return f"Failed to Update User!"


# DELETE
def delete_all_users():
    session = Session()
    session.execute("DELETE FROM users_roles;")
    session.execute("DELETE FROM users;")

    # event.listen(
    #     User.__table__,
    #     "after_create",
    #     DDL("ALTER TABLE %(table)s AUTO_INCREMENT = 1;")
    # )
    # event.listen(User.__table__, "after_create", DDL("ALTER TABLE user AUTO_INCREMENT = 5"))
    # session.execute("ALTER TABLE users AUTO_INCREMENT = 1;")  # de ce e blocanta???
    # DDL("ALTER TABLE users AUTO_INCREMENT = 1", on='mysql').execute_at('after-create', User)

    session.commit()


def delete_user_by_id(id):
    # statement = f"DELETE FROM users WHERE id = {id}"
    statement = delete(User).where(User.id == id)

    session = Session()
    session.execute(statement)
    session.commit()

    return "Delete Successful!"


def delete_user_by_username(username):
    # statement = f"DELETE FROM users WHERE username = '{username}'"
    statement = delete(User).where(User.username == username)

    session = Session()
    session.execute(statement)
    session.commit()

    return "Delete Successful!"
