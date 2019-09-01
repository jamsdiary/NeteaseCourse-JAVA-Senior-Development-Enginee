<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>set</title>
</head>
<body>      
	<h2>
		SessionID:<%=session.getId()%>   <br>
		往session中获取数据aa：<%=session.setAttribute("aa",request.getParameter("aa"))%>
	</h2>
</body>
</html>