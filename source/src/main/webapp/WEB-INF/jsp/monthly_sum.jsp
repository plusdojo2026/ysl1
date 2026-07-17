<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>月次集計</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="header.jsp" %>
<%@ include file="side_menu.jsp" %>

<main>
<h1>月次集計</h1>
<form method="POST" action="/ysl1/ControllerServlet">
<div class="layout">

<!-- 上部操作エリア -->
	<div class="top-area">
	<form action="${pageContext.request.contextPath}/monthlySum" method="get">
	<month
	
	
	</form>




</div>




</div>



</main>
</body>
</html>