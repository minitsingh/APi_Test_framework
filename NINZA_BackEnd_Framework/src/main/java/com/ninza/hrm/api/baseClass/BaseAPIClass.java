package com.ninza.hrm.api.baseClass;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ninza.hrm.api.genericutility.DataBaseUtility;
import com.ninza.hrm.api.genericutility.FileUtility;
import com.ninza.hrm.api.genericutility.JavaUtility;

import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAPIClass {
	
	public JavaUtility jLib = new JavaUtility();
	public FileUtility fLib = new FileUtility();
	public DataBaseUtility dbLib = new DataBaseUtility();
	public static RequestSpecification reqSpecObj;
	public static ResponseSpecification resSpecObj;
	@BeforeSuite
	public void configBS() throws SQLException, IOException
	{
//		dbLib.getDbconnection();	
		System.out.println("==========connecting to db========");
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setContentType(ContentType.JSON);
//		reqBuilder.setAuth(basic("username", "password"));
//		reqBuilder.addHeader(DEFAULT_PATH, "");
		reqBuilder.setBaseUri(fLib.getDataFromPropertiesFile("BASEUri"));
		reqSpecObj = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectContentType(ContentType.JSON);
		resSpecObj = resBuilder.build();
		
		
		
		
		
		
	}
	
	@AfterSuite
	public void configAS() throws SQLException
	{
//		dbLib.closeDbconnection();
		System.out.println("=========  DB Closed ========");
		
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
}
