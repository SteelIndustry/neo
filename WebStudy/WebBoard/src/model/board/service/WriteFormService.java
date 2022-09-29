package model.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;

public class WriteFormService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("type", request.getParameter("type"));
		
		return "board/WriteForm.jsp";
	}
	
}
