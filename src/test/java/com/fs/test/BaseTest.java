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

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;


class BaseTest {
    public static final Dotenv dotEnv = Dotenv.load();
    protected static RequestSpecification requestSpecification;
    protected static ResponseSpecification responseSpecification;
    static Response response;

    @BeforeAll
    static void setup() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getBaseUrl())
                .addHeader("User-Agent", "Mozilla/5.0")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .build();

        RestAssured.defaultParser = Parser.JSON;
    }


    public static Response executeCall(Method method, String endpoint, Map<String, ?> extraParams, Object bodyContent) {
        return (Response)
                given()
                .spec(requestSpecification)
                .queryParams(extraParams == null ? Map.of() : extraParams)
                .body(bodyContent == null ? "" : bodyContent)
                .request(method,endpoint)
                .then()
                .spec(responseSpecification)
                .extract();
    }

    public Response executeCall(Method method, String endpoint) {
        return executeCall(method, endpoint, null, null);
    }

    public Integer cheapestProductInCategoryId(String category){
        response = executeCall(Method.GET, Endpoints.PRODUCT_CATEGORY + category);
        return response.jsonPath().getList("sort {it.price}.id", Integer.class).getFirst();
    }

    public List<String> listOfProductCategories() {
        response = executeCall(Method.GET, Endpoints.ALL_CATEGORIES);
        return response.then().extract().path("$");
    }

    public Integer listofProductIdsByRating() {
        response = executeCall(Method.GET, Endpoints.PRODUCTS, null, null);
        return response.jsonPath().getList("sort {it.rating.rate}.id", Integer.class).getFirst();
    }


}
