package trans.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSIde
{
	public static void main(String[] args)
	{
		ServerSocket serverSocket = null;
		
		String sourcePath = "C:/TMTest/dest";
		
		try
		{
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("localhost", 10304));
			
			
			while (true)
			{
				System.out.println("[연결 기다림]");
				Socket socket = serverSocket.accept();
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("[연결 수락함] " + isa.getHostName());
				
				
				//InputStream is = socket.getInputStream();
				
				/*
				bytes = new byte[100];
				int readByteCount = is.read(bytes);
				message = new String(bytes, 0, readByteCount, "UTF-8");
				*/
				
				//System.out.println("[데이터 받기 성공]: " + message);
				
				Receive receive = new Receive(socket, sourcePath);
				receive.existDir(sourcePath);
				
				receive.existDir("C:/TMTest/dest");
				
				receive.getInfo();
				
				System.out.println("[데이터 받기 성공]");
				
				//is.close();
				
				/*
				OutputStream os = socket.getOutputStream();
				message = "Hello Client";
				bytes = message.getBytes("UTF-8");
				os.write(bytes);
				os.flush();
				System.out.println("[데이터 보내기 성공]");
				
				is.close();
				os.close();
				
				*/
				//socket.close();
				receive.closeSocket();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(!serverSocket.isClosed())
			{
				try
				{
					serverSocket.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
