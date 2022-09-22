package model.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;

public class GetBoardService implements IService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = new BoardDAO();
		
		List<BoardDTO> lists = dao.getList();
		
		dao.close();
		
		request.setAttribute("lists", lists);
	}
	
}
