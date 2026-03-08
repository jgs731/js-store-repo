package com.fs.test;

import com.fs.datamodels.*;
import com.fs.services.CartService;
import com.fs.services.ProductService;
import com.fs.services.UserService;
import com.fs.utils.Endpoints;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserLoginAndRegistration extends BaseTest {
    UserService userService;

    @BeforeEach
    void init() {
        userService = new UserService(requestSpecification);
    }


    @Test
    @DisplayName("Users are able to log into the store successfully")
    void validUsersCanLogIn(){
        response = userService.getAllUsers();
        Users firstUser = response.jsonPath().getObject("[0]", Users.class);

        Auth userLogin = new Auth(firstUser.username(), firstUser.password());
        Response response2 = userService.authenticateUser(userLogin);
        String storedToken = response2.jsonPath().getString("token");
        assertFalse(storedToken.isEmpty());
    }

    @Test
    @DisplayName("Users with invalid credentials are unable to log into the store")
    void invalidUsersCannotLogIn(){

        Map<String, String> invalidUserCreds = Map.of(
          "username", "fakeun",
          "password", "fakepw"
        );
        response = userService.authenticateUser(invalidUserCreds);

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
        response = userService.authenticateUser(userDetails);

        assertNotEquals("", response.jsonPath().getObject("id", Integer.class));
    }
}
