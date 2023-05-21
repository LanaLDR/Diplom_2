package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.order.Order;
import org.example.order.OrderClient;
import org.example.order.OrderGenerator;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class CreateOrderTest {
    private User user;
    private UserClient userClient;
    private Order order;
    private OrderClient orderClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = UserGenerator.getRandomUser();
        order = OrderGenerator.getDefaultOrder();
        accessToken = userClient.createUser(user).extract().path("accessToken");
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Можно создать заказ с авторизацией")
    public void createOrderWithAuthTest() {
        ValidatableResponse createOrderClient = orderClient.createOrder(accessToken, order);
        createOrderClient.assertThat().statusCode(200).body("success", is(true));
    }

    @Test
    @DisplayName("Можно создать заказ без авторизациий")
    public void createOrderWithoutAuthTest() {
        ValidatableResponse createOrderClient = orderClient.createOrder("", order);
        createOrderClient.assertThat().statusCode(200).body("success", is(true));
    }

    @Test
    @DisplayName("Нельзя создать заказ без ингредиентов с авторизацией")
    public void notCreateOrderWithoutIngredientsWithAuthTest() {
        order = new Order();
        ValidatableResponse createOrderClient = orderClient.createOrder(accessToken, order);
        createOrderClient.assertThat().statusCode(400).body("success", is(false));
    }

    @Test
    @DisplayName("Нельзя создать заказ без ингредиентов без авторизации")
    public void notCreateOrderWithoutIngredientsWithoutAuthTest() {
        order = new Order();
        ValidatableResponse createOrderClient = orderClient.createOrder("", order);
        createOrderClient.assertThat().statusCode(400).body("success", is(false));
    }

    @Test
    @DisplayName("Нельзя создать заказ c неверными ингредиетами с авторизацией")
    public void notCreateOrderWithFakeIngredientsWithAuthTest() {
        order = new Order(List.of("123kek321"));
        ValidatableResponse createOrderClient = orderClient.createOrder(accessToken, order);
        createOrderClient.assertThat().statusCode(500);
    }

    @Test
    @DisplayName("Нельзя создать заказ с неверными ингредиентами без авторизации")
    public void notCreateOrderWithFakeIngredientsWithoutAuthTest() {
        order = new Order(List.of("123kek321"));
        ValidatableResponse createOrderClient = orderClient.createOrder("", order);
        createOrderClient.assertThat().statusCode(500);
    }
}
