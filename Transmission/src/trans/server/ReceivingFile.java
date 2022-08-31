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
import java.util.logging.Logger;

public class ReceivingFile
{
	private static final int BUFFER_SIZE=1024;
	
	private Socket socket;
	private String repository;
	private Logger logger;
	
	private OutputStream os;
	private DataOutputStream dos;
	
	private InputStream is;
	private BufferedInputStream bis;
	private DataInputStream dis;
	private FileOutputStream fos;
	private BufferedOutputStream bos;
	
	public ReceivingFile (Socket socket, String repositoryPath, Logger logger)
	{
		this.socket = socket;
		this.repository = repositoryPath;
		this.logger = logger;
	}
	
	public void existDir(String path)
	{
		File file = new File(path);
		
		if (!file.exists())
		{
			file.mkdirs();
			StringBuffer sb = new StringBuffer();
			sb.append("make dir ");
			sb.append(path);
			logger.info(sb.toString());
		}
	}
	
	public void receiveFile()
	{
		try
		{
			// 스트림 open
			is = socket.getInputStream();
			bis = new BufferedInputStream(is);
			dis = new DataInputStream(bis);
						
			// 파일 or 디렉토리
			boolean isDir = dis.readBoolean();
			
			// File 객체 생성
			File file = new File(getAccuratePath(dis.readUTF()));
			
			StringBuffer sb = new StringBuffer(file.getName());
			sb.append(" 파일 수신 작업 시작");
			logger.info(sb.toString());
			
			if (!isDir)
			{
				// 디렉토리 없으면 생성
				existDir(file.getParent());
				
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos);
				
				// 정합성 검사용
				long fileSize = dis.readLong();
				
				// 파일 수신
				copy(file, fileSize);
				
				// 정합성 결과 발신
				os = socket.getOutputStream();
				bos = new BufferedOutputStream(os);
				dos = new DataOutputStream(bos);
				
				boolean check = (file.length() != fileSize) ? false : true; 
				dos.writeBoolean(check);
				
				// 정합성 검사 실패했으면 파일 삭제
				if (!check)
				{
					file.delete();
					sb = new StringBuffer("정합성 검사 실패");
					sb.append("한 파일 ");
					sb.append(file.getName());
					
				}
				logger.info(sb.toString());
			}
			else
			{
				// 전달받은 디렉토리 생성
				existDir(file.getPath());
				logger.info("폴더 생성");
			}
									
		} catch (IOException e)
		{
			e.printStackTrace();
			logger.warning(e.getMessage());
		} finally
		{
			close();
		}
	}
	
	public String getAccuratePath(String path)
	{
		return new StringBuffer(repository).append(path).toString();
	}
	
	private void copy(File file, long fileSize)
	{
		StringBuffer sb = new StringBuffer(file.getName());
		sb.append(" ");
		try
		{
			// 데이터 읽기용 버퍼
			int data = 0;
			byte[] bytes = new byte[BUFFER_SIZE];
			
			// 파일 바이트 사이즈만큼 read
			long count = 0;
			long maxCount = fileSize % BUFFER_SIZE == 0 
				? fileSize / BUFFER_SIZE 
				: fileSize / BUFFER_SIZE + 1;
			
			while ( count++ < maxCount)
			{
				data = dis.read(bytes);
				bos.write(bytes, 0, data);
			}
			bos.flush();
			sb.append("파일 수신 완료");
			logger.info(sb.toString());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
			logger.warning(sb.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
			logger.warning(sb.toString());
		}
	}
	
	public void close()
	{
		try
		{
			if (dos != null) dos.close();
			if (os != null) os.close();
			
			if(dis != null) dis.close();
			if(bis != null)	bis.close();
			if(is != null)	is.close();
			if(bos != null)	bos.close();
			if(fos != null)	fos.close();
		} catch (IOException e)
		{
			e.printStackTrace();
			logger.info("stream close error");
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
					logger.info("socket close error");
				}
			}
		}
	}
}
