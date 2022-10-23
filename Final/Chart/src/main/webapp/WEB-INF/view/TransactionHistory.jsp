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
		<c:forEach var="i" items="${idList }">
		<th>${i.idAccount }</th>
		</c:forEach>
		<th>총수량</th>
		<th>현재가</th>
		<th>갱신일</th>
	</tr>
	<c:forEach var="item" items="${total }">
	<tr>
		<th>${item.itemCode }</th>
		<c:forEach var="id" items="${idList }">
		<td>
			<c:forEach var="row" items="${transaction }">
				<c:choose>
				<c:when test="${item.itemCode == row.itemCode && row.idAccount == id.idAccount }">
					${row.unsettled }
				</c:when>
				<c:otherwise></c:otherwise>
				</c:choose>
			</c:forEach>
		</td>
		</c:forEach>
		<td>${item.total }</td>
		<td>${item.currPrice }</td>
		<td>${item.updateTime }</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>