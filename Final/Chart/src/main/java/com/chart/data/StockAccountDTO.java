package com.chart.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockAccountDTO {
	
	private String accountNum, itemCode, buySell, valProfitLoss, updateTime, id, idAccount;
	private int unsettled, total;
	private double avgPrice, currPrice;
}
