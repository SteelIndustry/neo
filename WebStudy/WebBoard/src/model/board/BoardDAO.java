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
	
	
	public List<BoardDTO> getList(Map<String, String> searchMap, Map<String, String> infoMap)
	{
		List<BoardDTO> result = new ArrayList<BoardDTO>();
		String[] category = null; // ?????? ??????
		
		StringBuffer sb = new StringBuffer(
				"SELECT NUM, TITLE, CONTENT, ID, NAME, POSTDATE, VISITCOUNT"
				+ ", FILENAME, SAVEDNAME"
				+ " FROM ( SELECT BI.*, ROW_NUMBER() OVER(ORDER BY NUM DESC) RN"
						+ " FROM " + viewName + " BI ");
		
		if (infoMap.get("category") != null)
		{
			category = infoMap.get("category").split(",");
			int count = category.length;
			
			// WHERE ??? ?????????
			sb.append(" WHERE ");
			for (int i=0; i<count; i++)
			{
				sb.append(category[i]);
        		sb.append(" LIKE '%'||?||'%' ");
				if (i != count-1)
					sb.append("AND ");
			}
		}
		sb.append(") WHERE RN BETWEEN ? AND ?");
		
		try {
			psmt = con.prepareStatement(sb.toString());
			
			// "like ?"??? ???????????? ??????
			int i = 1; // ???????????? ?????????
			
			if (category != null)
			{
				for (String c : category)
					psmt.setString(i++, searchMap.get(c));				
			}
			
			// ??? ???????????? ??? ??? ??????????????? ??? ????????? ????????????... (BETWEEN ? AND ?)
			// keyword ????????? ?????? 1?????????
			// keyword ????????? ?????? like ???????????? ?????? ???????????? ???????????? ?????? 
			psmt.setString(i++, infoMap.get("start"));
			psmt.setString(i, infoMap.get("end"));
			
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
	
	
	public int countList(Map<String, String> searchMap, Map<String, String> infoMap)
	{
		int result = 0;
		String[] category = null; // ?????? ??????
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) COUNT FROM " + viewName);
		
		if (infoMap.get("category") != null)
		{
			category = infoMap.get("category").split(",");
			int count = category.length;
			
			// WHERE ??? ?????????
			sb.append(" WHERE ");
			for (int i=0; i<count; i++)
			{
				sb.append(category[i]);
        		sb.append(" LIKE '%'||?||'%' ");
				if (i != count-1)
					sb.append("AND ");
			}
		}
		
		try {
			psmt = con.prepareStatement(sb.toString());
			
			// "like ?"??? ???????????? ??????
			if (category != null)
			{
				int i = 1;
				for (String c : category)
					psmt.setString(i++, searchMap.get(c));				
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
