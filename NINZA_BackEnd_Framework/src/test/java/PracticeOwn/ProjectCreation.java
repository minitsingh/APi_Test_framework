package PracticeOwn;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class ProjectCreation {
	
	@Test
	public void projectCreation()
	{
		String requestBody= "{\r\n"
				+ "  \"createdBy\": \"Thakur Rana Pratap\",\r\n"
				+ "  \"projectName\": \"BekarProject\",\r\n"
				+ "  \"status\": \"Created\",\r\n"
				+ "  \"teamSize\": 0\r\n"
				+ "}";
		
		given()
		.contentType(ContentType.JSON)
		.body(requestBody)
	.when()
		.post("http://49.249.29.5:8091/addProject")
	.then()	
		.log().all();

	
		
		
		
		
	}

}
