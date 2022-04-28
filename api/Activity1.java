package m2_class;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity1 {
	
	String ROOT_URI = "https://petstore.swagger.io/v2/pet";
	  
  @BeforeClass
  public void postRequest() {
	  
	  String reqBody = "{"
	            + "\"id\": 77232,"
	            + "\"name\": \"Riley\","
	            + " \"status\": \"alive\""
	        + "}";

	        Response response = given().contentType(ContentType.JSON)
	            .body(reqBody).when().post(ROOT_URI);
	        
	        response.then().body("id", equalTo(77232));
	        response.then().body("name", equalTo("Riley"));
	        response.then().body("status", equalTo("alive"));

  }
  

	@Test
	public void getRequest() {

		Response response = given().contentType(ContentType.JSON).when().pathParam("petId", "77232")
				.get(ROOT_URI + "/{petId}");

		response.then().body("id", equalTo(77232));
		response.then().body("name", equalTo("Riley"));
		response.then().body("status", equalTo("alive"));

	}


  @AfterClass
  public void deleteRequest() {
	  
	  Response response = given().contentType(ContentType.JSON).when().pathParam("petId", "77232")
	            .delete(ROOT_URI + "/{petId}");

	        response.then().body("code", equalTo(200));
	        response.then().body("message", equalTo("77232"));

  }

}
