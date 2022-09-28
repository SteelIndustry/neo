<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
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
		alert("내용을 입력하세요");
		form.content.focus();
		return false;
	}
	if ($("input[name='fileName']").val() != "")
	{
		if (checkFile())
			return false;	
	}
}

function checkFile()
{
	var result = false;
	var filename = $("input[name='fileName']").val();
	var arrayExt = new Array(".hwp", ".xlsx", ".xls", ".ppt", ".pptx", ".pdf", ".gif", ".jpg", ".png", ".txt");
    var fileLen = filename.length;
    var fileCom = filename.lastIndexOf('.');
    var fileExt = filename.substring(fileCom, fileLen).toLowerCase();
    
    for(var i =0; i<arrayExt.length; i++) {
    	if(Object.is(fileExt,arrayExt[i]))
    		break;
    	
    	if ( i == (arrayExt.length -1 ))
    	{
        	alert("해당 확장자는 업로드할 수 없습니다!");
        	result = true;
        	$("input[name='fileName']").val(null);
    	}
	}
    return result;
}
</script>
</head>
<body>
<h2>파일 첨부형 게시판 - 글쓰기(Write)</h2>
<form id="writeForm" method="post" enctype="multipart/form-data" 
	action="insert.do?type=${param.type }" onsubmit="return validateForm(this);">
<table border="1" style="width:90%">
	<tr>
		<td>제목</td>
		<td><input type="text" name="title" style="width:90%;" /></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea name="content" style="width:90%;height:100px;"></textarea></td>
	</tr>
	<tr>
		<td>첨부 파일</td>
		<td><input type="file" name="fileName"/></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<button type="submit" id="submitBtn">작성 완료</button>
			<button type="reset">RESET</button>
			<button type="button" onclick="location.href='board.do?type=${param.type}';">
				목록 바로가기
			</button>
		</td>
	</tr>
</table>	
	
</form>
</body>
</html>