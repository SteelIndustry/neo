<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%!
String str1 = "JSP";
String str2 = "안녕하세요..";
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello JSP</title>
</head>
<body>
	<h2>처음 만들어보는 <%= str1 %></h2>
	<p>
		<%
		out.println(str2 + str1 + "입니다. 열공합시다.^^*");
		%>
	</p>
	<p>
		<%
		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String todayStr = dateFormat.format(today);
		out.println("오늘 날짜 : " + todayStr);
		%>
	</p>
</body>
</html>