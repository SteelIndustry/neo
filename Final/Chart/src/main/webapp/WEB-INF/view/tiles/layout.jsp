<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="resourcesPath" value="${contextPath}/resources"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>레이아웃</title>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<link rel="stylesheet" href="${resourcesPath}/css/common.css">
</head>
<body>
<div class='wrap'>
	<tiles:insertAttribute name="header"/>
	<div class='content'>
		<tiles:insertAttribute name="left"/>
		<div class="page_content">
			<tiles:insertAttribute name="body"/>	
		</div>
	</div>
	<tiles:insertAttribute name="footer"/>	
</div>
</body>
</html>