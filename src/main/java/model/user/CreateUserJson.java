package model.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class CreateUserJson {
    private static String email;
    private static String password;
    private static String name;
    private static final Faker faker = new Faker();

    private static void createUserJson() {
        generateEmail();
        generatePassword();
        generateName();
    }

    @Step("Создание пользователя")
    public static UserJson generateUserAccount() {
        createUserJson();
        return new UserJson(email, password, name);
    }

    public static String generateEmail() {
        return email = faker.internet().emailAddress();
    }

    public static String generatePassword() {
        return password = faker.internet().password();
    }

    public static String generateName() {
        return name = faker.name().username();
    }
}