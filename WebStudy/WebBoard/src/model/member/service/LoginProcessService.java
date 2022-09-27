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
		
		// 원래 이동하려던 페이지가 있을 경우
		String originalUrl = request.getParameter("url");
		String type = request.getParameter("type");
		
		// 아이디 체크 성공
		if (dto.getName() != null )
		{
			request.getSession().setAttribute("id", dto.getId());
			request.getSession().setAttribute("name", dto.getName());
			
			String url = "redirect/";
			
			if (originalUrl != null)
			{
				url += originalUrl;
				System.out.println("url: "+ url);
			}
			else
			{
				url = url + "board.do";
				url += type != null ? ("?type=" + type ): ""; 
				System.out.println("url: " + url);
			}
			return url;
		}
		request.setAttribute("errMsg", "아이디 혹은 비밀번호를 잘못 입력하였습니다.");
		
		if (originalUrl != null)
			request.setAttribute("url", originalUrl);
		else
			request.setAttribute("type", type);
		
		return "member/Login.jsp";
	}

}
