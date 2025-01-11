package com.ninza.hrm.api.projecttest;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.ninza.hrm.api.baseClass.BaseAPIClass;
import com.ninza.hrm.api.pojoclass.ProjectPojo;
import com.ninza.hrm.constants.endpoints.IEndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import java.io.IOException;
import java.sql.SQLException;

public class ProjectTest extends BaseAPIClass{

	ProjectPojo pObj;

	@Test
	public void addSingleProjectWithCreated() throws SQLException, IOException {

		String baseUri = fLib.getDataFromPropertiesFile("BASEUri");
		
		// for verification of project name in database stored the name
		String projectName = "kiwi" + jLib.getRandomNumber();
		String expSucMsg = "Successfully Added";

		// create a object to pojo class
		pObj = new ProjectPojo(projectName, "Created", "Minit Singh", 0);

		// verify the projectName in the API layer

		Response resp = given()
						.spec(reqSpecObj)
							.body(pObj).
						when()
							.post(baseUri+IEndPoints.ADD_PROJ);
						resp.then()
								.assertThat().statusCode(201)
								.assertThat().time(Matchers.lessThan(3000L))
								.spec(resSpecObj)
								.log().all();

		String actSucMsg = resp.jsonPath().getString("msg");
		Assert.assertEquals(expSucMsg, actSucMsg);

		
		  //verify the projectName in the DB layer

//		Boolean flag =dbLib.executeQueryVerifyAndGetData("select * from project", 4, projectName);
//	    Assert.assertTrue(flag,"Project in DB is not verified");

		 
	}

	/**
	 * made the project name global so that it could be available in another tc also
	 * and we can use it and save the code length without again creating the project
	 * again same done with the pojo class object made it global so we can use it
	 * again because we are trying to to create the project with the help of pojoo
	 * class so global
	 * 
	 * other thing if we only make the pojo class global that much only is enough as
	 * we'll create the object to it so here we did that only
	 * @throws IOException 
	 */

	@Test(dependsOnMethods = "addSingleProjectWithCreated")
	public void addDuplicateProject() throws IOException {

		String baseUri = fLib.getDataFromPropertiesFile("BASEUri");
		
		given()
			.spec(reqSpecObj)
			.body(pObj)
		.when()
			.post(baseUri+IEndPoints.ADD_PROJ)
		.then()
			.assertThat().statusCode(409)
			.spec(resSpecObj)
			.log().all();

	}
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}





