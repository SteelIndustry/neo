package model.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;

public class WriteService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		return "board/Write.jsp";
	}
	
}