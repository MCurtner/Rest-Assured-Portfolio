import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class UserTest {

    final String baseUri = "https://reqres.in/api/";

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = baseUri;
    }

    @Test
    public void testListOfUsers_StatusCode() {
        RestUtils.performGet("users?page=0&per_page=100").then().assertThat().statusCode(200);
    }

    @Test
    public void testListOfUsers_TotalPages() {
        RestUtils.performGet("users?page=0&per_page=100")
                .then()
                .body("total_pages", equalTo(1));
    }

    @Test
    public void testListOfUsers_Total() {
        RestUtils.performGet("users?page=0&per_page=100")
                .then()
                .body("total", equalTo(12));
    }

    @Test
    public void testListOfUsers_VerifyParameters() {
        RestUtils.performGet(baseUri, "users?page=0&per_page=100")
                .then()
                .body("page", equalTo(1))
                .and()
                .body("per_page", equalTo(100));
    }

    @Test
    public void testGetUser_ById() {
        RestUtils.performGet(baseUri, "users/1")
                .then()
                .body("data.id", equalTo(1))
                .and()
                .body("data.email", equalTo("george.bluth@reqres.in"))
                .and()
                .body("data.first_name", equalTo("George"))
                .and()
                .body("data.last_name", equalTo("Bluth"));
    }

    @Test
    public void testLogin_Invalid() {
        String payload = "{\n" +
                "  \"username\": \"string\",\n" +
                "  \"email\": \"string\",\n" +
                "  \"password\": \"string\"\n" +
                "}";

        Map<String, String> headers = new HashMap<>();
        headers.put("accept:", "application/json");
        headers.put("Content-Type:", "application/json");

        RestUtils.performPost(baseUri, "login", payload, headers)
                .then()
                .statusCode(400);
    }

}
