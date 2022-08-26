package trans.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Receive
{
	InputStream is;
	BufferedInputStream bis;
	DataInputStream dis;
	FileOutputStream fos;
	BufferedOutputStream bos;
	
	Socket socket;
	
	String source;
	
	public Receive (Socket socket, String sourcePath)
	{
		this.socket = socket;
		this.source = sourcePath;
		
		try
		{
			is = socket.getInputStream();
			
			bis = new BufferedInputStream(is);
			dis = new DataInputStream(bis);
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void existDir(String path)
	{
		File file = new File(path);
		
		
		if (!file.exists())
			file.mkdirs();
	}
	
	public void getInfo()
	{
		String fileName, path;
		long fileSize;
		
		try
		{
			boolean isDir = dis.readBoolean();
			
			path = getDirPath(dis.readUTF());
			
			File file = new File(path);
			
			
			if (!isDir)
			{
				fileName = dis.readUTF();
				fileSize = dis.readLong();
				
				existDir(file.getParent());
				
				File newFile = copy(file);
				
				if (newFile.length() != fileSize)
					System.out.println("파일 크기 다름");
				if (!newFile.getName().equals(fileName))
					System.out.println("파일 이름 다름");
			}
			else
			{
				existDir(file.getPath());
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally {
			closeStream();
		}
	}
	
	public String getDirPath(String path)
	{
		return new StringBuffer(source).append(path).toString();
	}
	
	public File copy(File file)
	{
		File newFile;
		
		try
		{
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			
			int data = -1;
			
			byte[] bytes = new byte[1024];
			
			while ((data = dis.read(bytes)) != -1)
			{
				bos.write(bytes, 0, data);
			}
			bos.flush();
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			newFile = file;
		}
		return newFile;
	}
	
	
	public void closeStream()
	{
		try
		{
			if(dis != null) dis.close();
			if(bis != null)	bis.close();
			//if(is != null)	is.close();
			
			if(bos != null)	bos.close();
			if(fos != null)	fos.close();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void closeSocket()
	{
		if (!socket.isClosed())
		{
			try
			{
				socket.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		socket = null;
	}
}
