package com.fs.test;

import com.fs.services.CartService;
import com.fs.services.ProductService;
import com.fs.utils.Endpoints;
import io.restassured.http.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoreAdminTest extends BaseTest {

    ProductService productService;
    CartService cartService;

    @BeforeEach
    void init() {
        ApiClient client = new ApiClient(requestSpecification);
        productService = new ProductService(client);
        cartService = new CartService(client);
}
    @Test
    @DisplayName("The lowest rated product can be deleted from the product list")
    void deleteLowestRatedProductFromStore() {
        Integer lowestRatedProductId = productService.listofProductIdsByRating();

        response = productService.deleteProduct(lowestRatedProductId);
        assertEquals(200, response.statusCode());
    }
}
