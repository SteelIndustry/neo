<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<table border="1" style="width:90%;">
	<tr>
		<td align="center">
		<!-- 로그인 여부에 따른 메뉴 변화 -->
		<% if (session.getAttribute("UserId") == null) { %>
			<!-- 상대 경로 --><a href="../06Session/LoginForm.jsp">로그인</a>
			<!-- 절대 경로 <a href="/HelloWorld/06Session/LoginForm.jsp">로그인</a> --> 
		<% } else { %>
			<!-- 상대 경로 --><a href="../06Session/Logout.jsp">로그아웃</a>
			<!-- 절대 경로 <a href="/HelloWorld/06Session/Logout.jsp">로그아웃</a> -->
		<% } %>
			<!-- 회원제 게시판 -->
			&nbsp;&nbsp;&nbsp;
			<a href="../08Board/List.jsp">게시판(페이징X)</a>
			&nbsp;&nbsp;&nbsp;
			<a href="../09PagingBoard/List.jsp">게시판(페이징O)</a>
		</td>
	</tr>
</table>