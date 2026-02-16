package com.fs.test;

import com.fs.utils.Endpoints;
import io.restassured.http.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoreAdminTest extends BaseTest {

    @Test
    @DisplayName("The lowest rated product can be deleted from the product list")
    void deleteLowestRatedProductFromStore() {
        Integer lowestRatedProductId = listofProductIdsByRating();

        response = executeCall(Method.DELETE, Endpoints.PRODUCTS + lowestRatedProductId);
        assertEquals(200, response.statusCode());
    }
}
