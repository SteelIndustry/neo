package trans.client;

import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Future;
import java.util.logging.Logger;

public class ClientTask
{
	private String source;
	private ExecutorService executorService;
	private Logger logger;
	private Setting setting;
	//private List<Future<?>> futureList;
	
	public ClientTask(Setting setting, ExecutorService executorService)
	{
		this.setting = setting;
		this.source = setting.getPath();
		this.logger = setting.getLogger();
		this.executorService = executorService;
		//this.futureList = new ArrayList<Future<?>>();
	}
	
	public void existDir()
	{
		File file = new File(source);
		
		if (!file.exists())
		{
			file.mkdirs();
			StringBuffer sb = new StringBuffer();
			sb.append("make dir ");
			sb.append(source);
			logger.info(sb.toString());
		}
	}
	
	// 파일 탐색 및 전송 작업
	public void task(String path)
	{
		File[] list = search(path);
		
		for(File file : list)
		{
			// source: "c:/source" 이고, file: "c:/source/folder1/file1.txt" 일 때
			// shortPath:  "/folder1/file1.txt" 
			String shortPath = getShortPath(path, file.getName());
			
			if (file.isDirectory())
				task(shortPath);
			else
			{
				Runnable runnable = new Runnable()
				{
					@Override
					public void run()
					{
						boolean deleteCheck = false;
						deleteCheck = send(file, shortPath);
						
						StringBuffer sb = new StringBuffer(file.getName());
						sb.append(" 파일 전송");
						
						
						if (deleteCheck)
						{
							file.delete();
							sb.append(" 후 디렉토리에서 삭제");
						}
						else
						{
							sb.append(" 실패");
						}
						logger.info(sb.toString());							
					}
				};
				executorService.submit(runnable);
				//Future<?> future = executorService.submit(runnable);
				//futureList.add(future);
			}
		}
	}
	
	public File[] search(String path)
	{
		return new File(getAccuratePath(path)).listFiles();
	}
	
	public boolean send(File file, String path)
	{
		SendingProcess sendingProcess = new SendingProcess(file, path, setting);
		
		return sendingProcess.send();
	}
	
	// 빈 폴더 정리
	public void delete()
	{
		File sourceFolder = new File(source);
		File[] contents = sourceFolder.listFiles();
		
		for (File file : contents)
		{
			if (file.exists())
			{
				if ( file.isDirectory() )
					delete(file.getPath());
			}			
		}
	}
	public void delete(String path)
	{
		File sourceFolder = new File(path);
		File[] contents = sourceFolder.listFiles();
				
		for (File file : contents)
		{
			if (file.exists())
			{
				if ( file.isDirectory() )
					delete(file.getPath());
			}			
		}
		if (sourceFolder.list().length == 0)
		{
			if (sourceFolder.exists())
			{
				if (sourceFolder.delete())
				{
				}
			}
		}	
	}
	
	/*
	public List<Future<?>> getFutureList()
	{
		return futureList;
	}
	*/
	
	// source: C:/TMTest/source
	// file path: C:/TMTest/source/folder1/text.txt
	// 일 때, path = "/folder1/text.txt"로 설정하여 search(String path)의 파라미터로 넘겨줌.
	// 따라서 실제 주소가 필요함
	public String getAccuratePath(String path)
	{
		return new StringBuffer(source).append(path).toString();		
	}
	
	public String getShortPath(String path, String fileName)
	{
		return new StringBuffer(path).append("/").append(fileName).toString();
	}
}
