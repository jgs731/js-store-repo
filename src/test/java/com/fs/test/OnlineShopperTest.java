package com.fs.test;

import com.fs.datamodels.*;
import com.fs.utils.Endpoints;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OnlineShopperTest extends BaseTest {
    public int productId;
    @Test
    @DisplayName("Verify the number of products in store")
    void verifyNumberOfProductsInStore() {
        response = executeCall(Method.GET, Endpoints.PRODUCTS);

        assertEquals(20, response.jsonPath().getList("id").size());
    }

    @Test
    @DisplayName("Verify that a valid product ID returns a single result")
    void verifyAValidSingleProductIsReturned() {
        response = executeCall(Method.GET, Endpoints.PRODUCTS + "1");

        assertEquals(1, response.jsonPath().getObject("id", Integer.class));
    }

    @ParameterizedTest
    @DisplayName("Find all products for one particular category")
    @ValueSource(strings = {"electronics"})
    void verifyASingleCategoryOfProductIsReturned(String specificCategory) {
        response = executeCall(Method.GET, Endpoints.PRODUCT_CATEGORY + specificCategory);

        assertEquals(6, response.jsonPath().getList("category").size());
    }

    @Test
    @DisplayName("Verify that searches for non-existent Products returns no results")
    void verifyNoProductIsReturnedForAnInvalidId() {
        response = executeCall(Method.GET, Endpoints.PRODUCTS + "test");

        assertTrue(response.getBody().asString().isBlank());
    }

    @Test
    @DisplayName("Cart details for all customers can be viewed")
    void allCartDetailsCanBeViewed() {
        response = executeCall(Method.GET, Endpoints.CART);

        assertEquals(7, response.jsonPath().getList("carts", Cart.class).size());
    }

    @Test
    @DisplayName("Verify that cart details can be accessed for a specified user")
    void cartCanBeViewedForAnIndividualUser() {
        response = executeCall(Method.GET, Endpoints.CART + "/1");

        assertEquals(3, response.jsonPath().getList("products", Products.class).size());
    }

    @Test
    @DisplayName("Verify that the product added to cart contains expected product information")
    void addCheapestElectronicItemToCart() {
        String selectedCategory = listOfProductCategories().getFirst(); // electronics is first in list
        Integer cheapestElectronicID = cheapestProductInCategoryId(selectedCategory);

        Cart item = new Cart(null, 1, Date.valueOf(LocalDate.now()), List.of(
                new Products(cheapestElectronicID, 1)
        ));

        response = executeCall(Method.POST, Endpoints.CART, null, item);
        productId = response.jsonPath().getObject("$", Cart.class)
                .products().getLast().productId();
        assertNotNull(response.jsonPath().getObject("$", Cart.class)
                        .products().getLast().productId(),
                "product not added to cart");

        Response response2 = executeCall(Method.GET, Endpoints.PRODUCTS + productId);
        assertAll("Product is recognised in the products list",
                () -> assertEquals("64", response2.jsonPath().get("price").toString()),
                () -> assertEquals("203", response2.jsonPath().get("rating.count").toString())
        );
    }
}
