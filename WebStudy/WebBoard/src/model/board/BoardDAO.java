package model.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.DBConn;

public class BoardDAO {

	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	BoardDAO() 
	{
		try {
			con = DBConn.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		psmt = null;
		rs = null;
	}
	
	public int insert(BoardDTO dto)
	{
		int result = 0;
		
		String sql = "INSERT INTO BOARD"
				+ " (NUM, TITLE, CONTENT, ID, POSTDATE, VISITCOUNT, TYPE)"
				+ " VALUES(SEQ_BOARD_NUM.NEXTVAL(), ?, ?, ?, SYSDATE, 0, ?)";
		
		try {
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			psmt.setString(4, dto.getType());
			
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
		
		String sql = "UPDATE BOARD"
				+ " SET TITLE = ?, CONTENT = ?"
				+ " WHERE NUM = ?";
		
		try {
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getNum());
			
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
		
		String sql = "DELETE FROM BOARD WHERE NUM = ?";
		
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
	
	public List<BoardDTO> getList()
	{
		List<BoardDTO> result = new ArrayList<BoardDTO>();
		
		String sql = "SELECT NUM, TITLE, CONTENT, ID, POSTDATE, VISITCOUNT, TYPE FROM BOARD";
		
		try {
			psmt = con.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setPostdate(rs.getString("postdate"));
				dto.setVisitcount(rs.getString("visitcount"));
				dto.setType(rs.getString("type"));
				
				result.add(dto);
			}
			
			rs.close();
			psmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<BoardDTO> getList(Map<String, String> keyword)
	{
		List<BoardDTO> result = new ArrayList<BoardDTO>();
		
		// where 절 설정용
		Set<Map.Entry<String, String>> entries = keyword.entrySet();
		int times = entries.size() - 1; // index
		
		StringBuffer sb = new StringBuffer("SELECT NUM, TITLE, CONTENT, ID, POSTDATE, VISITCOUNT, TYPE FROM BOARD WHERE ");
		
		// keyword만큼 " like ? or " 첨부. 
		for (Map.Entry<String, String> e : entries)
		{
			String str = e.getKey() + " LIKE ? ";
			
			if (times-- == 1)
			{
				sb.append(str);
				break;
			}	
			str += "OR ";
			sb.append(str);
		}
		
		try {
			psmt = con.prepareStatement(sb.toString());
			
			// "like ?"에 파라미터 입력
			int i = 1;
			for (Map.Entry<String, String> e : entries)
			{
				psmt.setString(i++, e.getValue());
			}
			
			rs = psmt.executeQuery();
			
			while(rs.next())
			{
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setPostdate(rs.getString("postdate"));
				dto.setVisitcount(rs.getString("visitcount"));
				dto.setType(rs.getString("type"));
				
				result.add(dto);
			}
			
			rs.close();
			psmt.close();
			
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
