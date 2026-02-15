package com.fs.test;

import com.fs.datamodels.Product;
import com.fs.datamodels.Rating;
import com.fs.utils.Endpoints;
import io.restassured.http.Method;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StoreManagerTest extends BaseTest{

    @ParameterizedTest
    @MethodSource("listOfNewProducts")
    void addNewProductstoCatalogue(List<Product> products) {
        for(Product product : products) {
            response = executeCall(Method.POST, Endpoints.PRODUCTS, null, product);
            assertAll("Product is added to catalogue successfully",
                    () -> assertEquals(product.title(), response.jsonPath().get("title")),
                    () -> assertEquals(201,response.getStatusCode())
            );
        }
    }

    static Stream<Arguments> listOfNewProducts() {
        return Stream.of(
                Arguments.of(List.of(
                    new Product("womens blouse",29.99 ,"no-frills, ready for formal occasions", "clothing", new Rating(4.9, 140)),
                    new Product("mens crew-neck t-shirt", 36.99 ,"muscle fit t-shirt suitable for summer", "clothing", new Rating(4.9, 140)),
                    new Product("mens solid blue tie",9.99 ,"classic tie to complement any formal outfit", "clothing", new Rating(4.9, 140))
                )
            )
        );
    }

    @Test
    void duplicatesCannotBeAddedToCatalogue() {
        Product dupedProduct = new Product("Mens Cotton Jacket",55.99 ,
                "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, " +
                        "husband or son in this thanksgiving or Christmas Day.",
                "men's clothing", new Rating(4.7, 500));
        response = executeCall(Method.POST, Endpoints.PRODUCTS, null, dupedProduct);

        assertEquals(400, response.statusCode(), "A duplicate product has been added to the catalogue!");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 12})
    void productsAreAvailableInTheProductList(int productID) {
        response = executeCall(Method.GET, Endpoints.PRODUCTS + productID);

        assertEquals(productID, response.jsonPath().getInt("id"));
    }


}
