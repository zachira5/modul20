package apiAuto;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestTugas18 {
    @Test
    public void testPostLoginUnsuccessful(){
        // ini test case negative
        String valueEmail = "peter@klaven";
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("email", valueEmail);
        given()
                .header("Content-Type", "application/json")
                .header("Accept", "Application/json")
                .body(bodyObj.toString())
                .when()
                .post("https://reqres.in/api/login")
                .then().log().all()
                .assertThat().statusCode(400)
                .assertThat().body("email", Matchers.equalTo(valueEmail));
    }

    @Test
    public void testPostLoginSuccessful(){
        // ini test case positive
        String valueEmail = "eve.holt@reqres.in";
        String valuePassword = "cityslicka";
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("email", valueEmail);
        bodyObj.put("pass", valuePassword);
        given()
                .header("Content-Type", "application/json")
                .header("Accept", "Application/json")
                .body(bodyObj.toString())
                .when()
                .post("https://reqres.in/api/login")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("email", Matchers.equalTo(valueEmail))
                .assertThat().body("pass", Matchers.equalTo(valuePassword));
    }

    @Test
    public void testGetDelay(){
        given().when()
                .get("https://reqres.in/api/users?delay=3")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("page", Matchers.equalTo(1));
    }
}
