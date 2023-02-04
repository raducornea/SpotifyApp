from pymongo import MongoClient
from bson.objectid import ObjectId

# check uri string to add user and password
steam_users_client = MongoClient("mongodb://localhost:27017/")
steam_users_db = steam_users_client["pos_mongo_example"]
steam_users_collection = steam_users_db["steam_users"]


class User:
    def __init__(self, user_dict):
        self.id = user_dict['_id']
        self.username = user_dict['username'] if 'username' in user_dict else None
        self.achievements = user_dict['achievements'] if 'achievements' in user_dict else None
        self.games = user_dict['games'] if 'games' in user_dict else None
        self.join_year = user_dict['join_year'] if 'join_year' in user_dict else None

    def __iter__(self):
        for attr, value in self.__dict__.items():
            if value is not None:
                yield attr, value

    def __str__(self):
        return "".join([f"{field}={value}\n" for field, value in self.__iter__()])


def show_all_users():
    user_list = [User(user) for user in steam_users_collection.find({})]
    for user in user_list:
        print(user)


def find_user_by_username(username: str):
    user = User(steam_users_collection.find_one({"username": username}))
    return user


def delete_user_by_id(id: str):
    steam_users_collection.delete_one({"_id": ObjectId(id)})


def update_user():
    user = steam_users_collection.find_one({"username": "Ratonu"})

    user['new_field'] = 'New Value for field'
    steam_users_collection.update_one({"username": "Ratonu"}, {"$set": user}, upsert=True)

    print(steam_users_collection.find_one({"username": "Ratonu"}))


def create_user():
    user = {
        "username": "Tester",
        "achievements": ["Just One"]
    }
    steam_users_collection.insert_one(user)


if __name__ == "__main__":
    show_all_users()

    update_user()
    create_user()

    show_all_users()

    tester_user = find_user_by_username("Tester")
    print("*"*100)
    print(tester_user)
    delete_user_by_id(tester_user.id)

    show_all_users()
