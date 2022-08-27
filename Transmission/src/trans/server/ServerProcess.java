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

public class ServerProcess
{
	private Socket socket;
	private String source;
	
	InputStream is;
	BufferedInputStream bis;
	DataInputStream dis;
	FileOutputStream fos;
	BufferedOutputStream bos;
	
	int count;

	public ServerProcess (Socket socket, String sourcePath)
	{
		this.socket = socket;
		this.source = sourcePath;
		
		// 디렉토리 없으면 생성
		existDir(source);
		
		// 파일 수신 과정
		receiveTask();
	}
	
	private void existDir(String path)
	{
		File file = new File(path);
		
		if (!file.exists())
			file.mkdirs();
	}
	
	private void receiveTask()
	{
		
		// 스트림 open
		try
		{
			is = socket.getInputStream();
			bis = new BufferedInputStream(is);
			dis = new DataInputStream(bis);
			
			// 파일 or 디렉토리
			boolean isDir = dis.readBoolean();
			File file = new File(getAccuratePath(dis.readUTF()));
			
			if (!isDir)
			{
				// 디렉토리 없으면 생성
				existDir(file.getParent());
				
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos);
				
				// 정합성 검사용
				String fileName = dis.readUTF();
				long fileSize = dis.readLong();
				
				// 파일 수신
				File receivedFile = copy(file);
				
				if (!receivedFile.getName().equals(fileName))
					System.out.println("파일 이름 다름");
				if (receivedFile.length() != fileSize)
					System.out.println("파일 크기 다름");
				
			}
			else
			{
				// 전달받은 디렉토리 생성
				existDir(file.getPath());
			}
			System.out.println(file.getPath());
			System.out.println(dis.readUTF());
									
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public String getAccuratePath(String path)
	{
		return new StringBuffer(source).append(path).toString();
	}
		
	private File copy(File file)
	{
		File newFile;
		
		try
		{
			int data = -1;
			byte[] bytes = new byte[1024];
			
			while ((data = dis.read(bytes)) != -1)
			{
				bos.write(bytes, 0, data);
			}
			bos.flush();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			newFile = file;
		}
		
		return newFile;
	}
	
	public void close()
	{
		try
		{
			if(dis != null) dis.close();
			if(bis != null)	bis.close();
			if(is != null)	is.close();
			
			if(bos != null)	bos.close();
			if(fos != null)	fos.close();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		dis = null;
		bis = null;
		is = null;
		bos = null;
		fos = null;
		
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
