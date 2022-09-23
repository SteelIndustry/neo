package model.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import model.board.BoardDAO;
import model.board.BoardDTO;

public class DetailService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = new BoardDAO();
		
		BoardDTO dto = dao.getList(request.getParameter("num")).get(0);
		
		dao.close();
		
		request.setAttribute("dto", dto);
		
		return "board/Detail.jsp";
	}
	
}
