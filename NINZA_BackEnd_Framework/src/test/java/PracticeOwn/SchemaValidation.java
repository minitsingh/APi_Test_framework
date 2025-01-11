package PracticeOwn;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class SchemaValidation {

	@Test
	public void schemaValidate() {
		File file = new File("./ForProjectCreation.json");

		when().get("https://reqres.in/api/users?page=").then().assertThat()
				.body(JsonSchemaValidator.matchesJsonSchema(file));

	}

}
