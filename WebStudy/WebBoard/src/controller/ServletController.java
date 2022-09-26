package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.IService;
import util.RenderingTiles;
import util.Setting;

public class ServletController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final String basicPath = "WEB-INF/view/";
	private Properties prop; // URL - Service 매칭
	private Properties tilesProp;
	private RenderingTiles rendering;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// 기본 세팅
		ServletContext application = getServletContext();
		
		// 파일 업로드/다운로드 용
		Setting.setMaxPostSize(application.getInitParameter("maxPostSize"));
		Setting.setSaveDir(application.getRealPath(application.getInitParameter("saveDir")));
		
		// 페이징 용
		Setting.setPostsPerPage(application.getInitParameter("POSTS_PER_PAGE"));
		Setting.setPagesPerBlock(application.getInitParameter("PAGES_PER_BLOCK"));
		
		// tiles 용
		rendering = new RenderingTiles();
		tilesProp = rendering.getProp();
		
		// Properties 호출
		// Address.properties 는 Service 클래스 주소를 담고 있음
		// UploadDir.properties 는 Upload 주소
		String propPath = this.getClass().getResource("../util/Address.properties").getPath();
		prop = new Properties();
		
		try {
			propPath = URLDecoder.decode(propPath, "UTF-8");
			prop.load(new FileInputStream(propPath));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
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
		
		// 실제 요청 주소 얻기 위해 식별 주소 추출
		String uri = request.getRequestURI();
		String path = request.getContextPath();
				
		String command = uri.substring(path.length());
		System.out.println(command);
		
		IService service = null;
		// 동적으로 클래스 인스턴스 획득
		try {
			Class<?> clazz = Class.forName(prop.getProperty(command));
			Constructor<?> constructor = clazz.getConstructor();
			service = (IService) constructor.newInstance();	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		// 해당 주소의 service 실행
		String uriPath = service.execute(request, response);
		
		// return 값에 따라 forward, redirect, 아무것도 하지 않음 결정.
		if (uriPath.contains("redirect"))
		{
			uriPath = uriPath.split("/")[1];
			response.sendRedirect(uriPath);
		}
		else if (!uriPath.equals(""))
		{
			if (tilesProp.getProperty(uriPath) != null)
			{
				rendering.render(request, response, uriPath);
			}
			else
			{
				request.getRequestDispatcher(basicPath+uriPath).forward(request, response);
			}
			
			/*
			if (uriPath.contains("board/"))
			{
				
				// tiles
				ApplicationContext context = ServletUtil.getApplicationContext(request.getServletContext());
				TilesContainer container = TilesAccess.getContainer(context);
				
				Request tilesRequest = new ServletRequest(container.getApplicationContext(), request, response);
				AttributeContext attriContext = container.startContext(tilesRequest);
				
				attriContext.putAttribute("title", new Attribute("자유 게시판"));
				attriContext.putAttribute("body", new Attribute("/" + uriPath));
				//attriContext.putAttribute("footer", new Attribute(""));
				
				container.render("board", tilesRequest);
				
			}
			*/
		}
	}
}
