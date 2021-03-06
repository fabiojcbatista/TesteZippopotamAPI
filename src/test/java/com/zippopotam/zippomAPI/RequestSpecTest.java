package com.zippopotam.zippomAPI;

import static io.restassured.RestAssured.given;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestSpecTest {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification() {
    
        requestSpec = new RequestSpecBuilder().
            setBaseUri("http://api.zippopotam.us").
            build();
    }

    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
            expectStatusCode(200).
            expectContentType(ContentType.JSON).
            build();
    }

    @Test
    public void requestUsZipCode90210_checkResponseSpec_expectStatusCode200AndContetTypeApplicationJson() {

        given().
            spec(requestSpec).
        when().
            get("us/90210").
        then().
            spec(responseSpec);
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills_withRequestSpec() {

        given().
            spec(requestSpec).
        when().
            get("us/90210").
        then().
            assertThat().
            statusCode(200);
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills_withResponseSpec() {

        given().
            spec(requestSpec).
        when().
            get("us/90210").
        then().
            spec(responseSpec).
        and().
            assertThat().
            body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_extractPlaceNameFromResponseBody_assertEqualToBeverlyHills() {
    
        String placeName =
        given().
            spec(requestSpec).
        when().
            get("us/90210").
        then().
            extract().
            path("places[0].'place name'");

        Assert.assertEquals(placeName, "Beverly Hills");
    }
}

