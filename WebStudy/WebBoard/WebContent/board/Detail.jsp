<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
			<button type="button" onclick="location.href='edit.do?num=${param.num}';">수정하기</button>
			<button type="button" onclick="location.href='delete.do?num=${param.num}';">삭제하기</button>
			<button type="button" onclick="location.href='board.do';">목록 바로가기</button>
		</td>
	</tr>
</table>

</body>
</html>