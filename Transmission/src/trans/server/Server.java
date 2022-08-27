package trans.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

public class Server
{
	public static String sourcePath, ip;
	public static int port;
	
	public static void main(String[] args) throws Exception
	{
		// 1. 설정 파일 불러오기
		loadServerProperties();
		
		// 2. ServerSocket open
		ServerSocket serverSocket = new ServerSocket();
		
		int count = 0;
		
		try
		{
			serverSocket.bind(new InetSocketAddress(ip, port));
			
			// 3. 연결 대기 및 socket open
			while (true)
			{
				count++;
				
				System.out.println("[연결 기다림]");
				Socket socket = serverSocket.accept();
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("[연결 수락함] " + isa.getHostName());
				
				// 4. 작업용 객체 생성 및 수신 작업
				ServerProcess process = new ServerProcess(socket, sourcePath);
				
				System.out.println("[데이터 받기 성공]");
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(!serverSocket.isClosed())
			{
				try
				{
					serverSocket.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void loadServerProperties() throws Exception
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
		
		sourcePath = properties.getProperty("path");
		port = Integer.parseInt(properties.getProperty("port"));
		ip = properties.getProperty("ip");
	}
}
