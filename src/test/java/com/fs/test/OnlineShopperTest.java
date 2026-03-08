package com.fs.test;

import com.fs.datamodels.*;
import com.fs.services.CartService;
import com.fs.services.ProductService;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OnlineShopperTest extends BaseTest {

    ProductService productService;
    CartService cartService;

    @BeforeEach
    void init() {
        ApiClient client = new ApiClient(requestSpecification);
        productService = new ProductService(client);
        cartService = new CartService(client);
    }

    public int productId;
    @Test
    @DisplayName("Verify the number of products in store")
    void verifyNumberOfProductsInStore() {
        response = productService.getAllProducts();

        assertTrue(response.jsonPath().getList("id").size() > 0);
    }

    @ParameterizedTest
    @DisplayName("Verify that a valid product ID returns a single result")
    @ValueSource(ints = {1, 4, 9, 16})
    void verifyAValidSingleProductIsReturned(Integer id) {
        response = productService.getProduct(id);

        assertEquals(1, response.jsonPath().getObject("id", Integer.class));
    }

    @ParameterizedTest
    @DisplayName("Find all products for one particular category")
    @ValueSource(strings = {"electronics"})
    void verifyASingleCategoryOfProductIsReturned(String specificCategory) {
        List<String> listOfElectronicProducts = productService.listOfProductCategories();

        assertTrue(listOfElectronicProducts.size() < 20);
    }

    @Test
    @DisplayName("Verify that searches for non-existent Products returns no results")
    void verifyNoProductIsReturnedForAnInvalidId() {
        response = productService.getProduct(99);

        assertTrue(response.getBody().asString().isBlank());
    }

    @Test
    @DisplayName("Cart details for all customers can be viewed")
    void allCartDetailsCanBeViewed() {
        response = cartService.getAllCarts();

        assertEquals(7, response.jsonPath().getList("carts", Cart.class).size());
    }

    @Test
    @DisplayName("Verify that cart details can be accessed for a specified user")
    void cartCanBeViewedForAnIndividualUser() {
        response = cartService.getParticularItemFromCart(1);

        assertEquals(3, response.jsonPath().getList("products", Products.class).size());
    }

    @Test
    @DisplayName("Verify that the product added to cart contains expected product information")
    void addCheapestElectronicItemToCart() {
        String selectedCategory = productService.listOfProductCategories().getFirst(); // electronics is first in list
        Integer cheapestElectronicID = productService.cheapestProductInCategoryId(selectedCategory);

        Cart item = new Cart(1, Date.valueOf(LocalDate.now()), List.of(
                new Products(cheapestElectronicID, 1)
        ));

        response = cartService.addItemToCart(item);
        productId = response.jsonPath().getObject("$", Cart.class)
                .products().getLast().productId();
        assertNotNull(response.jsonPath().getObject("$", Cart.class)
                        .products().getLast().productId(),
                "product not added to cart");

        Response response2 = productService.getProduct(productId);
        assertAll("Product is recognised in the products list",
                () -> assertEquals("64", response2.jsonPath().get("price").toString()),
                () -> assertEquals("203", response2.jsonPath().get("rating.count").toString())
        );
    }
}
