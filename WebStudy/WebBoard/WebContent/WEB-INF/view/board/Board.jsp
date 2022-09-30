<%@page import="model.member.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유 게시판</title>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<style>a{text-decoration:none;}</style>
<script>
$(function() {
	$("#searchBtn").on("click", function() {
		
		$("input").remove(".formTag");
		
		var title = $("#title").val().trim();
		var content = $("#content").val().trim();
		
		if ( title == '' && content == '')
			return;
		else
		{
			var form = $("#searchForm");
			
			if (title != '')
			{
				var tagStr = "<input type='hidden' class='formTag' name='title' value='"+title+"'/>"
				form.append(tagStr);
			}
			if (content != '')
			{
				var tagStr = "<input type='hidden' class='formTag' name='content' value='"+content+"'/>"
				form.append(tagStr);
			}
			
			form.submit();
		}
	});
	
	$("#resetBtn").on("click", function() {
		location.href="board.do";
	});
	
});
</script>
</head>
<body>
	<h2>게시판 - 목록 보기</h2>
	
	<!-- 검색 폼 -->
	<form method="get" id="searchForm">
		<input type="hidden" name="type" value="${type }" />
		<input type="hidden" name="search" value="search"/>
	</form>
	
	<table border="1" style="width:90%;">
	<tr>
		<td align="right">
			<button id="resetBtn" type="button">검색 초기화</button>		
			<label>제목 <input type="text" id="title" maxlength="20"
				value="<c:if test="${param.title != null}">${param.title }</c:if>"/></label>
			<label>내용 <input type="text" id="content" maxlength="20"
				value="<c:if test="${param.content != null}">${param.content }</c:if>"/></label>
			<button id="searchBtn" type="button">검색하기</button>
		</td>
	</tr>
	</table>
	
	
	<!-- 목록 테이블 -->
	<table border="1" style="width:90%;">
		<tr>
			<th style="width:10%;">번호</th>
			<th style="width:*;">제목</th>
			<th style="width:15%;">작성자(닉네임)</th>
			<th style="width:10%;">조회수</th>
			<th style="width:15%;">작성일</th>
			<!-- <th style="width:8%;">첨부</th> -->
		</tr>
<c:choose>
	<c:when test="${ empty boardLists} ">
		<tr>
			<td colspan="6" align="center">등록된 게시물이 없습니다^^*</td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${lists }" var="row" varStatus="loop">
			<tr align="center">
				<td>${row.num }</td>
				<td align="left"><a href="detail.do?num=${row.num }&type=${type}">${row.title }</a></td>
				<td>${row.name }</td>
				<%-- <td>${map.totalCount- (((map.pageNum-1) * map.pageSize) + loop.index) }</td> --%>
				<td>${row.visitcount }</td>
				<td>${row.postdate }</td>
			</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
	</table>
	
	<!-- 하단 메뉴(바로가기, 글쓰기) -->
	<br />
	<div style="width:90%;" align="right">
		<button type="button" onclick="location.href='writeform.do?type=${type}';">글쓰기</button>
	</div>
</body>
</html>