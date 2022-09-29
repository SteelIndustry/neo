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

public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		if (request.getSession(false).getAttribute("id") == null)
		{
			String url = request.getRequestURI().substring(request.getContextPath().length()+1);
			//String query = request.getQueryString();
			
			/*
			if (query != null)
				url = url + "?" + query;
			*/
			
			request.setAttribute("url", url);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("loginform.do");
			dispatcher.forward((ServletRequest) request, (ServletResponse) response);
			
		}
		else
		{
			chain.doFilter(req, resp);
		}
	}
}
