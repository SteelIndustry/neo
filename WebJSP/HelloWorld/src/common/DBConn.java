package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	private static Connection dbConn;
	
	public static Connection getConnection() 
	{
		if (dbConn == null)
		{
			try 
			{
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "scott";
			String password = "tiger";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			dbConn = DriverManager.getConnection(url, user, password);				
			
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return dbConn;
	}
	
	public static Connection getConnection(String url, String user, String password) 
	{
		if (dbConn == null)
		{
			try 
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				dbConn = DriverManager.getConnection(url, user, password);
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return dbConn;
	}
	
	public static void close() 
	{
		if (dbConn != null)
		{
			try {
				if (!dbConn.isClosed())
				{
					dbConn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		dbConn = null;
	}
}
