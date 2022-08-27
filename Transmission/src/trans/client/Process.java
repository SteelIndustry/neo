package trans.client;

import java.io.File;

public class Process
{
	private String source, ip;
	private int port;
	
	public Process (String sourcePath, String ip, int port)
	{
		this.source = sourcePath;
		this.ip = ip;
		this.port = port;
	}
	
	public void existDir()
	{
		File file = new File(source);
		
		if (!file.exists())
			file.mkdirs();
	}
	
	// 파일 탐색
	public void search() { search(""); }
	
	// 파일 탐색
	private void search(String path)
	{
		File source = new File(getAccuratePath(path));
		File[] contents = source.listFiles();
		
		// 빈(empty) 폴더(빈 디렉토리) 용 if
		if (contents.length == 0)
		{
			SendFile sendFile = new SendFile(path, ip, port, source);
			
			return;
		}
		
		for (File file : contents)
		{
			if(!file.isDirectory())
			{
				SendFile sendFile = new SendFile(getShortPath(path, file.getName())
													, ip, port, file);
			}
			else
			{
				String newPath = new StringBuffer(path)
						.append("/").append(file.getName()).toString();
				
				search(newPath);
			}			
		}		
	}
	
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
