package com.ninza.hrm.api.genericutility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class DataBaseUtility {
	
	static Connection con = null;
	static ResultSet result = null;
	static FileUtility fLib = new FileUtility();
	
	public static void getDbconnection() throws SQLException, IOException
	{
		Driver driverRef;
		try {

			driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			con = DriverManager.getConnection(fLib.getDataFromPropertiesFile("DBUrl"),
					fLib.getDataFromPropertiesFile("DB_Username"), fLib.getDataFromPropertiesFile("DB_Password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void closeDbconnection() throws SQLException {

		try {
			con.close();
		} catch (Exception e) {

		}
	}

	public static ResultSet executeConSelectQuery(String query) throws SQLException {

		try {
			Statement stat = con.createStatement();
			result = stat.executeQuery(query);
		} catch (Exception e) {

		}
		return result;
	}

	public int executeNonSelectQuery(String query) {
		int result = 0;
		try {
			Statement stat = con.createStatement();
			result = stat.executeUpdate(query);
		} catch (Exception e) {

		}
		return result;
	}
		
	
	public  boolean executeQueryVerifyAndGetData(String query, int columnIndex , String expectedData) throws SQLException
	{
		boolean flag = false;
		result = con.createStatement().executeQuery(query);
		
		while(result.next())
		{
			if(result.getString(columnIndex).equals(expectedData))
			{
				flag = true;
				break;
			}
		}
		
		if(flag)
		{
			System.out.println(expectedData + "==> data verified in the DataBase table");
			return true;
		}
		else
		{
			System.out.println(expectedData + "==> data is not verified in the DataBase table");
			return false;
		}
	}
		
		
  }


