package m2_class;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.equalTo;

public class RestAssuredProject {

	String ROOT_URI = "https://api.github.com";

	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	
	int sshId;

	@BeforeClass
	public void setUp() {
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.addHeader("Authorization", "token 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")
				.setBaseUri(ROOT_URI).build();
	}

	@Test(priority = 1)
	public void postRequest() {

		String reqBody = "{" + "\"title\": \"TestAPIKey\","
				+ " \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDhvfl48u/bQTLHBr7yWkjONYNFlHZ5XnLK4kV7WVBniy93O+SAgtUdkFcSDBGdYX+SDTmkPwVcsAys3cvEUvv5JT9kP3j5SZaq0aXi7duFCBCUYTnUYQiRk7sqm0SAdFjJyiotH2q4EBGwyKa9PrO5pif1tT1GbcJnUJZGQNpJTp3i/PpU8CqXNdf0J9+sOkFyBXPomSj6TVys7NQ+I/Gn/3h5vz5sSf1mwphOM8xVUzXYKz5WI1caE0Ie34xxD7M/nYv0FPjnZpPKSXL6qTF92E/GsdfjHbd289YlpghsLPxphHZFhf3tE7/QUew/9EFszbP08I+EhAW0UIs3BTLQn6rXDqdlxxhXerJw9xqovYWzsmb5AECrz2leoerVUhH1iwmvQl99nOr/n0gmgPsPIf0y38BxLj83Fq9J4NlYAkUcZM3pY32uToKeA3tQJYlw9zk2cBTczdtd16CkMHr1KIwGvBBexFo93XSTYbscoU9xcmUnoXpjF60U1Oxkj7M=\""
				+ "}";

		Response response = given().spec(requestSpec).body(reqBody).when().post("/user/keys");
		
		sshId = response.then().extract().path("id");

		String responseBody = response.getBody().asPrettyString();

		System.out.println("Response Body has :" + responseBody);
		
		System.out.println("SSHID value is =>> "+sshId);
		
		System.out.println("<<=========================================================================>>");
		
		response.then().statusCode(201);
	
	}


	@Test(priority = 2)
	public void getRequest() {

		Response response = given().spec(requestSpec).pathParam("keyId", sshId).when()
				.get("/user/keys/{keyId}");

		System.out.println(response.asPrettyString());

		response.then().statusCode(200);

	}

	@Test(priority = 3)
	public void deleteRequest() {

		Response response = given().spec(requestSpec).pathParam("keyId", sshId)
				.when().delete("/user/keys/{keyId}");

		Reporter.log(response.asString());

		response.then().statusCode(204);

	}

}
