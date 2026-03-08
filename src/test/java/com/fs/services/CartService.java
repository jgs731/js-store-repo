package com.fs.services;

import com.fs.datamodels.Cart;
import com.fs.test.ApiClient;
import com.fs.utils.Endpoints;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class CartService {
    ApiClient client;

    public CartService(ApiClient client) {
        this.client = client;
    }

    public Response getAllCarts() {
        return client.get(Endpoints.CART);
    }

    public Response addItemToCart(Cart product) {
        return client.post(Endpoints.CART, product);
    }

    public Response getParticularItemFromCart(int id) {
        return client.get(Endpoints.PRODUCTS + id);
    }
}
