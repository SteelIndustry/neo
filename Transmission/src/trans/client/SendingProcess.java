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
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class SendingProcess
{
	private static final int BUFFER_SIZE = 1024;
	
	private FileInputStream fis;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	private DataInputStream dis;
	private DataOutputStream dos;
	private InputStream is;
	private OutputStream os;
	private Socket socket;
	
	private String path, ip;
	private int port;
	private File file;
	private Logger logger;
	private boolean deleteCheck;
	
	public SendingProcess(File file, String path, Setting setting)
	{
		this.file = file;
		this.path = path;
		this.ip = setting.getIp();
		this.port = setting.getPort();
		this.logger = setting.getLogger();
		this.deleteCheck = false;
	}
	
	public boolean send()
	{
		// 소켓, 스트림 open
		socket = new Socket();
		try
		{
			socket.connect(new InetSocketAddress(ip, port), 3000);
			
			StringBuffer sb = new StringBuffer();
			sb.append("ip: ").append(ip).append("/ port: ").append(port)
				.append(socket.isConnected() ? " 연결 성공" : " 연결 실패");
			logger.info(sb.toString());
			
			os = socket.getOutputStream();
			
			// 디렉토리 아닐 때 추가 open
			if (!file.isDirectory())
			{
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
			}
			
			bos = new BufferedOutputStream(os);
			dos = new DataOutputStream(bos);
						
		} catch ( UnknownHostException e ) {
			e.printStackTrace();
			logger.severe(e.getMessage());
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			logger.warning(e.getMessage());
		} catch (IOException e)
		{
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
		
		
		// 전송
		try
		{
			if (!file.isDirectory())
			{
				int data = -1;
				long fileSize = file.length();
				long sentData = 0;
				
				
				// 디렉토리인지 파일인지
				dos.writeBoolean(false);
				
				// 파일 path
				dos.writeUTF(path);
				
				// 파일 사이즈
				dos.writeLong(fileSize);
				
				// 파일 바이트 전송
				byte[] bytes = new byte[BUFFER_SIZE];	
				while ((data = bis.read(bytes)) > 0)
				{
					dos.write(bytes, 0, data);
					sentData += data;
				}
				dos.flush();
				
				// 파일 사이즈만큼 제대로 전송했을 때
				if (sentData == fileSize)
				{
					// 정합성 결과 수신
					is = socket.getInputStream();
					bis = new BufferedInputStream(is);
					dis = new DataInputStream(bis);
					
					deleteCheck = dis.readBoolean();
				}
			}
			// 빈 디렉토리 보낼 때
			else
			{
				dos.writeBoolean(true);
				dos.writeUTF(path);
				
				dos.flush();
				
				deleteCheck = true;
			}			
		} catch (IOException e)
		{
			e.printStackTrace();
			logger.severe(e.getMessage());
		} finally {
			close();
			logger.info("소켓 닫음");
		}
		return deleteCheck;
	}
	
	public void close()
	{
		try
		{
			if (dis != null) dis.close();
			if (is != null) is.close();
						
			if(dos != null) dos.close();
			if(bos != null)	bos.close();
			if(os != null) os.close();			
			if(bis != null)	bis.close();
			if(fis != null)	fis.close();	
		} catch (IOException e)
		{
			e.printStackTrace();
			logger.info("stream close() error");
		}
		
		if (socket != null)
		{
			if (!socket.isClosed())
			{
				try
				{
					socket.close();
				} catch (IOException e)
				{
					e.printStackTrace();
					logger.info("socket close() error");
				}
			}
		}
	}
}
