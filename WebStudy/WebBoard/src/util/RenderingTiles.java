package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.Request;
import org.apache.tiles.request.servlet.ServletRequest;
import org.apache.tiles.request.servlet.ServletUtil;

public class RenderingTiles {
	private final String basicPath = "/WEB-INF/view/";
	private Properties prop;
	
	public RenderingTiles()
	{
		String propPath = this.getClass().getResource("../util/Tiles.properties").getPath();
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
	
	public Properties getProp()
	{
		return this.prop;
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, String path)
	{
		ApplicationContext context = ServletUtil.getApplicationContext(request.getServletContext());
		TilesContainer container = TilesAccess.getContainer(context);
		
		Request tilesRequest = new ServletRequest(container.getApplicationContext(), request, response);
		AttributeContext attributeContext = container.startContext(tilesRequest);
		
		if (path.indexOf("?") != -1)
		{
			path = path.substring(0, path.indexOf("?"));
		}
		
		String[] parameters = prop.getProperty(path).split(",");
		
		// title
		attributeContext.putAttribute("title", new Attribute(parameters[0]));
		
		// header
		parameters[1] = parameters[1].equals("false") ? "" : basicPath+parameters[1];
		attributeContext.putAttribute("header", new Attribute(parameters[1]));
		
		// body
		attributeContext.putAttribute("body", new Attribute(basicPath+path));
		
		// footer
		parameters[2] = parameters[2].equals("false") ? "" : basicPath+parameters[2];
		attributeContext.putAttribute("footer", new Attribute(parameters[2]));
		
		container.render("template", tilesRequest);		
	}
}
