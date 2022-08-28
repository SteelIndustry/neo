package trans.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client
{
	public static String sourcePath, ip;
	public static int port, maxThread;
	public static ExecutorService executorService;
	
	public static void main(String[] args) throws Exception
	{
		long start, end;
		start = System.currentTimeMillis();
		
		// 1. 초기 설정
		// 설정 파일
		loadProperties();
		
		executorService = Executors.newFixedThreadPool(3);
		
		// 2. 전송용 클래스		
		Process transfer = new Process(sourcePath, ip, port);
		
		// 3. 디렉토리 유무 탐색
		transfer.existDir();
		
		// 4. 파일 탐색 및 전송
		transfer.search(executorService);
		executorService.shutdown();
				
		System.out.println("[데이터 보내기 성공]");
		
		end = System.currentTimeMillis();
		System.out.println(end-start+"ms");
	}
	
	public static void loadProperties() throws Exception
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
		
		sourcePath = properties.getProperty("path");
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
	}
}
