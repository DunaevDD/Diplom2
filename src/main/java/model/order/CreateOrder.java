package model.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import client.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateOrder {
    private static final Order order = new Order();
    private static final List<String> listOfIngredients = new ArrayList<>();

    private static void getIdIngredients() {
        Response response = order.getListIngredients();
        OrderResponseIngredients ingredients = response.then().extract().as(OrderResponseIngredients.class);
        ArrayList<OrderJson> dataJson = ingredients.getData();
        Random r = new Random();
        List<OrderJson> rndData = dataJson.subList(r.nextInt(dataJson.size()), dataJson.size());
        rndData.forEach((x) -> listOfIngredients.add(x.get_id()));
    }

    @Step("Создание заказа ингредиенты")
    public static OrderRequestIngredients createOrder() {
        getIdIngredients();
        return new OrderRequestIngredients(listOfIngredients);
    }
}