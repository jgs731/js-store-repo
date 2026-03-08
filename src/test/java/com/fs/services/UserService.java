package com.fs.services;

import com.fs.datamodels.Auth;
import com.fs.test.ApiClient;
import com.fs.utils.Endpoints;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserService {
    private final RequestSpecification requestSpec;

    public UserService(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response getAllUsers() {
        return ApiClient.executeCall(requestSpec, Endpoints.USERS);
    }

    public Response authenticateUser(Object userCredentials) {
        return ApiClient.executeCall(requestSpec, Method.POST, Endpoints.USERS, null, userCredentials);
    }

}
