package model.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import model.member.MemberDAO;
import model.member.MemberDTO;

public class JoinProcessService implements IService{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		MemberDAO dao = new MemberDAO();
		
		MemberDTO dto = new MemberDTO();
		
		dto.setId(request.getParameter("id"));
		dto.setName(request.getParameter("name"));
		dto.setPassword(request.getParameter("password"));
		
		int result = dao.insert(dto);
		
		if (result > 0)
			System.out.println("회원가입 성공");
		else
			System.out.println("회원가입 실패");
		
		return "redirect/login.do";
	}

}
