package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.order.OrderRequestIngredients;

public class Order extends TokenClient {

    public static final String ENDPOINT_INGREDIENTS = "/ingredients";
    public static final String ENDPOINT_ORDERS = "/orders";

    @Step("Отправка запроса на получение ингредиентов GET /api/ingredients")
    public Response getListIngredients() {
        return reqSpec.get(ENDPOINT_INGREDIENTS);
    }

    @Step("Отправка запроса на создание заказа POST /api/orders")
    public Response createOrder(OrderRequestIngredients body) {
        return reqSpec
                .header("Authorization", token)
                .body(body)
                .post(ENDPOINT_ORDERS);
    }

    @Step("Отправка запроса на получение заказов GET /api/orders")
    public Response getOrders() {
        return reqSpec
                .header("Authorization", token)
                .get(ENDPOINT_ORDERS);
    }
}