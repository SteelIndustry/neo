package trans.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		String propertiesPath = Client.class.getResource("client.properties").getPath();
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
			maxThread = 3;
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
				
		String propertiesPath = Client.class.getResource("log.properties").getPath();
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
			case "SEVERE":
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
		
		boolean defaultName = Boolean.valueOf(properties.getProperty("defaultName"));
		
		StringBuffer sb = new StringBuffer();
		sb.append(logPath);
		
		if (!defaultName)
		{
			boolean date = Boolean.valueOf(properties.getProperty("date"));
			boolean actor = Boolean.valueOf(properties.getProperty("actor"));
			
			if (date)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd__HH_mm");
				Calendar cal = Calendar.getInstance();
				sdf.format(cal.getTime());
				
				sb.append(sdf.format(cal.getTime()));
				sb.append("__");
			}
			if (actor)
			{
				sb.append("Client__");
			}	
		}
		
		sb.append("log");
		
		StringBuffer sb2 = new StringBuffer( (sb.toString()+".txt"));
		
		int count = 1;
		while(true)
		{
			File file = new File(sb2.toString());
			System.out.println("진입");
			
			if (!file.exists()) break;
			
			sb2 = new StringBuffer( sb.toString() );
			sb2.append("(");
			sb2.append(count++);
			sb2.append(")");
			sb2.append(".txt");
		}
		
		logSetting.setLog(sb2.toString(), level);
		
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
