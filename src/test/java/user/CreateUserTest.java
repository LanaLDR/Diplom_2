package user;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CreateUserTest {
    private UserClient userClient;
    private User user;
    private String accessToken;


    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Пользователь создается при корректных данных")
    public void createUniqueUserTest() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        createUserResponse.assertThat().statusCode(200).body("success", is(true));
        accessToken = createUserResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Нельзя создать существующего пользователя")
    public void createNotUniqueUserTest() {
        accessToken = userClient.createUser(user).extract().path("accessToken");
        ValidatableResponse createUserResponse = userClient.createUser(user);
        createUserResponse.assertThat().statusCode(403).body("success", is(false));
    }
}
