<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#submitBtn").on("click", function() {
		// 에러 메시지 초기화
		$('span:visible').css('display', 'none');
		
		// 아이디 중복 검사
		var checkId = false;
		if (!checkId)
			$("#errIdMsg").css('display', 'block');
		
		// 비밀번호 정확히 입력되었는지 확인
		var pwds = $("input[name='password']");
		
		if (pwds[0].value != pwds[1].value
				|| pwds[0].value == "")
			$("#errPwdMsg").css('display', 'block');
	});
})
</script>
</head>
<body>
<form action="" method="post">

	<table>
		<tr>
			<th>ID</th>
			<td><input type="text" name="id" placeholder="ID 입력"/></td>
			<td><button type="button">아이디 중복 검사</button></td>
			<td><span id="errIdMsg" style="color: red; display: none;">중복된 아이디입니다.</span></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input type="text" name="name" placeholder="이름 입력"/></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="password" placeholder="비밀번호 입력"/></td>
			<td><span id="errPwdMsg" style="color: red; display: none;" >비밀번호가 틀렸습니다.</span></td>
		</tr>
		<tr>
			<th></th>
			<td><input type="password" name="password" placeholder="비밀번호 재입력"/></td>
		</tr>
	</table>
	<br />
	<button id="submitBtn" type="button">확인</button>
	<button type="reset">리셋</button>
	<button type="button">취소</button>	
</form>
</body>
</html>