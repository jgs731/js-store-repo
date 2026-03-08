package com.fs.test;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class ApiClient {

    public static Response executeCall(RequestSpecification requestSpecification, Method method,
                                String endpoint, Map<String, ?> extraParams,
                                Object bodyContent) {
        return given()
                        .spec(requestSpecification)
                        .queryParams(extraParams == null ? Map.of() : extraParams)
                        .body(bodyContent == null ? "" : bodyContent)
                        .request(method,endpoint);
    }

    public static Response executeCall(RequestSpecification requestSpecification, String endpoint) {
        return executeCall(requestSpecification, Method.GET, endpoint, null, null);
    }

}
