# https://github.com/mpdavis/python-jose/


from jose import jwt

secret = "v#$%^<V4256vy$%^>vy@6V@%VY>@%^v>v^@%<p<bu,P13V<&*b_+"


def main():
    token = jwt.encode({"key": "value"}, secret, algorithm="HS256")
    print(token)

    decoded = jwt.decode(token, secret, algorithms=["HS256"])
    print(decoded)


if __name__ == '__main__':
    main()
