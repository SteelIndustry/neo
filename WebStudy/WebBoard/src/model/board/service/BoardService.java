package model.board.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import model.board.BoardDAO;
import model.board.BoardDTO;
import util.Paging;
import util.TableName;

public class BoardService implements IService {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		int totalCount; // 총 게시글 개수
		int[] pageRange; // 한 페이지 내 게시물 범위
		String query = null; // 게시판 타입 및 검색어 조건을 query 형식으로 저장할 String
		String tableName; // 참조 테이블 이름
		
		// 검색어 조건 종류
		String[] categoryList = {"title", "content"};
		
		// 검색어
		String keyword = request.getParameter("keyword");
		
		// tableName
		String type = request.getParameter("type") != null ? request.getParameter("type") : "free"; 
		tableName = TableName.getTableName(type);
		
		// 현재 페이지 번호
		int pageNum = request.getParameter("pageNum") != null 
				? Integer.parseInt(request.getParameter("pageNum")) : 1;
		
		BoardDAO dao = new BoardDAO(tableName);
		List<BoardDTO> lists = new ArrayList<BoardDTO>();
		
		// 검색, 게시물 출력 관련 정보 담을 map
		Map<String, String> infoMap = new HashMap<String, String>();
		
		if (keyword != null)
		{
			// dao 전달용
			infoMap.put("keyword", keyword);
			
			String category = "";
			
			query = "";
			for (String c : categoryList)
			{
				String value = (String) request.getParameter(c);
				if (value != null)
				{
					category += value + ",";
					query += "&" + c +"="+value;
				}
					
			}
			category = category.substring(0, category.length()-1); // 마지막 ',' 제거
			query += "&keyword=" +keyword;
			
			request.setAttribute("category", category); // 검색 조건 유지를 위해서 키워드 저장
			request.setAttribute("keyword", keyword);
			
			infoMap.put("category", category);
		}
		
		// 전체 row 개수
		totalCount = dao.countList(infoMap);
		
		// 페이징 정보 추출
		Paging paging = new Paging(totalCount, pageNum);
		pageRange = paging.getPageRange();
		
		// 한 페이지 내 출력할 게시물의 시작 번호 및 끝 번호
		infoMap.put("start", String.valueOf(pageRange[0]));
		infoMap.put("end", String.valueOf(pageRange[1]));
		
		lists = dao.getList(infoMap);
		
		dao.close();
		
		if (query != null)
		{
			request.setAttribute("query", query);
		}
		
		request.setAttribute("lists", lists);
		request.setAttribute("paging", paging.getPagingMap());
		request.setAttribute("type", type);
		
		return "board/Board.jsp";
	}
	
}
