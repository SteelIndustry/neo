package model.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;

public class LoginFormService implements IService{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		return "member/LoginForm.jsp";
	}

}
