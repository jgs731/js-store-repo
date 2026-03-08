package com.fs.services;

import com.fs.datamodels.Product;
import com.fs.test.ApiClient;
import com.fs.utils.Endpoints;
import io.restassured.response.Response;
import java.util.List;

public class ProductService {
    private final ApiClient client;

    public ProductService(ApiClient client) {
        this.client = client;
    }

    public Response getAllProducts() {
        return client.get(Endpoints.PRODUCTS);
    }

    public Response getProduct(int id) {
        return client.get(Endpoints.PRODUCTS + id);
    }

    public Response addProduct(Product product) {
        return client.post(Endpoints.CART, product);
    }

    public Response deleteProduct(int id) {
        return client.delete(Endpoints.PRODUCTS, id);
    }

    public Integer cheapestProductInCategoryId(String category) {
        Response response = client.get(Endpoints.PRODUCT_CATEGORY + category);
        return response.jsonPath().getList("sort {it.price}.id", Integer.class).getFirst();
    }

    public List<String> listOfProductCategories() {
        Response response = client.get(Endpoints.ALL_CATEGORIES);
        return response.then().extract().path("$");
    }

    public Integer listofProductIdsByRating() {
        Response response = client.get(Endpoints.PRODUCTS);
        return response.jsonPath().getList("sort {it.rating.rate}.id", Integer.class).getFirst();
    }


}