<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列表展示</title>
<link rel="stylesheet" href="<%=basePath%>static/css/style.css">
</head>
<body>
	<h2>用户列表</h2>
	<table class="gridtable">
		<tr>
			<th>id</th>
			<th>userName</th>
			<th>userAge</th>
			<th>userAddress</th>
		</tr>

		<c:forEach items="${users}" var="item">
			<tr>
				<td>${item.id }</td>
				<td>${item.userName }</td>
				<td>${item.userAge }</td>
				<td>${item.userAddress }</td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>