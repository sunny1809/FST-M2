package m2_class;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {
	
	String ROOT_URI = "https://petstore.swagger.io/v2/user";
	
  @Test(priority=1)
  public void postRequest() throws IOException {
	  
	  FileInputStream inputJSON = new FileInputStream("src/test/java/m2_class/userinfo.json");

      String reqBody = new String(inputJSON.readAllBytes());

      Response response = 
          given().contentType(ContentType.JSON)
          .body(reqBody).when().post(ROOT_URI);

      inputJSON.close();

      response.then().body("code", equalTo(200));
      response.then().body("message", equalTo("9804"));

  }
  
  @Test(priority=2)
  public void getRequest() {
	  
	  File outputJSON = new File("src/test/java/m2_class/userGETResponse.json");

      Response response = 
          given().contentType(ContentType.JSON).pathParam("username", "AbhijitD")
          .when().get(ROOT_URI + "/{username}");
      
      String resBody = response.getBody().asPrettyString();

      try {
          outputJSON.createNewFile();
          FileWriter writer = new FileWriter(outputJSON.getPath());
          writer.write(resBody);
          writer.close();
      } catch (IOException excp) {
          excp.printStackTrace();
      }
      
      response.then().body("id", equalTo(9804));
      response.then().body("username", equalTo("AbhijitD"));
      response.then().body("firstName", equalTo("Abhijit"));
      response.then().body("lastName", equalTo("Dey"));
      response.then().body("email", equalTo("abhijitdey@mail.com"));
      response.then().body("password", equalTo("password123"));
      response.then().body("phone", equalTo("9876543210"));
  }
  
  
  @Test(priority=3)
  public void deleteRequest() {
	  
	  Response response = given().contentType(ContentType.JSON).pathParam("username", "AbhijitD")
	            .when().delete(ROOT_URI + "/{username}");

	        response.then().body("code", equalTo(200));
	        response.then().body("message", equalTo("AbhijitD"));

  }
  
}
