package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.RestClient;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String CREATE_ORDER_PATH = "api/orders/";
    private static final String GET_ORDERS_USER_PATH = "api/orders/";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(String accessToken, Order order) {
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then();
    }

    @Step("Получение списка заказов пользователя")
    public ValidatableResponse getListOrdersUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .when()
                .get(GET_ORDERS_USER_PATH)
                .then();
    }
}
