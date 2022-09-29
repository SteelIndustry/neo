<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script type="text/javascript">
$(function() {
	$("#editBtn").on("click", function() {
		$("#editForm").attr("action", "editform.do");
		$("#editForm").submit();
	});
	
	$("#deleteBtn").on("click", function() {
		$("#editForm").attr("action", "delete.do");
		$("#editForm").submit();
	});
});
</script>

<title>상세 보기(Detail)</title>
</head>
<body>
<h2>게시판 - 상세 보기(Detail)</h2>

<table border="1" style="width:90%;">
	<colgroup>
		<col width="15%"/> <col width="35%"/>
		<col width="15%"/> <col width="*"/>
	</colgroup>
	
	<!-- 게시글 정보 -->
	<tr>
		<td>번호</td> <td>${dto.num }</td>
		<td>작성자(닉네임)</td> <td>${dto.name }</td>
	</tr>
	<tr>
		<td>작성일</td> <td>${ dto.postdate }</td>
		<td>조회수</td> <td>${dto.visitcount }</td>
	</tr>
	<tr>
		<td>제목</td>
		<td colspan="3">${dto.title }</td>
	</tr>
	<tr>
		<td>내용</td>
		<td colspan="3" height="100">${dto.content }</td>
	</tr>
	 
	<tr>
		<td>첨부파일</td>
		<td>
			<c:choose>
			<c:when test="${ not empty dto.fileName }">
				${dto.fileName }
				<a href="download.do?fileName=${dto.fileName }&savedName=${dto.savedName}">
					[다운로드]
				</a>
			</c:when>
			<c:otherwise>
				첨부파일 없음
			</c:otherwise>
			</c:choose>
		</td>		
	</tr>
	<!-- 하단 메뉴(버튼) -->
	<tr>
		<td colspan="4" align="center">
			<c:if test="${dto.id == sessionScope.id }">
			<form id="editForm" action="" method="post">
				<input type="hidden" name="num" value="${param.num }" />
				<input type="hidden" name="type" value="${param.type }" />
				<input type="hidden" name="access" value="true" />				
				<button id="editBtn" type="button">수정하기</button>
				<button id="deleteBtn" type="button">삭제하기</button>
			</form>
			</c:if>
			<button type="button" onclick="location.href='board.do?type=${param.type}';">목록 바로가기</button>
		</td>
	</tr>
</table>

</body>
</html>