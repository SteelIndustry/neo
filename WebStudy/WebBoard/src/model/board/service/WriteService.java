package model.board.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import model.IService;
import model.board.BoardDAO;
import model.board.BoardDTO;
import util.FileUtil;
import util.Setting;
import util.TableName;
import util.XSSForMultiPart;

public class WriteService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String tableName; // 참조 테이블 이름
		
		if (request.getHeader("Content-type") == null)
		{
			String url = "board.do";
			request.setAttribute("errMsg", "잘못된 요청 접근입니다.");
			request.setAttribute("url", url);
			return "board/ErrorPage.jsp";
		}
		
		// Multipart Request
		String saveDir = Setting.getSaveDir();
		MultipartRequest mr = FileUtil.uploadFile(request, saveDir, Setting.getMaxPostSize());
		
		// 잘못된 접근(주소로 action 접근)
		String access = null;
		access = mr.getParameter("access");
		
		if (access == null)
		{
			String url = "board.do";
			request.setAttribute("errMsg", "잘못된 요청 접근입니다.");
			request.setAttribute("url", url);
			
			return "board/ErrorPage.jsp";
		}
		
		// XSS 체크
		String title = mr.getParameter("title");
		String content = mr.getParameter("content");
		
		if (XSSForMultiPart.checkXSS(title) || XSSForMultiPart.checkXSS(content))
		{
			String url = "board.do";
			request.setAttribute("errMsg", "XSS Filter 작동");
			request.setAttribute("url", url);
			
			return "board/ErrorPage.jsp";
		}
		
		// 게시판 타입에 따른 테이블 이름
		String type = request.getParameter("type") != null ? request.getParameter("type") : "free"; 
		tableName = TableName.getTableName(type);
		
		// DAO 설정
		BoardDAO dao = new BoardDAO(tableName);
		
		BoardDTO dto = new BoardDTO();
		
		dto.setTitle(title);
		dto.setContent(content);
		
		dto.setId((String)request.getSession().getAttribute("id"));
		
		String fileName = mr.getFilesystemName("fileName");
		if (fileName != null)
		{
			// 첨부 파일이 있을 경우 파일명 변경
			// 새로운 파일명 생성
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(Calendar.getInstance().getTime());
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = now + ext;
			
			// 파일명 변경
			File oldFile = new File(saveDir + File.separator + fileName);
			File newFile = new File(saveDir + File.separator + newFileName);
			oldFile.renameTo(newFile);
			
			// DTO에 저장
			dto.setFileName(fileName);
			dto.setSavedName(newFileName);
		}
		
		int result = dao.insert(dto);
		
		if (result==1)
			System.out.println("글쓰기 성공");
		else
			System.out.println("글쓰기 실패");
		
		dao.close();
		
		return "redirect/board.do?type="+type;
		
	}
	
}
