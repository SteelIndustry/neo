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

public class OpenSocketStream
{
	Socket socket;
	
	OutputStream os;
	FileInputStream fis;
	BufferedInputStream bis;
	BufferedOutputStream bos;
	DataOutputStream dos;
	
	OpenSocketStream()
	{
		this.socket = new Socket();
	}
	
	public void connectSocket(String ip, int port)
	{
		try
		{
			socket.connect(new InetSocketAddress(ip, port));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void openStream(String path)
	{
		try
		{
			File file = new File(path);
			
			if (!file.isDirectory())
			{
				fis = new FileInputStream(path);
				bis = new BufferedInputStream(fis);
			}
			
			os = socket.getOutputStream();
			bos = new BufferedOutputStream(os);
			dos = new DataOutputStream(bos);
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void send(File file, String path)
	{
		
		try
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
			
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally {
			closeStream();
		}
	}
	
	public void send(String path)
	{
		try
		{
			dos.writeBoolean(true);
			
			dos.writeUTF(path);
			
			dos.flush();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally {
			closeStream();
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
	
	public void closeStream()
	{
		try
		{
			if(dos != null) dos.close();
			if(bos != null)	bos.close();
			if(bis != null)	bis.close();
			if(fis != null)	fis.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		dos = null;
		bos = null;
		bis = null;
		fis = null;
	}
	
}
