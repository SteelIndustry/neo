package trans.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LogSetting
{
	private Logger logger;
	private FileHandler fileHandler;
	
	LogSetting()
	{
		logger = Logger.getLogger("client");
	}
	
	public Logger getLogger() { return logger; }
	
	public void setLog(String path, Level level)
	{
		try
		{
			// 기존 핸들러 삭제
			Logger rootLogger = Logger.getLogger("");
	        Handler[] handlers = rootLogger.getHandlers();
			if (handlers[0] instanceof ConsoleHandler) {
	            rootLogger.removeHandler(handlers[0]);
	        }
			
			// 파일 핸들러
			fileHandler = new FileHandler(path, true);
			fileHandler.setLevel(level);
			
			LogFormatter formatter = new LogFormatter();			
			fileHandler.setFormatter(formatter);
			
			logger.addHandler(fileHandler);
			
			// 콘솔 핸들러
			Handler handler = new ConsoleHandler();
			handler.setLevel(level);
			handler.setFormatter(formatter);
			
			logger.addHandler(handler);
			
		} catch (SecurityException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private class LogFormatter extends Formatter
	{
		@Override
		public String format(LogRecord record)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = new Date(record.getMillis());
			
			StringBuffer sb = new StringBuffer(1024);
			
			sb.append(dateFormat.format(date));
			
			sb.append(" [");
			sb.append(record.getLevel());
			sb.append("]");
			
			sb.append(" [");
			sb.append(record.getSourceClassName());
			sb.append(" ");
			sb.append(record.getSourceMethodName());
			sb.append("] ");
			sb.append(record.getMessage());
			sb.append("\n");
			
			return sb.toString();
		}
	}
}
