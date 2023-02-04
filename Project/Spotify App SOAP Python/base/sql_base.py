from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

Base = declarative_base()
engine = create_engine('mariadb+mariadbconnector:'
                       '//remote-admin:passwdremote@localhost/idm')
Session = sessionmaker(bind=engine)
