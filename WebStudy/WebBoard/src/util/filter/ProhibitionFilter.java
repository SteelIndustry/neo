package util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProhibitionFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String access = request.getParameter("access");
		
		if (access != null)
			chain.doFilter(req, resp);
		else
		{
			String url = "board.do";
			
			request.setAttribute("errMsg", "잘못된 요청 접근입니다.");
			request.setAttribute("url", url);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("errorpage.do");
			dispatcher.forward((ServletRequest) request, (ServletResponse) response);
		}
	}
}
