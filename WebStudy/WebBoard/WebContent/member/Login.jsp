<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#submitBtn").on("click", function() {
		// 에러 메시지 초기화
		$('span:visible').css('display', 'none');
		
		// 공백 검사
		$("input").each(function() { 
			if($(this).val() == '')
				$("#errMsg").css('display', 'block');
		});
	});
})
</script>
</head>
<body>
<form action="" method="post">
	<table>
		<tr>
			<th>ID</th>
			<td><input type="text" name="id" placeholder="ID 입력" value=""/></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="password" placeholder="비밀번호 입력" value=""/></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<button id="submitBtn" type="button">확인</button>
				<button type="button">취소</button>
				<button type="button" onclick="location.href='./Join.jsp';">회원가입</button>
			</td>
		</tr>
	</table>
	<br />
	<span id="errMsg" style="color: red; display: none;" >아이디와 비밀번호를 모두 입력해주세요.</span>
		
</form>
</body>
</html>