<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>에러</title>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script type="text/javascript">
$(function() {
	alert($("#errMsg").val());
	
	var url = $("#redirect").val();
	location.href= url;	
})
</script>
</head>
<body>

<input id="errMsg" type="text" value="${errMsg }" style="display: none;"/>
<input id="redirect" type="text" value="${url }" style="display: none;"/>


</body>
</html>