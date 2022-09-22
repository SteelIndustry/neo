<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>헤더</title>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		var str = "세션 값 받을 자리";
		
		if (false)
		{
			$(".nonmember").show();
			$(".member").hide();
		}
		else
		{
			$(".member").show();
			$(".nonmember").hide();
		}
	});
	
	
</script>
</head>
<body>
<span>
	<a href="">게시판1</a>
	<a href="">게시판2</a>
	
	<a class="nonmember" href="../member/Login.jsp">로그인</a>	
	<a class="nonmember" href="../member/Join.jsp">회원가입</a>	
	<a class="member" href="../board/List.jsp">로그아웃</a>	
</span>


</body>
</html>