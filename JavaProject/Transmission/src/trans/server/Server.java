package trans.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server
{
	private static ExecutorService executorService;
	private static Logger logger;
	
	public static void main(String[] args) throws Exception
	{
		// 1. 초기 설정
		// 설정 파일, 로그 설정 파일
		Setting setting = new Setting();
		setting.loadProperties();
		setting.loadLogProperties();
				
		// Logger 객체
		logger = setting.getLogger();
		
		// 서버 시작 알림
		logger.info("Server start");
		
		// 스레드 풀 설정
		executorService = Executors.newFixedThreadPool(setting.getMaxThread());
		
		// 2. ServerSocket open
		ServerSocket serverSocket = new ServerSocket();
		try
		{
			serverSocket.bind(new InetSocketAddress(setting.getIp(), setting.getPort()));
			
			// 3. 연결 대기 및 socket open
			while(true)
			{
				System.out.println("[연결 기다림]");
				Socket socket = serverSocket.accept();
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				
				StringBuffer sb = new StringBuffer("[연결 수락함] ");
				sb.append(isa.getHostName());
				logger.info(sb.toString());
				
				// 4. 작업용 객체 생성
				ReceivingFile rf = new ReceivingFile(socket, setting.getPath(), logger);
				
				// 5. 저장 디렉토리 존재 유무. 없으면 디렉토리 생성
				rf.existDir(setting.getPath());
				
				// 6. 파일 수신 작업
				executorService.submit(new Runnable()
				{
					@Override
					public void run()
					{
						boolean consistency = rf.receiveFile();
						
						// 정합성 검사 실패했으면 파일 삭제
						if (!consistency)
							rf.delete();
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
			
			logger.severe(e.getMessage());
			
			if (serverSocket != null)
			{
				if(!serverSocket.isClosed())
				{
					try
					{
						serverSocket.close();
					} catch (IOException e1)
					{
						e1.printStackTrace();
						logger.info(e1.getMessage());
					}
				}
			}
			
			logger.info("server ends");
		}
	}
}
