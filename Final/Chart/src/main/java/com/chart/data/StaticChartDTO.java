package com.chart.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaticChartDTO {
	
	private String date, name;
	//private int volume;
	private double open, high, low, close;
}
