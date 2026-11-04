package org.openindustries.media.programming;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ProgrammingSlotResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Friday Prime\",\"slotType\":\"PRIME_TIME\",\"durationMinutes\":120}")
                .when().post("/api/v1/programming-slots")
                .then().statusCode(201)
                .body("status", is("SCHEDULED"))
                .body("slotType", is("PRIME_TIME"))
                .extract().path("id");

        given().when().post("/api/v1/programming-slots/" + id + "/air")
                .then().statusCode(200).body("status", is("AIRED"));
    }
}
