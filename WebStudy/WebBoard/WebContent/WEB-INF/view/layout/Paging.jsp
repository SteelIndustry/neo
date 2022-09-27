<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageNum">
	${param.pageNum }
	<c:if test="${empty param.pageNum }">
		1
	</c:if>
</c:set>

<table style="width:90%;">
<tr align="center"><td>

<c:if test="${paging['startPage'] != 1 }">
	<a href="board.do?pageNum=1${query }">첫 페이지</a>
	&nbsp;
	<a href="board.do?pageNum=${paging['prevBlock']}
	${query }">이전 블록</a>	
</c:if>
<c:forEach var="i" begin="${paging['startPage'] }" end="${ paging['endPage']}">
	&nbsp;
	<c:choose>
		<c:when test="${i == pageNum }">
			${i }
		</c:when>
		<c:otherwise>
			<a href="board.do?pageNum=${i }&type=${type }
				${query }
			">${i }</a>
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:if test="${paging['endPage'] != paging['lastPage'] }">
	<a href="board.do?pageNum=${paging['nextBlock']}
	${query }">다음 블록</a>
	&nbsp;
	<a href="board.do?pageNum=${paging['lastPage']}
	${query }">마지막 페이지</a>	
</c:if>
		
</td></tr>
</table>

