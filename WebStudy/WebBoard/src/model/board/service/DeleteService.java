package model.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import model.board.BoardDAO;

public class DeleteService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = new BoardDAO();
		
		int result =  dao.delete(request.getParameter("num"));
		dao.close();
		
		if (result == 1)
			System.out.println("삭제 성공");
		else
			System.out.println("삭제 실패");
		
		return "redirect/board.do";
	}
	
}
