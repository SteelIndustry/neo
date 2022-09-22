<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="util.DBConn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%

	Connection con = DBConn.getConnection();

	String sql = "select * from board where num = 2";

	Statement smt = con.createStatement();
	
	ResultSet rs = smt.executeQuery(sql);
	
	String title = "fff";	
	
	if (rs.next())
	{
		title = rs.getString("title");
	}
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Title</title>
</head>
<body>
	Hello World!
	
	<table>
		<tr>
			<td><%=title %></td>
		</tr>
	</table>
</body>
</html>