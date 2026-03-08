package com.fs.services;

import com.fs.datamodels.Cart;
import com.fs.datamodels.Product;
import com.fs.test.ApiClient;
import com.fs.utils.Endpoints;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static com.fs.test.ApiClient.executeCall;
import java.util.List;

public class ProductService {

    private final RequestSpecification requestSpec;

    public ProductService(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response getAllProducts() {
        return executeCall(requestSpec, Endpoints.PRODUCTS);
    }

    public Response getProduct(int id) {
        return executeCall(requestSpec, Endpoints.PRODUCTS + id);
    }

    public Response addProduct(Product product) {
        return executeCall(requestSpec, Method.POST, Endpoints.CART,null, product);
    }

    public Response deleteProduct(int id) {
        return ApiClient.executeCall(requestSpec, Method.DELETE, Endpoints.PRODUCTS + id, null, null);
    }

    public Integer cheapestProductInCategoryId(String category) {
        Response response = executeCall(requestSpec, Endpoints.PRODUCT_CATEGORY + category);
        return response.jsonPath().getList("sort {it.price}.id", Integer.class).getFirst();
    }

    public List<String> listOfProductCategories() {
        Response response = executeCall(requestSpec, Endpoints.ALL_CATEGORIES);
        return response.then().extract().path("$");
    }

    public Integer listofProductIdsByRating() {
        Response response = executeCall(requestSpec, Endpoints.PRODUCTS);
        return response.jsonPath().getList("sort {it.rating.rate}.id", Integer.class).getFirst();
    }


}