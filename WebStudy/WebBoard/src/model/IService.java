package model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IService {
	public void execute(HttpServletRequest request, HttpServletResponse response);
}
