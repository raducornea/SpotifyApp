from sqlalchemy import Column, String, Integer
from base.sql_base import Base
from sqlalchemy.orm import relationship
from models.users_roles_orm import users_roles_relationship


class Role(Base):
    __tablename__ = 'roles'

    id = Column(Integer, primary_key=True)
    value = Column(String)
    roles = relationship("User", secondary=users_roles_relationship)

    def __init__(self, value):
        self.value = value
