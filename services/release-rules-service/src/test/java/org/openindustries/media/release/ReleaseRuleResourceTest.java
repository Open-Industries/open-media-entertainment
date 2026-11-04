package org.openindustries.media.release;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ReleaseRuleResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"latam-standard\",\"territoryGroup\":\"LATAM\",\"maxSimultaneousReleases\":40,\"maxPiracyIncidents\":5,\"priority\":1}")
                .when().post("/api/v1/release-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/release-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"territoryGroup\":\"LATAM\",\"requiredMaxPiracyIncidents\":8,\"requiredMaxSimultaneousReleases\":20}")
                .when().post("/api/v1/release-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"territoryGroup\":\"EUROPE\",\"requiredMaxPiracyIncidents\":8,\"requiredMaxSimultaneousReleases\":20}")
                .when().post("/api/v1/release-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
