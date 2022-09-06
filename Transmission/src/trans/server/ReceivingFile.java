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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceivingFile
{
	private static final int BUFFER_SIZE=1024;
	
	private Socket socket;
	private String repository;
	private String filePath;
	private boolean consistency;
	
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
		this.filePath = null;
		this.consistency = false;
		
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
	
	public boolean receiveFile()
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
			filePath = getAccuratePath(dis.readUTF());
			
			File file = new File(filePath);
			
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
				
				// 정합성 검사 결과 발신용 스트림 open
				os = socket.getOutputStream();
				bos = new BufferedOutputStream(os);
				dos = new DataOutputStream(bos);
				 
				// 정합성 검사 결과 발신
				consistency = checkConsistency(file, fileSize);
				dos.writeBoolean(consistency);
				
				if (logger.getLevel() == Level.INFO ||
						logger.getLevel() == Level.CONFIG || 
							logger.getLevel() == Level.FINE || 
								logger.getLevel() == Level.FINER || 
									logger.getLevel() == Level.FINEST)
				{
					sb = new StringBuffer(file.getName());
					sb.append(" 정합성 검사 결과");
					sb.append(consistency ? " 성공": " 실패");
					logger.config(sb.toString());
				}
				
			}
			else
			{
				// 전달받은 디렉토리 생성
				existDir(file.getPath());
				
				consistency = true;
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
		return consistency;
	}
	
	public void delete()
	{
		if (filePath != null)
		{
			File file = new File(filePath);
			
			StringBuffer sb = new StringBuffer("정합성 검사 실패한 파일 ");
			sb.append(file.getPath());
			
			if (file.delete())
			{
				sb.append(" 삭제 성공");
			}
			else
			{
				sb.append(" 삭제 실패");
			}
			
			logger.info(sb.toString());
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
	
	private boolean checkConsistency (File file, long fileSize)
	{
		return (file.length() != fileSize) ? false: true;
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
