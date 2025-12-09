package com.cjc.bms.DbConfiger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbConnection {
 
	public static Connection getConnection() {
		Connection con=null;
		// step 1 Load the Database Driver class
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		
		//step 2 Establish the connection url, uname, pass
		
		 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Bank","root","1234");
		
	
		}
		catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return con;
	}

}
