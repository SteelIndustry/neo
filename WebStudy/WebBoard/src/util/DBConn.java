package util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConn {
	
	private static DataSource ds;
	
	public static Connection getConnection() throws SQLException 
	{
		try {
			if (ds == null)
			{
				Context context = new InitialContext();
				ds = (DataSource) context.lookup("java:comp/env/board_dbcp");
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ds.getConnection();
	}
}
