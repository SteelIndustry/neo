package com.chart.price.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chart.price.IPriceByItemService;

@Controller
public class PriceController {
	
	@Autowired
	private IPriceByItemService service;
	
	@RequestMapping("/pricebyitem")
	public String getTransactionHistoryPage(Model model,
			HttpServletRequest request, HttpServletResponse response)
	{
		service.getList(model);
		
		return "/PriceByItem";
	}
}
