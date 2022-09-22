package controller;

import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import model.board.GetBoardService;

@WebServlet
public class ServletController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGetPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGetPost(req, resp);
	}
	
	private void doGetPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//request.setCharacterEncoding("UTF-8");
		
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		
		String command = uri.substring(path.length());
		
		System.out.println(command);
		
		IService service = null;
		
		if (command.equals("/board.do"))
		{
			service = new GetBoardService();
		}
		
		service.execute(request, response);
		
		request.getRequestDispatcher("board/FreeBoard.jsp").forward(request, response);
	}
}
