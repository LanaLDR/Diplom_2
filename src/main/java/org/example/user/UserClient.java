package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.RestClient;
import static io.restassured.RestAssured.*;

public class UserClient extends RestClient {
    private static final String USER_PATH = "api/auth/user/";
    private static final String CREATE_USER_PATH = "api/auth/register/";
    private static final String LOGIN_USER_PATH = "api/auth/login/";
    private static final String DELETE_USER_PATH = "api/auth/user/";
    private static final String EDIT_USER_PATH = "api/auth/user/";



    @Step("Получеие информации о пользователе")
    public ValidatableResponse getUserInfo() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(USER_PATH)
                .then();
    }

    @Step("Обновление информации о пользователе")
    public ValidatableResponse editUserInfo(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .patch(USER_PATH)
                .then();
    }

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(CREATE_USER_PATH)
                .then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(String email, String password) {
        UserCredentials userCredentials = new UserCredentials(email, password);
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(LOGIN_USER_PATH)
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .when()
                .delete(DELETE_USER_PATH)
                .then();
    }

    @Step("Изменение пользователя")
    public ValidatableResponse editUser(String accessToken, String email, String password, String name) {
        User user = new User(email, password, name);
        return given()
                .spec(getBaseSpec())
                .header("authorization", accessToken)
                .body(user)
                .when()
                .patch(EDIT_USER_PATH)
                .then();
    }
}
