package com.chart.current.handler;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@SuppressWarnings("restriction")
public class MyWebSocketHandler extends TextWebSocketHandler{
	
	private static Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>();
	//private static List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.put(session.getId(), session);
		System.out.println(session.getId()+" 입장");
		
		/*
		Iterator<String> sessionIds = sessions.keySet().iterator();
		String sessionId = "";
		while (sessionIds.hasNext()) {
			sessionId = sessionIds.next();
			sessions.get(sessionId).sendMessage(new TextMessage("[" + session.getId() + "] 입장."));
		}
		
		*/		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println(session.getId()+" 퇴장");
		sessions.remove(session.getId());
		
		/*
		Iterator<String> sessionIds = sessions.keySet().iterator();
		String sessionId = "";
		while (sessionIds.hasNext()) {
			sessionId = sessionIds.next();
			sessions.get(sessionId).sendMessage(new TextMessage("[" + session.getId() + "] 퇴장."));
		}
		*/
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		//System.out.println(session.getId()+"이(가) 데이터 요청");
		
		JSONArray data = new JSONArray();		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		data.put(sdf.format(now));
		
		OperatingSystemMXBean osbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		String cpu = String.format("%.2f", osbean.getSystemCpuLoad() * 100);
		data.put(cpu);
		
		
		Iterator<String> sessionIds = sessions.keySet().iterator();
		
		String sessionId = "";
		while (sessionIds.hasNext()) {
			sessionId = sessionIds.next();
			//sessions.get(sessionId).sendMessage(new TextMessage(session.getId() + " : " + message.getPayload()));
			
			sessions.get(sessionId).sendMessage(new TextMessage(data.toString()));
		}
	}
}
