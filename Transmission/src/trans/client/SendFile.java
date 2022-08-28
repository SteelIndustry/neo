package trans.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SendFile
{
	private Socket socket;
	
	private InputStream is;
	private BufferedInputStream bis2;
	private DataInputStream dis;
	
	private OutputStream os;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	private DataOutputStream dos;
	
	private String path, ip;
	private int port;
	private File file;
	
	public SendFile(String path, String ip, int port, File file)
	{
		this.path = path;
		this.ip = ip;
		this.port = port;
		this.file = file;
		
		sendTask();
	}
	
	private void sendTask()
	{
		// 소켓, 스트림 open
		socket = new Socket();
		
		try
		{
			socket.connect(new InetSocketAddress(ip, port));
			os = socket.getOutputStream();
			
			if (!file.isDirectory())
			{
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
			}
			
			bos = new BufferedOutputStream(os);
			dos = new DataOutputStream(bos);
						
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		// 전송
		try
		{
			if (!file.isDirectory())
			{
				dos.writeBoolean(false);
				
				dos.writeUTF(path);
				dos.writeUTF(file.getName());
				dos.writeLong(file.length());
				
				int data = -1;
				byte[] bytes = new byte[1024];
				
				while ((data = bis.read(bytes)) != -1)
				{
					dos.write(bytes, 0, data);
				}
				
				dos.flush();
				
				/*
				InputStream is = socket.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				DataInputStream dis = new DataInputStream(bis);
				
				System.out.println(dis.readBoolean());
				*/
				
			}
			// 빈 디렉토리 보낼 때
			else
			{
				dos.writeBoolean(true);
				dos.writeUTF(path);
				
				dos.flush();
			}			
		} catch (IOException e)
		{
			e.printStackTrace();
			close();
		}
		
		// 정합성 검사 결과 받기
		try
		{
			fis.close();
			bis.close();
			
			bis = null;
			
			is = socket.getInputStream();
			bis2 = new BufferedInputStream(is);
			dis = new DataInputStream(bis2);
			
			System.out.println("계류중");
			
			dis.readBoolean();
			System.out.println(dis.readBoolean());
			System.out.println("못해");
			
		} catch (IOException e)
		{
			e.printStackTrace();
			close();
		}
		
		close();
	}
	
	private void receiveMsg()
	{
		
			
		
	}
	
	public void close()
	{
		try
		{
			if(dos != null) dos.close();
			if(bos != null)	bos.close();
			if(os != null) os.close();			
			if(bis != null)	bis.close();
			if(fis != null)	fis.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		dos = null;
		bos = null;
		os = null;
		bis = null;
		fis = null;
		
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
