package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;

public class JDBConnect {
	public Connection con;
	public Statement stmt;
	public PreparedStatement psmt;
	public ResultSet rs;
	
	public JDBConnect()
	{
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "scott";
			String password = "tiger";
			
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
			
			System.out.println("DB 연결 성공(기본 생성자)");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JDBConnect(String driver, String url, String user, String password)
	{
		try {
			Class.forName(driver);
			
			con = DriverManager.getConnection(url, user, password);
			
			System.out.println("DB 연결 성공(인수 생성자 1)");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JDBConnect(ServletContext application)
	{
		try {
			String driver = application.getInitParameter("OracleDriver");
			Class.forName(driver);
			
			String url = application.getInitParameter("OracleURL");
			String user = application.getInitParameter("OracleUser");
			String password = application.getInitParameter("OraclePassword");
			con = DriverManager.getConnection(url, user, password);
			
			System.out.println("DB 연결 성공(인수 생성자 2)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (psmt != null) psmt.close();
			
			if (con != null)
			{
				if (!con.isClosed())
					con.close();
			}
			rs = null;
			stmt = null;
			psmt = null;
			con = null;
			
			System.out.println("JDBC 자원 해제");
		} catch (Exception e) {
			
		}
	}
	
}
