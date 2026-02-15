package com.fs.test;

import com.fs.datamodels.*;
import com.fs.utils.Endpoints;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserLoginAndRegistration extends BaseTest {
    static Users users;

    @Test
    @DisplayName("Users are able to log into the store successfully")
    void validUsersCanLogIn(){
        response = executeCall(Method.GET, Endpoints.USERS);
        users = response.jsonPath().getObject("[0]", Users.class);

        Auth userLogin = new Auth(users.username(), users.password());
        response = executeCall(Method.POST, Endpoints.AUTH, null, userLogin);
        String storedToken = response.jsonPath().getString("token");
        System.setProperty("token", storedToken);
        assertFalse(storedToken.isEmpty());
    }

    @Test
    @DisplayName("Users with invalid credentials are unable to log into the store")
    void invalidUsersCannotLogIn(){

        Map<String, String> invalidUserCreds = Map.of(
          "username", "fakeun",
          "password", "fakepw"
        );
        response = executeCall(Method.POST, Endpoints.AUTH, null, invalidUserCreds);

        assertTrue(response.body().asString().contains("username or password is incorrect"));
    }

    @Test
    @DisplayName("A new user can successfully register to the store")
    void newUserCanSuccessfullyRegisterToTheStore(){
        double random = Math.ceil(Math.random() * 100);
        Users userDetails = new Users( "test" + random  + "@example.net", "jaytest" + random, "jaypwd",
                new Name("joshua", "smith"),
                new Address("Manchester", "Mancunian Way", "1", "M1 9DJ",
                        new Geolocation("53.470811512522246", "-2.2417950582410384"),
                        "0123465798"
                ));
        response = executeCall(Method.POST, Endpoints.USERS, null, userDetails);

        assertTrue(response.jsonPath().get("id").equals(11));
    }
}
