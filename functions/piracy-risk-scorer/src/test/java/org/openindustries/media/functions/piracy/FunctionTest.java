package org.openindustries.media.functions.piracy;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"assetId\":\"ct-1\",\"leakIndex\":2,\"torrentIndex\":6,\"socialIndex\":4}")
                .when().post("/piracyRiskScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"assetId\":\"ct-2\",\"leakIndex\":88,\"torrentIndex\":79,\"socialIndex\":63}")
                .when().post("/piracyRiskScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
