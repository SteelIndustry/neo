package model1.board;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import common.JDBConnect;

public class BoardDAO extends JDBConnect{
	public BoardDAO(ServletContext application) {
		super(application);		
	}
	
	// 검색 조건에 맞는 게시물의 개수를 반환
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		
		// 게시물 수를 얻어오는 쿼리문 작성
		String query = "SELECT COUNT(*) FROM BOARD";
		if (map.get("searchWord") != null)
		{
			query += " WHERE " + map.get("searchField") + " "
				   + " LIKE '%" + map.get("searchWord") + "%'";
		}
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	// 검색 조건에 맞는 게시물 목록을 반환
	public List<BoardDTO> selectList(Map<String, Object> map) {
		List<BoardDTO> bbs = new ArrayList<BoardDTO>();	// 게시물 목록을 담을 변수
		
		String query = "SELECT * FROM board";
		if (map.get("searchWord") != null)
		{
			query += " WHERE " + map.get("searchField") + " "
				   + " LIKE '%" + map.get("searchWord") + "%' ";
		}
		
		query += " ORDER BY num DESC ";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				bbs.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		return bbs;
	}
	
	// 검색 조건에 맞는 게시물 목록을 반환(페이징 기능 지원)
	public List<BoardDTO> selectListPage(Map<String, Object> map) {
		List<BoardDTO> bbs = new ArrayList<BoardDTO>();
		
		String query = "SELECT *"
				+ " FROM ( SELECT TB.*"
						+ ", ROWNUM RNUM"
						+ " FROM ( SELECT *"
								+ " FROM BOARD";
		if (map.get("searchWord") != null)
		{
			query += " WHERE "+ map.get("searchField") + " LIKE '%"+ map.get("searchWord") +"%' ";
		}
		
		query += " ORDER BY NUM DESC ) TB"
				+ " )"
				+ " WHERE RNUM BETWEEN ? AND ?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			
			rs = psmt.executeQuery();
			
			while (rs.next())
			{
				BoardDTO dto = new BoardDTO();
                dto.setNum(rs.getString("num"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString("visitcount"));

                // 반환할 결과 목록에 게시물 추가
                bbs.add(dto);
			}
			
		} catch (SQLException e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		return bbs;
	}
	
	// 게시글 데이터를 받아 DB 추가
	public int insertWrite(BoardDTO dto) {
		int result = 0;
		
		String query = "INSERT INTO BOARD ( "
				+ " NUM, TITLE, CONTENT, ID, VISITCOUNT) "
				+ " VALUES ( "
				+ " SEQ_BOARD_NUM.NEXTVAL, ?, ?, ?, 0)";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			
			result = psmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(" 게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 지정한 게시물을 찾아 내용을 반환합니다.
	public BoardDTO selectView(String num) {
		BoardDTO dto = new BoardDTO();
		
		String query = "SELECT B.*, M.NAME"
				+ " FROM MEMBER M INNER JOIN BOARD B"
				+ " ON M.ID=B.ID"
				+ " WHERE NUM=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,  num);
			rs = psmt.executeQuery();
			
			if (rs.next())
			{
				dto.setNum(rs.getString(1)); 
                dto.setTitle(rs.getString(2));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setId(rs.getString("id"));
                dto.setVisitcount(rs.getString(6));
                dto.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		
		return dto;
	}
	
	// 지정한 게시물의 조회수를 1증가시킵니다.
	public void updateVisitCount(String num) {
		String query = "UPDATE BOARD"
				+ " SET VISITCOUNT=VISITCOUNT+1"
				+ " WHERE NUM=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			psmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	// 지정한 게시물을 수정합니다.
	public int updateEdit(BoardDTO dto)
	{
		int result = 0;
		
		String query = "UPDATE BOARD"
				+ " SET TITLE=?, CONTENT=?"
				+ " WHERE NUM=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getNum());

			result = psmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int deletePost(BoardDTO dto) {
		int result = 0;
		
		String query = "DELETE"
				+ " FROM BOARD"
				+ " WHERE NUM=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getNum());
			
			// 쿼리문 실행
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("게시물 삭제 중 예외 발생");
			e.printStackTrace();
		}
		
		return result;
	}
}
