package model.board.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import model.board.BoardDAO;
import model.board.BoardDTO;

public class BoardService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = new BoardDAO();
		
		List<BoardDTO> lists = dao.getList();
		
		dao.close();
		
		request.setAttribute("lists", lists);
		
		return "board/Board.jsp";
	}
	
}
