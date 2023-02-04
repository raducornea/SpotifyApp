# https://www.geeksforgeeks.org/how-to-generate-jwt-tokens-using-fastapi-in-python/

# Terminal run that command:
# openssl rand -hex 32

# Put the SECRET_KEY

# Terminal run that command:
# uvicorn main:app --reload


from fastapi import FastAPI, status, HTTPException
from jose import JWTError, jwt
from pydantic import BaseModel
from datetime import datetime, timedelta

# replace it with your 32 bit secret key
SECRET_KEY = "977bb4fec499fef10bcc9d2cf8ecb763c6fd9036151ee6184fdbc5d2e8d97cba"

# encryption algorithm
ALGORITHM = "HS256"


# Pydantic Model that will be used in the token endpoint for the response
class Token(BaseModel):
    access_token: str
    token_type: str


# Initialise the app
app = FastAPI()


# this function will create the token for particular data
def create_access_token(data: dict):
    to_encode = data.copy()

    # expire time of the token
    expire = datetime.utcnow() + timedelta(minutes=15)
    to_encode.update({"exp": expire})
    encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)

    # return the generated token
    return encoded_jwt


# the endpoint to get the token
@app.get("/get_token")
async def get_token():
    # data to be signed using token
    data = {
        'info': 'secret information',
        'from': 'GFG'
    }
    token = create_access_token(data=data)
    return {'token': token}


# the endpoint to verify the token
@app.post("/verify_token")
async def verify_token(token: str):
    # try to decode the token, it will
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        return payload

    # raise error if the token is not correct
    except JWTError:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Could not validate credentials",
        )
