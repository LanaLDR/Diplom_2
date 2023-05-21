package org.example.user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    private static final String defaultEmail = "Amogus123@yandex.ru";
    private static final String defaultPassword = "123321";
    private static final String defaultName = "AmogusUser";


    public static User getRandomUser() {
        final String email = RandomStringUtils.randomAlphabetic(3, 5)+"@yandex.ru";
        final String password = RandomStringUtils.randomAlphabetic(3, 10);
        final String name = RandomStringUtils.randomAlphabetic(3, 10);
        return new User(email, password, name);
    }

    public static User getDefaultUser() {
        return new User(defaultEmail, defaultPassword, defaultName);
    }

    public static String getDefaultEmail() {
        return defaultEmail;
    }

    public static String getDefaultPassword() {
        return defaultPassword;
    }

    public static String getDefaultName() {
        return defaultName;
    }
}
