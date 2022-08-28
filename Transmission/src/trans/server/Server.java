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
import java.util.concurrent.Executors;

public class Server
{
	private static String sourcePath, ip;
	private static int port, maxThread;
	
	public static ExecutorService executorService;
	
	
	public static void main(String[] args) throws Exception
	{
		// 1. 초기 설정
		// 설정 파일
		loadServerProperties();
		
		// 스레드 풀 시작
		executorService = Executors.newFixedThreadPool(maxThread);
		
		// 2. ServerSocket open
		ServerSocket serverSocket = new ServerSocket();
		try
		{
			serverSocket.bind(new InetSocketAddress(ip, port));
		} catch (IOException e) {
			e.printStackTrace();
			
			if(!serverSocket.isClosed())
			{
				try
				{
					serverSocket.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
		
		// 3. 연결 대기 및 socket open
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				while(true)
				{
					try
					{
						System.out.println("[연결 기다림]");
						Socket socket = serverSocket.accept();
						InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
						System.out.println("[연결 수락함] " + isa.getHostName());
						
						// 4. 작업용 객체 생성 및 수신 작업
						new ServerProcess(executorService, socket, sourcePath);
						
						System.out.println("[데이터 받기 성공]");
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		};	
		executorService.submit(runnable);
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
		if (properties.getProperty("maxThread").equals("default"))
			maxThread = Runtime.getRuntime().availableProcessors();
		else
		{
			maxThread = (maxThread > Runtime.getRuntime().availableProcessors())
				? maxThread = Runtime.getRuntime().availableProcessors()
					: Integer.parseInt(properties.getProperty("maxThread"));
		}
	}
}
