package org.openindustries.media.content;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ContentAssetResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"title\":\"Northern Lights\",\"contentType\":\"FILM\",\"registryCode\":\"CT-2026-ABC12345\"}")
                .when().post("/api/v1/content")
                .then().statusCode(201)
                .body("status", is("DRAFT"))
                .body("registryCodeMasked", endsWith("2345"))
                .extract().path("id");

        given().when().get("/api/v1/content/" + id)
                .then().statusCode(200).body("status", is("DRAFT"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"title\":\"Northern Lights\",\"contentType\":\"FILM\",\"registryCode\":\"CT-2026-ABC12345\"}")
                .when().post("/api/v1/content")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/content/" + id + "/publish")
                .then().statusCode(200).body("status", is("PUBLISHED"));
    }
}
