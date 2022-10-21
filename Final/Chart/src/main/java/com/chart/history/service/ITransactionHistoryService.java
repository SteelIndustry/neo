package com.chart.history.service;

import org.springframework.ui.Model;

public interface ITransactionHistoryService {
	public int getCount(Model model);
	
	public void getList(Model model);
}
