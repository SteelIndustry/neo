package model.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import model.board.BoardDAO;
import util.TableName;

public class DeleteService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String tableName; // 참조 테이블 이름
		
		// 게시판 타입에 따른 테이블 이름
		String type = request.getParameter("type") != null ? request.getParameter("type") : "free"; 
		tableName = TableName.getTableName(type);
		
		// DAO 설정
		BoardDAO dao = new BoardDAO(tableName);
				
		int result =  dao.delete(request.getParameter("num"));
		dao.close();
		
		if (result == 1)
			System.out.println("삭제 성공");
		else
			System.out.println("삭제 실패");
		
		return "redirect/board.do?type="+type;
	}
	
}
