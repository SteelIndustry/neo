package com.chart.price;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.chart.data.IStaticChartDAO;
import com.chart.data.StaticChartDTO;

@Service
public class PriceByItemServiceImpl implements IPriceByItemService{

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void getList(Model model) {
		IStaticChartDAO dao = sqlSession.getMapper(IStaticChartDAO.class);
		List<StaticChartDTO> result = dao.getStaticList();
		
		JSONArray jsonList = new JSONArray();
		for(StaticChartDTO dto : result)
		{
			JSONArray json = new JSONArray();
			json.put(Long.parseLong(dto.getDate()));
			json.put(dto.getOpen());
			json.put(dto.getHigh());
	        json.put(dto.getLow());
	        json.put(dto.getClose());
	        jsonList.put(json);
		}
		
		model.addAttribute("result", jsonList);
		
	}
}
