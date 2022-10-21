package com.chart.history.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.chart.data.IStockAccountDAO;
import com.chart.data.StockAccountDTO;

@Service
public class TransactionHistoryServiceImpl implements ITransactionHistoryService{

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public int getCount(Model model) {
		IStockAccountDAO dao = sqlSession.getMapper(IStockAccountDAO.class);
		
		int result = dao.getCount();
		
		model.addAttribute("result", result);
		
		return result;
	}

	@Override
	public void getList(Model model) {
		IStockAccountDAO dao = sqlSession.getMapper(IStockAccountDAO.class);
		
		List<StockAccountDTO> result = dao.getList();
		
		model.addAttribute("result", result);
		
		List<StockAccountDTO> total = dao.getTotal();
		
		model.addAttribute("total", total);
	}
}
