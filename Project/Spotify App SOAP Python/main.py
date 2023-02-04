import repositories.user_repository as users_table
import repositories.role_repository as roles_table
import repositories.users_roles_repository as users_roles_table


def user_crud():
    # CREATE
    # users_table.create_user("mihaitaesteboss", "123")
    # users_table.create_user("johnyxd", "dvorak")
    # users_table.create_user("catlover69", "ilovecats")

    # READ
    # users = users_table.get_users()
    # for user in users:
    #     print(user.id, user.username, user.password)

    # user_johnyxd = users_table.get_user_by_id(2)
    # user_johnyxd = users_table.get_user_by_username("johnyxd")
    # print(user_johnyxd.id, user_johnyxd.username, user_johnyxd.password)

    # UPDATE
    # users_table.update_username(3, "doglover69")
    # users_table.update_password(3, "goldenretrievers")

    # DELETE
    # users_table.delete_all_users()

    # users_table.delete_user_by_username("johnyxd")
    # users_table.delete_user_by_id(2)
    print("done user???")


def role_crud():
    # CREATE
    roles_table.create_role(1)
    roles_table.create_role(2)
    roles_table.create_role(3)
    roles_table.create_role(4)

    # READ
    roles = roles_table.get_roles()
    for role in roles:
        print(role.id, role.value)

    print("done role???")


def users_roles_crud():
    # CREATE
    # users_roles_table.create_user_role(3, 4)
    # users_roles_table.create_user_role(3, 2)
    # users_roles_table.create_user_role(2, 2)
    # users_roles_table.create_user_role(4, 1)

    # READ
    # users_roles = users_roles_table.get_users_roles_relationships()
    # for user_role in users_roles:
    #     print(user_role)

    users_roles = users_roles_table.get_user_roles(25)
    for user_role in users_roles:
        print(user_role)

    # UPDATE
    # users_roles_table.update_user_role(4, 2, 4)

    # DELETE
    # users_roles_table.delete_user_role(4, 4)

    print("done users_roles???")


def main():
    # user_crud()
    # role_crud()
    users_roles_crud()

    print("DONE EVERYTHING")


if __name__ == '__main__':
    main()
