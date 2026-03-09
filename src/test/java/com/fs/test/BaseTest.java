package com.fs.test;

import com.fs.config.ConfigLoader;
import com.fs.utils.Endpoints;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;


class BaseTest {
    protected static RequestSpecification requestSpecification;
    protected static ResponseSpecification responseSpecification;
    public Response response;

    @BeforeEach
    void setup() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .build();
    }
}
