package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class MyContextListener implements ServletContextListener, ServletRequestListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		System.out.println("웹 어플리케이션 시작");
		System.out.println("서버 정보 : " + context.getServerInfo());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("웹 어플리케이션 종료");
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		System.out.println("받아줘 리퀘스트====================");
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		System.out.println("======================리퀘스트 없다.");
	}

	
	
	
}
