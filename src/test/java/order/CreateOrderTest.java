package order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.order.OrderRequestIngredients;
import client.Order;
import client.User;
import model.user.CreateUserJson;
import model.user.UserJson;
import model.order.CreateOrder;
import java.net.HttpURLConnection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest {


    Order order;
    User user;
    OrderRequestIngredients orderJson;
    UserJson userJson;

    @Before
    public void setUp() {
        order = new Order();
        user = new User();
        orderJson = CreateOrder.createOrder();
        userJson = CreateUserJson.generateUserAccount();
    }


    @Test
    @DisplayName("Проверка создания заказа с авторизацией")
    public void checkCreateOrderWithLogin() {
        user.createUser(userJson);

        Response response = order.createOrder(orderJson);

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order", notNullValue());
    }

    @Test
    @DisplayName("Проверка создания заказа без авторизации")
    public void checkCreateOrderWOLogin() {
       user.createUser(userJson);
       user.clearToken();

        Response response = order.createOrder(orderJson);

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order", notNullValue());
    }



    @Test
    @DisplayName("Проверка создания заказа без ингредиентов")
    public void checkCreateOrderWOIngredients() {
        user.createUser(userJson);
        orderJson.setIngredients(List.of());


        Response response = order.createOrder(orderJson);

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Проверка создания заказа с неверным хешем ингредиентов")
    public void checkCreateOrderWOInvalidIdIngredients() {
        user.createUser(userJson);
        orderJson.setIngredients(List.of("111111111111111111111111"));

        Response response = order.createOrder(orderJson);

        response.then()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("One or more ids provided are incorrect"));
    }

    @After
    public void tearDown(){
        User.deleteUserAccount(userJson);
    }

}