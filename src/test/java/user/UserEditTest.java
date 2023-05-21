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

public class UserEditTest {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUser();
        accessToken = userClient.createUser(user).extract().path("accessToken");
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Можно изменить пользователя при корректном корректной авторизации")
    public void editUserWithAuthTest() {
        ValidatableResponse editUserResponse =
                userClient.editUser(accessToken, "new@yandex.ru", "newPassword", "newName");
        editUserResponse.assertThat().statusCode(200).body("success", is(true));
    }

    @Test
    @DisplayName("Нельзя изменить данные пользователя при некорректном авторизации")
    public void editUserWithoutAuthTest() {
        ValidatableResponse editUserResponse =
                userClient.editUser("", "new@yandex.ru", "newPassword", "newName");
        editUserResponse.assertThat().statusCode(401).body("success", is(false));
    }
}
