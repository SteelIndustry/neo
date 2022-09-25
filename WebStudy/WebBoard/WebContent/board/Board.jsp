<%@page import="model.member.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	System.out.println("name: " + session.getAttribute("name"));
	System.out.println("id: " + session.getAttribute("id"));
%>
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
		if ( $("input[name='keyword']").val().trim() == '')
			return;
		else
		{
			$("#searchForm").submit();
		}
	});
	
	$("#resetBtn").on("click", function() {
		location.href="board.do";
	});
	
})</script>
</head>
<body>
	<h2>게시판 - 목록 보기</h2>
	
	<!-- 검색 폼 -->
	<form method="get" id="searchForm">
	<table border="1" style="width:90%;">
	<tr>
		<td align="right">
			<button id="resetBtn" type="button">검색 초기화</button>		
			<label><input type="checkbox" name="title" value="title"
				   <c:if test="${param.title != null}">checked</c:if>/>제목</label>
			<label><input type="checkbox" name="content" value="content"
				   <c:if test="${param.content != null}">checked="checked"</c:if>/>내용</label>
			<input type="text" name="keyword"
				<c:if test="${param.keyword != null}">value="${param.keyword }"</c:if>/>
			<button id="searchBtn" type="button">검색하기</button>
		</td>
	</tr>
	</table>
	</form>
	
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
				<td align="left"><a href="detail.do?num=${row.num }">${row.title }</a></td>
				<td>${row.name }</td>
				<%-- <td>${map.totalCount- (((map.pageNum-1) * map.pageSize) + loop.index) }</td> --%>
				<td>${row.visitcount }</td>
				<td>${row.postdate }</td>
				<%-- 
				<td>
				<c:if test="${ not empty row.ofile }">
					<a href="../mvcboard/download.do?ofile=${ row.ofile }&sfile=${row.sfile}&idx=${row.idx}">[Down]</a>
				</c:if>
				</td>
				--%>
			</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
	</table>
	
	<!-- 하단 메뉴(바로가기, 글쓰기) -->
	<table border="1" style="width:90%;">
		<tr align="center">
			<td><jsp:include page="/layout/Paging.jsp"/></td>
			<td style="width:100;">
				<button type="button" onclick="location.href='write.do';">글쓰기</button>
			</td>
		</tr>
	</table>
</body>
</html>