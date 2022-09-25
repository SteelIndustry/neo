package model.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import model.member.MemberDAO;
import model.member.MemberDTO;

public class LoginProcessService implements IService{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDAO dao = new MemberDAO();
		
		MemberDTO dto = new MemberDTO();
		
		dto.setId(request.getParameter("id"));
		dto.setPassword(request.getParameter("password"));
		
		dto = dao.getList(dto);
		
		if (dto.getName() != null )
		{
			request.getSession().setAttribute("id", dto.getId());
			request.getSession().setAttribute("name", dto.getName());
			
			return "redirect/board.do";
		}
		request.setAttribute("errMsg", "아이디 혹은 비밀번호를 잘못 입력하였습니다.");
		return "login.do";
	}

}
