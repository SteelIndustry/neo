package model.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;

public class LoginService implements IService{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		return "member/Login.jsp";
	}

}
