import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class RestUtils {

    public static Response performGet(String endPoint) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .get(endPoint)
                .then()
                .extract().response();
    }

    public static Response performGet(String baseUri, String endPoint) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .get(endPoint)
                .then()
                .extract().response();
    }

    public static Response performPost(String baseUri, String endpoint, String payload, Map<String, String> headers) {
        return RestAssured.given()
                .baseUri(baseUri)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(payload)
                .post(endpoint)
                .then()
                .extract().response();
    }
}
