package com.chart.current;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class CurrentPriceServiceImpl implements ICurrentPriceService{

	@Override
	public void getList(HttpServletResponse response) {
		
		JSONArray data = new JSONArray();		
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		data.put(sdf.format(now));
		data.put(Math.random());
		
		try {
			response.getWriter().print(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
