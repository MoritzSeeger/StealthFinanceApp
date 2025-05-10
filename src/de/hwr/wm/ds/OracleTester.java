package de.hwr.wm.ds;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hwr.wm.ds.OracleDsSingleton;

public class OracleTester {

	public static void main(String[] args) {
		OracleDsSingleton ora = OracleDsSingleton.getInstance();
		try {
			Connection con = ora.getConnection();
			System.out.println("Creating statement...");
		      Statement stmt = con.createStatement();
		      String sql;
		      sql = "SELECT * FROM CUSTOMER";
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){
		         //Retrieve by column name
		    	  String last = rs.getString("CUSTOMER_ID");
		    	  String first = rs.getString("USER_NAME");
		    	  String pass = rs.getString("PASSWORT");

		         //Display values
		         System.out.println("Name: " + first + " " + last +
		        		 " Passwort: " + pass);
		      }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
