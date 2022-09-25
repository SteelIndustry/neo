package model.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import util.FileUtil;
import util.Setting;

public class DownloadService implements IService {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		String savedName = request.getParameter("savedName");
		
		FileUtil.download(request, response, Setting.getSaveDir(), savedName, fileName);
		
		return "";
	}
	
}
