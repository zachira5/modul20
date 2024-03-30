package apiauto;

//import com.google.inject.matcher.Matchers;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;


public class testQeqres {
    @Test
    public void testGetListUsers(){
        RestAssured
                .given().when()
                .get("https://reqres.in/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("page", Matchers.equalTo(1));

    }

    @Test
    public void testPostCreateUser(){
        String valueName = "Mala";
        String valueJob = "QA";

        JSONObject bodyObj = new JSONObject();

        bodyObj.put("name", valueName);
        bodyObj.put("Job", valueJob);

        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "Application/json")
                .body(bodyObj.toString())
                .when()
                .post("https://reqres.in/api/users")
                .then().log().all()
                .assertThat().statusCode(201)
                .assertThat().body("name", Matchers.equalTo(valueName));

    }
}
