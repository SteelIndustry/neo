package trans.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Setting
{
	private String path, ip;
	private int port, maxThread;
	private Logger logger;
	
	public void loadProperties() throws Exception
	{
		String propertiesPath = Server.class.getResource("server.properties").getPath();
		Properties properties = new Properties();
		
		try
		{
			propertiesPath = URLDecoder.decode(propertiesPath, "utf-8");
			properties.load(new FileInputStream(propertiesPath));
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("설정 파일 오류");
			throw new Exception();
		}
		
		path = properties.getProperty("path");
		port = Integer.parseInt(properties.getProperty("port"));
		ip = properties.getProperty("ip");
		if (properties.getProperty("maxThread").equals("default"))
			maxThread = Runtime.getRuntime().availableProcessors();
		else
		{
			maxThread = (maxThread > Runtime.getRuntime().availableProcessors())
				? maxThread = Runtime.getRuntime().availableProcessors()
					: Integer.parseInt(properties.getProperty("maxThread"));
		}
		
		checkValidation();
	}

	public void loadLogProperties() throws Exception
	{
		LogSetting logSetting = new LogSetting();
				
		String propertiesPath = Server.class.getResource("log.properties").getPath();
		Properties properties = new Properties();
		
		try
		{
			propertiesPath = URLDecoder.decode(propertiesPath, "utf-8");
			properties.load(new FileInputStream(propertiesPath));
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("로그 설정 파일 오류");
			throw new Exception();
		}
		
		String logPath = properties.getProperty("path");
		String logLevel = properties.getProperty("level");
		
		Level level;
		
		switch (logLevel)
		{
			case "SERVERE":
				level = Level.SEVERE;
				break;
			case "WARNING":
				level = Level.WARNING;
				break;
			case "INFO":
				level = Level.INFO;
				break;
			case "FINE":
				level = Level.FINE;
				break;
			case "FINER":
				level = Level.FINER;
				break;
			case "FINEST":
				level = Level.FINEST;
				break;
			default:
				level = Level.CONFIG;
				break;
		}
		
		logSetting.setLog(logPath, level);
		
		logger = logSetting.getLogger();
	}
	
	private void checkValidation()
	{
		System.out.println("원본 디렉토리, 포트 번호, ip 유효성 검사 하는 곳");
	}
	
	public String getPath()
	{
		return path;
	}

	public String getIp()
	{
		return ip;
	}

	public int getPort()
	{
		return port;
	}

	public int getMaxThread()
	{
		return maxThread;
	}
	
	public Logger getLogger()
	{
		return logger;
	}
}
