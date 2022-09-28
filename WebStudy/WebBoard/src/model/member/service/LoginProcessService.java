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
		
		// 원래 이동하려던 페이지 정보
		String num = request.getParameter("num");
		String type = request.getParameter("type");
		String url = request.getParameter("url");
		
		dto.setId(request.getParameter("id"));
		dto.setPassword(request.getParameter("password"));
		dto = dao.getList(dto);
		
		System.out.println("num: " + num);
		System.out.println("type: " + type);
		System.out.println("url: " + url);
		
		// 타입은 필수 파라미터. 파라미터 미리 설정
		String query = "?type=" + type;
		if (num != null)
			query+= "&num=" + num;
		
		// 아이디 체크 성공
		if (dto.getName() != null )
		{
			request.getSession().setAttribute("id", dto.getId());
			request.getSession().setAttribute("name", dto.getName());
			
			String path = "redirect/";
			
			// 목적지 없이 그냥 로그인만 했을 경우
			if (url == null)
				path = path + "board.do";	
			else
				path = path+url+query;
			
			return path;
		}
		request.setAttribute("errMsg", "아이디 혹은 비밀번호를 잘못 입력하였습니다.");
		request.setAttribute("url", url);
		
		System.out.println("로그인 오류");
		
		return "member/Login.jsp" + query;
	}

}
