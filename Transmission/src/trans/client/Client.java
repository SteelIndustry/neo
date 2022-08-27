package trans.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.util.Properties;

public class Client
{
	public static String sourcePath, ip;
	public static int port;
	
	public static void main(String[] args) throws Exception
	{
		// 1. 설정 파일 불러오기
		loadProperties();
		
		// 2. 전송용 클래스		
		Process transfer = new Process(sourcePath, ip, port);
		
		// 3. 디렉토리 유무 탐색
		transfer.existDir();
		
		// 4. 파일 탐색 및 전송
		transfer.search();
		
		System.out.println("[데이터 보내기 성공]");
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
	}
}
