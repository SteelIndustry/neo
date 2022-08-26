package trans.client;

import java.io.File;

public class Transfer
{
	String source;
	
	public Transfer (String sourcePath)
	{
		this.source = sourcePath;
	}
	
	public void existDir()
	{
		File file = new File(source);
		
		if (!file.exists())
			file.mkdirs();
	}
	
	public void search(String path)
	{
		File source = new File(getCurrentPath(path));
		File[] contents = source.listFiles();
		
		for (File file : contents)
		{
			if(!file.isDirectory())
			{
				OpenSocketStream socket = new OpenSocketStream();
				
				socket.connectSocket("localhost", 10304);
				
				socket.openStream(file.getPath());
				
				socket.send(file, path+"/"+file.getName());
				
				socket.closeSocket();
			}
			else
			{
				String newPath = new StringBuffer(path)
						.append("/").append(file.getName()).toString();
				
				search(newPath);
			}			
		}
		
		if (contents.length == 0)
		{
			OpenSocketStream socket = new OpenSocketStream();
			
			socket.connectSocket("localhost", 10304);
			
			socket.openStream(source.getPath());
			
			socket.send(path);
			
			socket.closeSocket();
		}
		
		
	}
	public String getCurrentPath(String pathName)
	{
		return new StringBuffer(source).append(pathName).toString();		
	}
}
