package ru.lexxxz.go2lunch;

import ru.lexxxz.go2lunch.model.Role;
import ru.lexxxz.go2lunch.model.User;

import static ru.lexxxz.go2lunch.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);
}
