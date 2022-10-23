package com.chart.history.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chart.history.service.ITransactionHistoryService;

@Controller
public class HistoryController {
	
	@Autowired
	private ITransactionHistoryService service;
	
	@RequestMapping("/transactionhistory")
	public String getTransactionHistoryPage(Model model,
			HttpServletRequest request, HttpServletResponse response)
	{
		service.getList(model);
		
		return "/TransactionHistory";
	}
}
