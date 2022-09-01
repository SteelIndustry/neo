package trans.client;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class Client2
{
	private static ExecutorService executorService;
	private static ThreadPoolExecutor threadPoolExecutor;
	private static Logger logger;
	
	public static void main(String[] args) throws Exception
	{
		//long start, end;
		//start = System.currentTimeMillis();
		
		// 1. 초기 설정
		// 설정 파일, 로그 설정 파일
		Setting setting = new Setting();
		setting.loadProperties();
		setting.loadLogProperties();
		
		// Logger 객체
		logger = setting.getLogger();
		
		// 클라이언트 시작 알림
		StringBuffer sb = new StringBuffer();
		InetAddress local = InetAddress.getLocalHost();
		sb.append("client(");		
		sb.append(local.getHostAddress());
		sb.append(") starts");
		logger.info(sb.toString());
		
		// 스레드 풀 설정
		
		threadPoolExecutor = new ThreadPoolExecutor(
			setting.getMaxThread()
			, Runtime.getRuntime().availableProcessors()
			, 10L
			, TimeUnit.SECONDS
			, new LinkedBlockingQueue<Runnable>()
		); 
		executorService = threadPoolExecutor;
		
		// 2. 전송 작업용 클래스
		ClientTask clientTask = new ClientTask(setting, executorService);
		
		// 3. 디렉토리 생성
		clientTask.existDir();
		
		// 4. 파일 탐색 및 전송
		while(true)
		{
			// 파일 전송 작업
			logger.info("탐색 및 전송 작업 시작");
			clientTask.task("");
			
			// 활동 중인 쓰레드가 모두 중지될 때까지 대기
			while(true)
			{
				if (threadPoolExecutor.getActiveCount() == 0)
				{
					break;
				}
			}
			
			// 5. 빈 폴더 모두 삭제
			clientTask.delete();
			
			logger.info("탐색 및 전송 작업 + 빈 폴더 정리 끝");
			System.out.println("루프 1회 끝");
			
			Thread.sleep(10000);
		}
		
	}
}
