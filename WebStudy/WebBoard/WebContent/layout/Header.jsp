<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table style="width:90%;">
	<tr>
	<td align="left">
	
	<a href="board.do">게시판1</a>
	</td>
	<td align="right">
	<c:choose>
		<c:when test="${sessionScope.name == null }">
			<a class="nonmember" href="login.do">로그인</a>	
			<a class="nonmember" href="join.do">회원가입</a>	
		</c:when>
		<c:otherwise>
			<a class="member" href="logout.do">로그아웃</a>
		</c:otherwise>
	</c:choose>
	</td>
	</tr>
</table>