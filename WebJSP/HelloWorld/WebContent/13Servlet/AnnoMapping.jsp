<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AnnoMapping.jsp</title>
</head>
<body>
	<h2>Annotation으로 매핑</h2>
	<p>
		<strong>${ message }</strong>
		<br />
		<a href="<%= request.getContextPath()%>/13Servlet/AnnoMapping.do">바로가기</a>
	</p>
</body>
</html>