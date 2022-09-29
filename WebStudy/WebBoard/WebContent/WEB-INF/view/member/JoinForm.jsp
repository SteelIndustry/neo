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
		
		if ($('input[name="id"]').val() == '')
		{
			alert("아이디 입력해주세요.")
			return;
		}
		if ($('input[name="name"]').val() == '')
		{
			alert("이름 입력해주세요.")
			return;
		}
		
		var pwds = $("input[name='password']");
		
		if (pwds[0].value == '' || pwds[1].value == '')
		{
			alert("비밀번호 입력해주세요.")
			return;
		}
		
		// 비밀번호 정확히 입력되었는지 확인
		if (pwds[0].value != pwds[1].value)
		{
			$("#errPwdMsg").css('display', 'block');
			retur;
		}
		$("#joinForm").submit();
	});
	
	// 아이디 중복 검사
	$('#searchBtn').on("click", function() {
		// 에러 메시지 초기화
		$('span:visible').css('display', 'none');
		
		var id = $('input[name="id"]').val();
		var data = "id="+id;
		
		$.ajax({
			type : "GET"
			, url : "duplicationcheck.do"	// 요청 URL
			, data : data
			, dataType : "text"
			, success : function(data) {
				data = Number(data);
				
				if(data > 0) {
					$("#errIdMsg").css('display', 'block').html("중복된 아이디입니다.");
				}
				else
				{
					$("#errIdMsg").css('display', 'block').html("사용가능한 아이디입니다.");
				}
			}
		});
	});
})
</script>
</head>
<body>
<form id="joinForm" action="join.do" method="post">
	<input type="hidden" name="type" value="${type }"/>
	<input type="hidden" name="access" value="true" />
	<table>
		<tr>
			<th>ID</th>
			<td><input type="text" name="id" placeholder="ID 입력"/></td>
			<td><button id="searchBtn" type="button">아이디 중복 검사</button></td>
			<td><span id="errIdMsg" style="color: red; display: none;">중복된 아이디입니다.</span></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input type="text" name="name" placeholder="이름 입력"/></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="password" placeholder="비밀번호 입력"/></td>
			<td><span id="errPwdMsg" style="color: red; display: none;" >두 비밀번호가 일치하지 않습니다.</span></td>
		</tr>
		<tr>
			<th>재입력</th>
			<td><input type="password" name="password" placeholder="비밀번호 재입력"/></td>
		</tr>
	</table>
	<br />
	<button id="submitBtn" type="button">확인</button>
	<button type="reset">리셋</button>
	<button type="button" onclick="location.href='login.do';">취소</button>	
</form>
</body>
</html>