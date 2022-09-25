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

public class UpdateService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();
		
		String saveDir = Setting.getSaveDir();
		
		MultipartRequest mr = FileUtil.uploadFile(request, saveDir, Setting.getMaxPostSize());
		
		String prevFileName = mr.getParameter("prevFileName");
		String preveSavedName = mr.getParameter("prevSavedName");
		
		dto.setTitle(mr.getParameter("title"));
		dto.setContent(mr.getParameter("content"));
		dto.setNum(mr.getParameter("num"));
		
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
			
			// 기존 파일 삭제
			FileUtil.deleteFile(request, saveDir, preveSavedName);
		}
		else
		{
			dto.setFileName(prevFileName);
			dto.setSavedName(preveSavedName);
		}
		int result = dao.update(dto);
		
		if (result==1)
			System.out.println("수정 성공");
		else
			System.out.println("수정 실패");
		
		dao.close();
		
		return "redirect/board.do";
	}
	
}
