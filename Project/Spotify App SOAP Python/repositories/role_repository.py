from models.role_orm import Role
from base.sql_base import Session


# CREATE
def create_role(role):
    session = Session()
    role = Role(role)
    try:
        session.add(role)
        session.commit()
    except Exception as exc:
        print(f"Failed to add role - {exc}")
    return role


# READ
def get_roles():
    session = Session()
    roles = session.query(Role).all()
    return roles