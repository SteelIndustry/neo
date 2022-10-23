package com.chart.current.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chart.current.ICurrentPriceService;

@Controller
public class CurrentController {
	
	@Autowired
	private ICurrentPriceService service;
	
	@RequestMapping("/currentprice")
	public String getCurrentPricePage(Model model,
			HttpServletRequest request, HttpServletResponse response)
	{
		return "/CurrentPrice";
	}
	
	@RequestMapping("/data")
	public void getData(Model model,
			HttpServletRequest request, HttpServletResponse response)
	{
		service.getList(response);
	}
}
