package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import client.Order;
import client.User;
import model.user.CreateUserJson;
import model.user.UserJson;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.*;

public class GetOrderTest {
    Order order;
    User user;
    UserJson userJson;

    @Before
    public void setUp() {
        order = new Order();
        user = new User();
        userJson = CreateUserJson.generateUserAccount();
        user.createUser(userJson);

    }

    @Test
    @DisplayName("Проверка получения заказов авторизованного пользователя")
    public void checkGetOrderAuthUser() {
        Response response = order.getOrders();

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("orders", notNullValue() )
                .body("total", notNullValue())
                .body("totalToday", notNullValue());
    }

    @Test
    @DisplayName("Проверка получения заказов неавторизованного пользователя")
    public void checkGetOrderWithoutAuth() {
        user.clearToken();

        Response response = order.getOrders();

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