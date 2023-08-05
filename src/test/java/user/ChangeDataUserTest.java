package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import client.User;
import model.user.CreateUserJson;
import model.user.UserJson;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ChangeDataUserTest {
    User user;
    UserJson userJson;

    @Before
    public void setUp() {
        user = new User();
        userJson = CreateUserJson.generateUserAccount();
        user.createUser(userJson);
        user.authorization(userJson);
    }

    @Test
    @DisplayName("Проверка изменения имени авторизованного пользователя")
    public void checkChangeName() {
        userJson.setName(CreateUserJson.generateName());
        Response response = user.changeDataUser(userJson);

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("user", notNullValue());
    }

    @Test
    @DisplayName("Проверка изменения почты авторизованного пользователя")
    public void checkChangeEmail() {
        userJson.setEmail(CreateUserJson.generateEmail());
        Response response = user.changeDataUser(userJson);

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("user", notNullValue());
    }

    @Test
    @DisplayName("Изменение имени неавторизованного пользователя")
    public void checkChangeNameWOAuth() {
        userJson.setName(CreateUserJson.generateName());
        user.clearToken();

        Response response = user.changeDataUser(userJson);

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Изменение почты неавторизованного пользователя")
    public void checkChangeEmailWOAuth() {
        String emailBefore = userJson.getEmail();
        userJson.setEmail(CreateUserJson.generateEmail());
        user.clearToken();

        Response response = user.changeDataUser(userJson);
        userJson.setEmail(emailBefore);

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void tearDown() {
        User.deleteUserAccount(userJson);
    }
}