package trans.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ServerProcess
{
	private Socket socket;
	private String source;
	private ExecutorService executorService;
	
	OutputStream os;
	BufferedOutputStream bos2;
	DataOutputStream dos;
	
	InputStream is;
	BufferedInputStream bis;
	DataInputStream dis;
	FileOutputStream fos;
	BufferedOutputStream bos;
	
	int count;

	public ServerProcess (ExecutorService executorService, Socket socket, String sourcePath)
	{
		this.executorService = executorService; 
		this.socket = socket;
		this.source = sourcePath;
		
		// 디렉토리 없으면 생성
		existDir(source);
		
		// 파일 수신 과정
		
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				receiveTask();
			}
		};
		this.executorService.submit(runnable);
		
	}
	
	private void existDir(String path)
	{
		File file = new File(path);
		
		if (!file.exists())
			file.mkdirs();
	}
	
	private void receiveTask()
	{
		System.out.println(Thread.currentThread().getName());
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
									
		} catch (IOException e)
		{
			e.printStackTrace();
			close();
		}
		
		try
		{
			fos.close();

			bos.close();
			
			bos = null;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendMsg();
		
		close();
	}
	
	public String getAccuratePath(String path)
	{
		return new StringBuffer(source).append(path).toString();
	}
	
	private void sendMsg()
	{
		
		try
		{
			
			os = socket.getOutputStream();
			bos2 = new BufferedOutputStream(os);
			dos = new DataOutputStream(bos2);
			
			System.out.println("생성 완료");
			
			dos.writeBoolean(true);
			dos.writeBoolean(true);
			System.out.println("보내기 완료");
		} catch (IOException e)
		{
			e.printStackTrace();
			close();
		}		
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
