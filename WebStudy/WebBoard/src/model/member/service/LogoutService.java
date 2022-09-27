package model.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;

public class LogoutService implements IService{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		request.getSession().invalidate();
		
		String type = request.getParameter("type") != null ? request.getParameter("type") : "free";
		
		return "redirect/board.do?type=" + type;
	}
}
