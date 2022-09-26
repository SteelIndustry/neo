<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정하기(Edit)</title>
<script type="text/javascript">
	function validateForm(form) {
		if (form.title.value == "")
		{
			alert("제목을 입력하세요.");
			form.title.focus();
			return false;
		}
		if (form.content.value == "")
		{
			alert("내용을 입력하세요.");
			form.content.focus();
			return false;
		}
	}
</script>
</head>
<body>
<h2>게시판 - 수정하기(Edit)</h2>
<form name="writeFrm" method="post" 
	enctype="multipart/form-data" action="update.do" 
	onsubmit="return validateForm(this);">

<input type="hidden" name="num" value="${dto.num }" />

<input type="hidden" name="prevFileName" value="${dto.fileName }" />
<input type="hidden" name="prevSavedName" value="${dto.savedName }" />
	
<table border="1" style="width:90%;">
	<tr>
		<td>제목</td>
		<td><input type="text" name="title" style="width:90%;" value="${dto.title}" /></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea name="content" style="width:90%;height:100px;">${dto.content }</textarea></td>
	</tr>
	 
	<tr>
		<td>첨부 파일</td>
		<td><input type="file" name="fileName" /></td>
	</tr>
	
	<tr>
		<td colspan="2" align="center">
			<button type="submit">작성 완료</button>
			<button type="reset">RESET</button>
			<button type="button" onclick="location.href='board.do'">목록 바로가기</button>
		</td>
	</tr>
</table>	
</form>

</body>
</html>