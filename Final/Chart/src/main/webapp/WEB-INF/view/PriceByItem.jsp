<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>종목별 가격</title>
<style type="text/css">
#container {
	height: 400px;
	min-width: 310px;
}
</style>
<script src="https://code.highcharts.com/stock/highstock.js"></script>
<script src="https://code.highcharts.com/stock/modules/data.js"></script>
<script src="https://code.highcharts.com/stock/modules/exporting.js"></script>
<script src="https://code.highcharts.com/stock/modules/accessibility.js"></script>
<script type="text/javascript">
//Highcharts.getJSON('https://demo-live-data.highcharts.com/aapl-ohlc.json',
window.onload = function() {
	
	var data = ${result}
	//data = JSON.parse(data);
	
	// create the chart
	Highcharts.stockChart('container', {
		rangeSelector : {
			selected : 1
		},

		title : {
			text : 'Stock Price'
		},

		series : [ {
			type : 'candlestick',
			name : 'AAPL Stock Price',
			data : data,
			dataGrouping : {
				units : [ [ 'week', // unit name
				[ 1 ] // allowed multiples
				], [ 'month', [ 1, 2, 3, 4, 6 ] ] ]
			}
		} ]
	});
} 
</script>
</head>
<body>
	<div id="container"></div>
</body>
</html>