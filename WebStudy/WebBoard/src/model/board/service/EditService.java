package model.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import model.board.BoardDAO;
import model.board.BoardDTO;
import util.TableName;

public class EditService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String tableName; // 참조 테이블 이름
		
		// 게시판 타입에 따른 테이블 이름
		String type = request.getParameter("type") != null ? request.getParameter("type") : "free"; 
		tableName = TableName.getTableName(type);
		
		// DAO 설정
		BoardDAO dao = new BoardDAO(tableName);
		
		BoardDTO dto = dao.getList(request.getParameter("num")).get(0);
		
		dao.close();
		
		request.setAttribute("dto", dto);
		request.setAttribute("type", request.getParameter("type"));
		
		return "board/Edit.jsp";
	}
	
}
