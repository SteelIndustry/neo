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

public class InsertService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();
		
		String saveDir = Setting.getSaveDir();
		
		MultipartRequest mr = FileUtil.uploadFile(request, saveDir, Setting.getMaxPostSize());
		
		dto.setTitle(mr.getParameter("title"));
		dto.setContent(mr.getParameter("content"));
		// 임시
		dto.setId("musthave");
		dto.setType("1");
		//dto.setId(mr.getParameter("id"));
		//dto.setType(mr.getParameter("type"));
		
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
		
		return "redirect/board.do";
	}
	
}
