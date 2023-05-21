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

import static org.hamcrest.CoreMatchers.is;

public class ListOrdersTest {
    private UserClient userClient;
    private OrderClient orderClient;
    private Order order;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = UserGenerator.getRandomUser();
        order = OrderGenerator.getDefaultOrder();
        accessToken = userClient.createUser(user).extract().path("accessToken");
        orderClient.createOrder(accessToken, order);
        System.out.println(accessToken);
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Можно получить список заказов пользователя с авторизацией")
    public void getOrderListUserWithAuthTest() {
        ValidatableResponse getUserListOrder = orderClient.getListOrdersUser(accessToken);
        getUserListOrder.assertThat().statusCode(200).body("success", is(true));
    }

    @Test
    @DisplayName("Можно получить список заказов пользователя без авторизации")
    public void getOrderListUserWithoutAuthTest() {
        ValidatableResponse getUserListOrder = orderClient.getListOrdersUser("");
        getUserListOrder.assertThat().statusCode(401).body("success", is(false));
    }
}
