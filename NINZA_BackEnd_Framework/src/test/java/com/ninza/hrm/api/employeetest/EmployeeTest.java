package com.ninza.hrm.api.employeetest;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import com.ninza.hrm.api.baseClass.BaseAPIClass;
import com.ninza.hrm.api.genericutility.DataBaseUtility;
import com.ninza.hrm.api.genericutility.FileUtility;
import com.ninza.hrm.api.genericutility.JavaUtility;
import com.ninza.hrm.api.pojoclass.EmployeePOJO;
import com.ninza.hrm.api.pojoclass.ProjectPojo;
import com.ninza.hrm.constants.endpoints.IEndPoints;

import io.restassured.http.ContentType;

public class EmployeeTest extends BaseAPIClass{
	
	JavaUtility jLib = new JavaUtility();
	FileUtility fLib = new FileUtility();
	DataBaseUtility dbLib = new DataBaseUtility();
	
	/**
	 * @throws SQLException
	 * @throws IOException
	 */
	@Test
	public void addEmployeTest() throws SQLException, IOException
	{
	
		String projectName = "Airtel_"+jLib.getRandomNumber();
		String userName = "user_"+jLib.getRandomNumber();
		
		//create a object to pojo class		
		//API-1 ==> add a project inside the server
		
		ProjectPojo pObj = new ProjectPojo(projectName, "Created", "Minit Singh", 0);
		
						given()
						.spec(reqSpecObj)
							.body(pObj)
						.when()
							.post(IEndPoints.ADD_PROJ)
						.then()
							.assertThat().statusCode(201)
							
							.and()
							.assertThat().body("msg", Matchers.equalTo("Successfully Added"))
							.spec(resSpecObj)
							.log().all();
	
						
						
		//API-2 = => Add employee to the same project
		
		EmployeePOJO empObj = new EmployeePOJO("Architect", "24/04/1983", "minitsingh@gmail.com", userName, 18, "0987654321", projectName, "ROLE_EMPLOYEE",userName);		
				
						given()
						.spec(reqSpecObj)
							.body(empObj)
						.when()
							.post(IEndPoints.ADD_EMP)
						.then()
					
							.assertThat().statusCode(201)
							.and()						// just used to show for multiple assertion just looks goood
							.assertThat().time(Matchers.lessThan(3000L))
							.spec(resSpecObj)
							.log().all();
						
		// verify Employee name in the DB	
//		dbLib.getDbconnection();				
//		Boolean flag =dbLib.executeQueryVerifyAndGetData("select * from employee", 5, userName);				
//		Assert.assertTrue(flag, "Employee in DB is not verified");	
//		dbLib.closeDbconnection();
						
						
	}
	
	/**
	 * @throws IOException
	 */
	@Test
	public void addEmployeeWithoutEmailTest() throws IOException
	{
		String baseUri = fLib.getDataFromPropertiesFile("BASEUri");
		
		Random random = new Random();
		int ranNum = random.nextInt(5000);
		
		String projectName = "Airtel\"+ranNum";
		String userName = "user_"+ranNum;
		
		//create a object to pojo class		
		//API-1 ==> add a project inside the server
		
		ProjectPojo pObj = new ProjectPojo(projectName, "Created", "Minit Singh", 0);
		
						given()
							.spec(reqSpecObj)
							.body(pObj)
						.when()
							.post(IEndPoints.ADD_EMP)
						.then()
						.spec(resSpecObj)
							.log().all();
	
						
						
		//API-2 ==> Add employee to the same project
		
		EmployeePOJO empObj = new EmployeePOJO("Architect", "24/04/1983", "", userName, 18, "0987654321", projectName, "ROLE_EMPLOYEE",userName);		
				
						given()
							.spec(reqSpecObj)
							.body(empObj)
						.when()
							.post(IEndPoints.ADD_EMP)
						.then()
							.assertThat().statusCode(500)
							.spec(resSpecObj)
							.log().all();
						
	}


	
	
	
	
	
	
	
}
