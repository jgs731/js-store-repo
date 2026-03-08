package com.fs.services;

import com.fs.test.ApiClient;
import com.fs.utils.Endpoints;
import io.restassured.response.Response;


public class UserService {
    ApiClient client;

    public UserService(ApiClient client) {
        this.client = client;
    }

    public Response getAllUsers() {
        return client.get(Endpoints.USERS);
    }

    public Response authenticateUser(Object userCredentials) {
        return client.post(Endpoints.AUTH, userCredentials);
    }

    public Response addUser(Object userCredentials) {
        return client.post(Endpoints.USERS, userCredentials);
    }

}
