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

public class BoardService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		int totalCount; // 총 게시글 개수
		int[] pageRange; // 한 페이지 내 게시물 범위
		
		String query = null; // 검색어 조건을 query 형식으로 저장할 String
		
		// 검색어 조건 종류
		String[] keyList = {"title", "content"};
		
		// 검색어
		String keyword = request.getParameter("keyword");
		
		// 현재 페이지 번호
		int pageNum = request.getParameter("pageNum") != null 
				? Integer.parseInt(request.getParameter("pageNum")) : 1;
		
		BoardDAO dao = new BoardDAO();
		List<BoardDTO> lists = new ArrayList<BoardDTO>();
		
		// 검색, 게시물 출력 관련 정보 담을 map
		Map<String, String> infoMap = new HashMap<String, String>();
		
		if (keyword != null)
		{
			infoMap.put("keyword", keyword);
						String keywords = "";
			query ="";
			for (String key : keyList)
			{
				String value = (String) request.getParameter(key);
				if (value != null)
				{
					keywords += value + ",";
					query += "&" + key +"="+value;
				}
					
			}
			keywords = keywords.substring(0, keywords.length()-1); // 마지막 ',' 제거
			query += "&keyword=" +keyword;
			
			request.setAttribute("keywords", keywords); // 검색 조건 유지를 위해서 키워드 저장
			request.setAttribute("keyword", keyword);
			
			infoMap.put("keywords", keywords);
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
		
		return "board/Board.jsp";
	}
	
}
