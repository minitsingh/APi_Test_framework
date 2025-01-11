package forutility;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.mysql.cj.jdbc.Driver;
import com.ninza.hrm.api.baseClass.BaseAPIClass;
import com.ninza.hrm.api.pojoclass.EmployeePOJO;
import com.ninza.hrm.api.pojoclass.ProjectPojo;
import io.restassured.http.ContentType;

public class EmployeeTest extends BaseAPIClass {

	/**
	 * @throws SQLException
	 * @throws IOException
	 */
	@Test
	public void addEmployeTest() throws SQLException, IOException
	{
		String baseUri = fLib.getDataFromPropertiesFile("BASEUri");
		String projectName = "Airtel_"+jLib.getRandomNumber();
		String userName = "user_"+jLib.getRandomNumber();
		
		//create a object to pojo class		
		//API-1 ==> add a project inside the server
		
		ProjectPojo pObj = new ProjectPojo(projectName, "Created", "Minit Singh", 0);
		
						given()
							.contentType(ContentType.JSON)
							.body(pObj)
						.when()
							.post(baseUri+"/addProject")
							.then()
							.assertThat().statusCode(201)
							.and()
							.assertThat().body("msg", Matchers.equalTo("Successfully Added"))
							.log().all();
	
						
						
		//API-2 ==> Add employee to the same project
		
		EmployeePOJO empObj = new EmployeePOJO("Architect", "24/04/1983", "minitsingh@gmail.com", userName, 18, "0987654321", projectName, "ROLE_EMPLOYEE",userName);		
				
						given()
							.contentType(ContentType.JSON)
							.body(empObj)
						.when()
							.post(baseUri+"/employees")
						.then()
							.assertThat().contentType(ContentType.JSON)
							.assertThat().statusCode(201)
							.and()						// just used to show for multiple assertion just looks goood
							.assertThat().time(Matchers.lessThan(3000L))
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
							.contentType(ContentType.JSON)
							.body(pObj)
						.when()
							.post(baseUri+"/addProject")
						.then()
							.log().all();
	
						
						
		//API-2 ==> Add employee to the same project
		
		EmployeePOJO empObj = new EmployeePOJO("Architect", "24/04/1983", "", userName, 18, "0987654321", projectName, "ROLE_EMPLOYEE",userName);		
				
						given()
							.contentType(ContentType.JSON)
							.body(empObj)
						.when()
							.post(baseUri+"/employees")
						.then()
							.assertThat().contentType(ContentType.JSON)
							.assertThat().statusCode(500)
							.log().all();
						
	}

	
	
	
	
	
	
	
	
}
