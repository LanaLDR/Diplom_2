package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class NotLoginUserParamTest {
    private final String email;
    private final String password;
    private UserClient userClient;
    private static User user;
    private String accessToken;

    public NotLoginUserParamTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Object[][] getLoginData() {
        return new Object[][] {
                {UserGenerator.getDefaultEmail(), "fakePass"},
                {"fakeEmail@yandex.ru", UserGenerator.getDefaultPassword()},
                {UserGenerator.getDefaultEmail(), null},
                {null, UserGenerator.getDefaultPassword()},
                {null, null}
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getDefaultUser();
        accessToken = userClient.createUser(user).extract().path("accessToken");
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Пользователь не может авторизоваться при некорректных данных")
    public void loginUserWithFakeEmailTest() {
        ValidatableResponse loginUserResponse = userClient.loginUser(email, password);
        loginUserResponse.assertThat().statusCode(401).body("success", is(false));
    }
}
