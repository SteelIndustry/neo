package trans.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SendFile
{
	private Socket socket;
	
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
				dos.writeUTF("체크");
				dos.flush();
				
			}
			// 빈 디렉토리 보낼 때
			else
			{
				dos.writeBoolean(true);
				dos.writeUTF(path);
				
				dos.writeUTF("체크");
				dos.flush();
			}
			
			
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally {
			
			close();
		}
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
