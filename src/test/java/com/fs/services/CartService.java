package com.fs.services;

import com.fs.datamodels.Cart;
import com.fs.test.ApiClient;
import com.fs.utils.Endpoints;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.fs.test.ApiClient.executeCall;
import static io.restassured.RestAssured.requestSpecification;


public class CartService {
    private final RequestSpecification requestSpec;

    public CartService(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response getAllCarts() {
        return ApiClient.executeCall(requestSpec, Endpoints.CART);
    }

    public Response addItemToCart(Cart product) {
        return ApiClient.executeCall(requestSpec, Method.POST, Endpoints.CART,null, product);
    }

    public Response getParticularItemFromCart(int id) {
        return ApiClient.executeCall(requestSpec, Endpoints.PRODUCTS + id);
    }
}
