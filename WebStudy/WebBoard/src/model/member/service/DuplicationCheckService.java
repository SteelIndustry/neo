package model.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import model.member.MemberDAO;

public class DuplicationCheckService implements IService{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		MemberDAO dao = new MemberDAO();
		
		boolean result = dao.getList(request.getParameter("name"));
		
		request.setAttribute("result", result);
		
		return "";
	}
	
}
