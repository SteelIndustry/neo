<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>종목별 계좌 거래 내역</title>
<script src="https://code.jquery.com/jquery-3.6.1.min.js">
</script>
</head>
<body>
<table border="1">
	
	<tr>
		<th>종목번호</th>
		<th>총수량</th>
	</tr>
	
	<c:forEach var="i" items="${total }">
	<tr>
		<th>${i.itemCode }</th>
		<td>${i.total }</td>
	</tr>
	</c:forEach>
	<%-- 
	<tr>
		<td>&nbsp;</td>
		<c:forEach var="i" items="${result}">
			<th>${i.id }</th>
		</c:forEach>
	</tr>
	<c:forEach var="i" items="${result}">
		<tr>
			<td>${i.itemCode }</td>
		</tr>
	</c:forEach>
	--%>
</table>
</body>
</html>