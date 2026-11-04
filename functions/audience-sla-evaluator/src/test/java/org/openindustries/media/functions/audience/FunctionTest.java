package org.openindustries.media.functions.audience;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"assetId\":\"ct-1\",\"measuredBufferingEvents\":12,\"targetBufferingEvents\":50,\"measuredCompletionRatePct\":78.0,\"targetCompletionRatePct\":70.0}")
                .when().post("/audienceSlaEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"assetId\":\"ct-2\",\"measuredBufferingEvents\":240,\"targetBufferingEvents\":50,\"measuredCompletionRatePct\":78.0,\"targetCompletionRatePct\":70.0}")
                .when().post("/audienceSlaEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
