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
		String category = null;	// 검색 조건 목록
		String tableName; // 참조 테이블 이름
		String[] categoryList = {"title", "content"}; // 검색어 조건 종류
		Map<String, String> searchMap = new HashMap<String, String>(); // 검색어를 담은 map
		Map<String, String> infoMap = new HashMap<String, String>(); // 검색 관련 정보를 담은 map
		BoardDAO dao;
		List<BoardDTO> lists = new ArrayList<BoardDTO>(); // 출력 내용 저장
		
		
		// tableName
		String type = request.getParameter("type") != null ? request.getParameter("type") : "free"; 
		tableName = TableName.getTableName(type);
		
		// dao 생성
		dao = new BoardDAO(tableName);
		
		// 현재 페이지 번호
		int pageNum = request.getParameter("pageNum") != null 
				? Integer.parseInt(request.getParameter("pageNum")) : 1;
		
		// 검색조건 map에 입력
		if (request.getParameter("search") != null)
		{
			category = "";	// 검색 조건 목록
			query = "";
			
			for (String c : categoryList)
			{
				String value = null;
				
				if ((value=request.getParameter(c)) != null )
				{
					value = value.trim();
					
					if ( !value.equals("") )
					{
						searchMap.put(c, value);
						query = query + "&" + c +"="+value;
					}
					category += c + ",";
				}
			}
			category = category.substring(0, category.length()-1);
			
			infoMap.put("category", category);
		}
		
		// 전체 row 개수
		totalCount = dao.countList(searchMap, infoMap);
		
		// 페이징 정보 추출
		Paging paging = new Paging(totalCount, pageNum);
		pageRange = paging.getPageRange();
		
		// 한 페이지 내 출력할 게시물의 시작 번호 및 끝 번호
		infoMap.put("start", String.valueOf(pageRange[0]));
		infoMap.put("end", String.valueOf(pageRange[1]));
		
		// 최종 출력할 결과물 받아오기
		lists = dao.getList(searchMap, infoMap);
		dao.close();
		
		if (query != null)
			request.setAttribute("query", query);
		
		request.setAttribute("lists", lists);
		request.setAttribute("paging", paging.getPagingMap());
		request.setAttribute("type", type);
		
		return "board/Board.jsp";
	}
	
}
