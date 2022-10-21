package com.chart.data;

import java.util.ArrayList;

public interface IStockAccountDAO {
	public int getCount();
	
	public ArrayList<StockAccountDTO> getList();
	
	public ArrayList<StockAccountDTO> getTotal();
	
	public ArrayList<StockAccountDTO> getIdAccount();
	
	public ArrayList<StockAccountDTO> getTransaction();
}
