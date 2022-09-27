package model.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.DBConn;

public class BoardDAO {

	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	private String tableName;
	private String viewName;
	
	public BoardDAO() 
	{
		try {
			con = DBConn.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		psmt = null;
		rs = null;
	}
	
	public BoardDAO(String name)
	{
		try {
			con = DBConn.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		psmt = null;
		rs = null;
		tableName = name;
		viewName = name + "_INFO";
	}
	
	public int insert(BoardDTO dto)
	{
		int result = 0;
		
		String sql = "INSERT INTO " + tableName
				+ " (NUM, TITLE, CONTENT, ID, POSTDATE, VISITCOUNT, FILENAME, SAVEDNAME)"
				+ " VALUES(SEQ_"+tableName+"_NUM.NEXTVAL, ?, ?, ?, SYSDATE, 0, ?, ?)";
		
		try {
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			psmt.setString(4, dto.getFileName());
			psmt.setString(5, dto.getSavedName());
			
			result = psmt.executeUpdate();
			
			psmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int update(BoardDTO dto)
	{
		int result = 0;
		
		String sql = "UPDATE " +tableName
				+ " SET TITLE = ?, CONTENT = ?, FILENAME = ?, SAVEDNAME = ?"
				+ " WHERE NUM = ?";
		
		try {
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getFileName());
			psmt.setString(4, dto.getSavedName());
			psmt.setString(5, dto.getNum());
			
			result = psmt.executeUpdate();
			
			psmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int delete(String num)
	{
		int result = 0;
		
		String sql = "DELETE FROM "+tableName+" WHERE NUM = ?";
		
		try {
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, num);
			
			result = psmt.executeUpdate();
			
			psmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<BoardDTO> getList(String num)
	{
		List<BoardDTO> result = new ArrayList<BoardDTO>();
		
		String sql = "SELECT NUM, TITLE, CONTENT, ID, NAME"
				+ ", POSTDATE, VISITCOUNT, FILENAME, SAVEDNAME"
				+ " FROM "+viewName
				+ " WHERE NUM = ?";
		
		try {
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, num);
			
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("NAME"));
				dto.setPostdate(rs.getString("postdate"));
				dto.setVisitcount(rs.getString("visitcount"));
				dto.setFileName(rs.getString("filename"));
				dto.setSavedName(rs.getString("savedname"));
				
				result.add(dto);
			}
			
			rs.close();
			psmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public List<BoardDTO> getList(Map<String, String> info)
	{
		List<BoardDTO> result = new ArrayList<BoardDTO>();
		
		String keyword = info.get("keyword");
		int count = 0; // 파라미터 count 용
		
		String sql = "SELECT NUM, TITLE, CONTENT, ID, NAME, POSTDATE, VISITCOUNT"
				        + ", FILENAME, SAVEDNAME"
				  + " FROM"
				  + " ( SELECT BI.*, ROW_NUMBER() OVER(ORDER BY NUM DESC) RN "
				    + " FROM "+viewName+" BI ";
		
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		
		if (keyword != null)
		{
			// where 절 설정용
			String[] category = info.get("category").split(",");
			count = category.length; // 검색 키워드 존재할 경우 count에 값 부여
			
			sb.append("WHERE ");
			
			// keyword만큼 " like ? or " 첨부. 
			for (int i=0; i<category.length; i++)
			{
				String str = category[i] + " LIKE '%'||?||'%'";
				if (i != category.length -1)
				{
					str += "OR ";
				}
				sb.append(str);
			}
		}
		
		sb.append(") WHERE RN BETWEEN ? AND ?");
		
		try {
			psmt = con.prepareStatement(sb.toString());
			
			// "like ?"에 파라미터 입력
			for (int i=1; i<=count; i++)
			{
				psmt.setString(i, keyword);
			}
			
			// 한 페이지에 몇 번 게시물부터 몇 번까지 출력할지... (BETWEEN ? AND ?)
			// keyword 없었을 경우 1번부터
			// keyword 있었을 경우 like 파라미터 이후 번호부터 파라미터 입력 
			count = keyword != null ? count+1 : 1;
			psmt.setString(count++, info.get("start"));
			psmt.setString(count, info.get("end"));
			
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("NAME"));
				dto.setPostdate(rs.getString("postdate"));
				dto.setVisitcount(rs.getString("visitcount"));
				dto.setFileName(rs.getString("filename"));
				dto.setSavedName(rs.getString("savedname"));
				
				result.add(dto);
			}
			
			rs.close();
			psmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public int countList(Map<String, String> info)
	{
		int result = 0;
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) AS COUNT FROM " + viewName);
		String keyword = info.get("keyword");
		int count = 0; // keyword 개수 세기
		
		if (keyword != null)
		{
			// where 절 설정용
			String[] category = info.get("category").split(",");
			
			// keyword 개수 입력
			count = category.length;
			
			sb.append(" WHERE ");
			
			// keyword만큼 " like ? or " 첨부. 
			for (int i=0; i<category.length; i++)
			{
				String str = category[i] + " LIKE '%'||?||'%'";
				if (i != category.length -1)
				{
					str += "OR ";
				}
				sb.append(str);
			}
		}
		
		try {
			psmt = con.prepareStatement(sb.toString());
			
			// "like ?"에 파라미터 입력
			for (int i=1; i<=count; i++)
			{
				psmt.setString(i, keyword);
			}
			
			rs = psmt.executeQuery();
			
			if (rs.next())
				result = rs.getInt("COUNT");
			
			rs.close();
			psmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int checkWriter(String num, String id)
	{
		int result = 0;
		
		String sql = "SELECT COUNT(*) COUNT"
				+ " FROM "+viewName
				+" WHERE NUM=? AND ID = ?";
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, num);
			psmt.setString(2, id);
			
			rs = psmt.executeQuery();
			
			if (rs.next())
				result = rs.getInt("COUNT");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void close()
	{
		if (con != null)
		{
			try {
				if (!con.isClosed())
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con = null;
		}
	}
}
