package org.openindustries.media.rights;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class RightsResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"assetId\":\"11111111-1111-1111-1111-111111111111\",\"licensedUnits\":1000}")
                .when().post("/api/v1/rights")
                .then().statusCode(201).body("licensedUnits", is(1000))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"channel\":\"STREAMING\",\"units\":250}")
                .when().post("/api/v1/rights/" + id + "/uses")
                .then().statusCode(200)
                .body("remainingAfter", is(750));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"assetId\":\"11111111-1111-1111-1111-111111111111\",\"licensedUnits\":1000}")
                .when().post("/api/v1/rights")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"channel\":\"STREAMING\",\"units\":4000}")
                .when().post("/api/v1/rights/" + id + "/uses")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/rights/" + id)
                .then().statusCode(200).body("licensedUnits", is(1000));
    }
}
