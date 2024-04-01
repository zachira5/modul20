package apiAuto;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TestReqres {

    @Test
    public void testListUser(){
        // test case get list user (positif)
        RestAssured
                .given().when()
                .get("https://reqres.in/api/users?page=1")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("page", Matchers.equalTo(1));
    }

    @Test
    public void testListUserNotFound(){
        // test case get list user (negative)
        RestAssured
                .given().when()
                .get("https://reqres.in/api/users/23")
                .then().log().all()
                .assertThat().statusCode(404);
    }

    @Test
    public void testCreateUser(){
        // test case create user (positive) dengan post
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

    @Test
    public void testUpdate (){
        // test case update user (positive) dengan put
        RestAssured.baseURI = "https://reqres.in/";
        String userId = String.valueOf(2);
        String newName ="Mala Yoichi";

        String fname = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.first_name");
        String lname = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.last_name");
        String avatar = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.avatar");
        String email = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.email");
        System.out.println("name before ="+fname);

        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("id",userId);
        bodyMap.put("email", email);
        bodyMap.put("first_name", newName);
        bodyMap.put("last_name", lname);
        bodyMap.put("avatar", avatar);
        JSONObject jsonObject = new JSONObject(bodyMap);

        given().log().all()
                .header("Content-type", "application/json")
                .body(jsonObject.toString())
                .put("api/users/"+userId)
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("first_name", Matchers.equalTo(newName));
    }

    @Test
    public void testUpdateUser (){
        // test case update user (positive) dengan patch
        RestAssured.baseURI = "https://reqres.in/";
        String userId = String.valueOf(2);
        String newName ="Mala Fauzana";

        String fname = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.first_name");
        System.out.println("name before ="+fname);

        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("first_name", newName);

        JSONObject jsonObject = new JSONObject(bodyMap);

        given().log().all()
                .header("Content-type", "application/json")
                .body(jsonObject.toString())
                .put("api/users/"+userId)
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("first_name", Matchers.equalTo(newName));
    }

    @Test
    public void testDeleteUser (){
        // test case delete user (positive) dengan delete
        RestAssured.baseURI = "https://reqres.in/";
        String userToDelete = String.valueOf(4);

        given().log().all()
                .when().delete("api/users/"+userToDelete)
                .then().log().all()
                .assertThat().statusCode(204);
    }
}