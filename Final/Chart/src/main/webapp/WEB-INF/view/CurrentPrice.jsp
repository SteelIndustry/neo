<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>현재 주가</title>
<style type="text/css">
.highcharts-figure, .highcharts-data-table table {
	min-width: 360px;
	max-width: 800px;
	margin: 1em auto;
}

.highcharts-data-table table {
	font-family: Verdana, sans-serif;
	border-collapse: collapse;
	border: 1px solid #ebebeb;
	margin: 10px auto;
	text-align: center;
	width: 100%;
	max-width: 500px;
}

.highcharts-data-table caption {
	padding: 1em 0;
	font-size: 1.2em;
	color: #555;
}

.highcharts-data-table th {
	font-weight: 600;
	padding: 0.5em;
}

.highcharts-data-table td, .highcharts-data-table th,
	.highcharts-data-table caption {
	padding: 0.5em;
}

.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even)
	{
	background: #f8f8f8;
}

.highcharts-data-table tr:hover {
	background: #f1f7ff;
}
</style>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/series-label.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
<script type="text/javascript">
var chart;
var ws;

function sendMessage() {
	if (ws.readyState == 1)	{
		ws.send("요청");	
	}
}

function onMessage(e) {
	var data = e.data;
	//$("#data").append(data + "<br>");
	data = JSON.parse(data);
	var series = chart.series[0], 
		shift = series.data.length > 20;
		
	data[0]= new Date(data[0]).getTime();
	data[1] = parseFloat(data[1]);
	chart.series[0].addPoint(data, true, shift);
}

function onOpen(e){
	$("#data").append("onOpen(): 연결<br>");
}

function onClose(e){
	$("#data").append("onClose(): 연결 종료<br>");
}

function onError(e) {
	$("#data").append("onError(): 연결 오류<br>");
}

function requestLooping() {
	sendMessage();
	setTimeout(requestLooping, 1000);
}

$(document).ready(function() {
	ws = new WebSocket("ws://192.168.1.241:8090/ws")
	
	ws.onopen = () => {

	}
	ws.onerror = onError;
	ws.onmessage = onMessage;
	ws.onclose = onClose;
	
	chart = new Highcharts.Chart({
		chart: {
			renderTo: 'container',
			defaultSeriesType: 'spline',
			events: {
				load: requestLooping
			}
		},
		title: {
			text: 'CPU Usage Chart'
		},
		xAxis: {
			type: 'datetime',
			tickPixelInterval: 150,
			maxZoom: 20 * 1000
		},
		yAxis: {
			minPadding: 0.2,
			maxPadding: 0.2,
			title: {
				text: 'Percent',
				margin: 80
			}
		},
		series: [{
			name: 'Usage',
			data: []
		}]
	});
	
	$("#sendBtn").click(function() {
		sendMessage();
	});
	
	$("#exitBtn").click(function() {
		ws.close();
	});	
});
</script>
</head>
<body>	
	<!-- 
	<input type="text" id="message" size="50" />
	<input type="button" value="SEND" id="sendBtn" />
	<div id="data"></div>
	 -->
	<figure class="highcharts-figure">
		<div id="container"></div>
	</figure>
</body>
</html>