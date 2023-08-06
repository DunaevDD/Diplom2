package user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.user.CreateUserJson;
import client.User;
import model.user.UserJson;

import java.net.HttpURLConnection;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class LoginTest {

    User user;
    UserJson userJson;

    @Before
    public void setUp(){
        user = new User();
        userJson = CreateUserJson.generateUserAccount();
        user.createUser(userJson);
    }

    @Test
    @DisplayName("Проверка авторизации для существующего пользователя")
    public void checkSuccessfulAuthorization(){
        Response response = user.authorization(userJson);

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success",equalTo(true))
                .body("accessToken",notNullValue())
                .body("refreshToken",notNullValue());
    }

    @Test
    @DisplayName("Проверка авторизации с неверным логином и паролем")
    public void checkInvalidAuthorization(){
        userJson.setName(CreateUserJson.generateName());
        userJson.setPassword(CreateUserJson.generatePassword());
        Response response = user.authorization(userJson);

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success",equalTo(false))
                .body("message",equalTo("email or password are incorrect"));
    }



    @After
    public void tearDown(){
        User.deleteUserAccount(userJson);
    }
}