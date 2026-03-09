package com.fs.test;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class ApiClient {
    private final RequestSpecification spec;

    public ApiClient(RequestSpecification spec) {
        this.spec = spec;
    }

    public Response get(String endpoint) {
        return given()
                .spec(spec)
                .when()
                .get(endpoint);
    }

    public Response post(String endpoint, Object body) {
        return given()
                .spec(spec)
                .body(body)
                .when()
                .post(endpoint);
    }

    public Response patch(String endpoint, Object body) {
        return given()
                .spec(spec)
                .body(body)
                .when()
                .patch(endpoint);
    }

    public Response delete(String endpoint, Object body) {
        return given()
                .spec(spec)
                .body(body)
                .when()
                .delete(endpoint);
    }

}
