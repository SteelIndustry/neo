package model.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBConn;

public class MemberDAO {
	
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	public MemberDAO() 
	{
		try {
			con = DBConn.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		psmt = null;
		rs = null;
	}
	
	public int insert(MemberDTO dto)
	{
		int result = 0;
		
		String sql = "INSERT INTO MEMBER (ID, PASSWORD, NAME, REGIDATE)"
				+ " VALUES (?, ?, ?, SYSDATE)";
		
		try {
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPassword());
			psmt.setString(3, dto.getName());
			
			result = psmt.executeUpdate();
			
			psmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public MemberDTO getList(MemberDTO dto)
	{
		MemberDTO result = dto;
		
		String sql = "SELECT * FROM MEMBER WHERE ID = ? AND PASSWORD = ?";
		
		try {
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPassword());
			
			rs = psmt.executeQuery();
			
			if (rs.next())
			{
				result.setName(rs.getString("name"));
				result.setRegidate(rs.getString("regidate"));
			}
			
			rs.close();
			psmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 닉네임 중복 검사
	public boolean getList(String name)
	{
		boolean result = false;
		String sql = "SELECT COUNT(*) AS COUNT FROM MEMBER WHERE NAME = ?";
		
		try {
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, name);
						
			rs = psmt.executeQuery();
			
			result = rs.next();
			
			rs.close();
			psmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
