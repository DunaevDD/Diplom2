package user;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import client.User;
import model.user.CreateUserJson;
import model.user.UserJson;

import java.net.HttpURLConnection;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest {

    UserJson userJson;
    User user;

    @Before
    public void setUp() {
        user = new User();
        userJson = CreateUserJson.generateUserAccount();
    }

    @Test
    @DisplayName("Проверка создания уникального пользователя")
    public void checkCreateSuccessUserTest() {
        Response response = user.createUser(userJson);
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания уже существующего пользователя")
    public void checkCreateExistUser(){
        user.createUser(userJson);

        Response response = user.createUser(userJson);
        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("success",equalTo(false))
                .body("message",equalTo("User already exists"));
    }

    @Test
    @DisplayName("Проверка создания пользователя без почты")
    public void CheckCreateUserWithoutEmail(){
        userJson.setEmail(null);

        Response response = user.createUser(userJson);
        response.then()
                .assertThat()
                .statusCode(403)
                .body("success",equalTo(false))
                .body("message",equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Проверка сооздания пользователя без пароля")
    public void checkCreateUserWOPassword(){
        userJson.setPassword(null);

        Response response = user.createUser(userJson);
        response.then()
                .assertThat()
                .statusCode(403)
                .body("success",equalTo(false))
                .body("message",equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Проверка создание пользователя без имени")
    public void checkCreateUserWOName(){
        userJson.setName(null);

        Response response = user.createUser(userJson);
        response.then()
                .assertThat()
                .statusCode(403)
                .body("success",equalTo(false))
                .body("message",equalTo("Email, password and name are required fields"));
    }



    @After
    public void tearDown(){
        User.deleteUserAccount(userJson);
    }
}