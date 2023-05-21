package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.user.User;
import org.example.user.UserClient;
import org.example.user.UserGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class NotCreateUserTest {
    private UserClient userClient;
    private User user;
    private final String email;
    private final String password;
    private final String name;

    public NotCreateUserTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2}")
    public static Object[][] getLoginData() {
        return new Object[][] {
                {null, UserGenerator.getDefaultPassword(), UserGenerator.getDefaultName()},
                {UserGenerator.getDefaultEmail(), null, UserGenerator.getDefaultName()},
                {UserGenerator.getDefaultEmail(), UserGenerator.getDefaultPassword(), null},
                {null, null, null},
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = new User(email, password, name);
    }

    @Test
    @DisplayName("Нельзя создать пользователя без обязательных полей")
    public void notCreateUserWithoutRequiredFieldsTest() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        createUserResponse.assertThat().statusCode(403).body("success", is(false));
    }
}
