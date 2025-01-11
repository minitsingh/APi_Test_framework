package com.ninza.hrm.api.pojoclass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class ProgramToConnectDB {
	
	public static void main(String[] args) throws SQLException {
		Driver driverRef = new Driver();
		DriverManager.registerDriver(driverRef);
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ninza_hrm","root","root");
		ResultSet result = con.createStatement().executeQuery("Query");
		while(result.next())
		{
			System.out.println(result.getString(1));
		}
		
		con.close();
	}

}
