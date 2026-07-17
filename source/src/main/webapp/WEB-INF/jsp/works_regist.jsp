<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>工数入力</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="header.jsp" %>
<%@ include file="side_menu.jsp" %>

<main>
<h1>工数入力</h1>
<form method="POST" action="/ysl1/ControllerServlet">
<div class="table" id="works">
案件名：${task.caseName}<br>
タスク名：${task.taskName}<br>
作業日<input type="date" name="workDate" required><br>
工数<input type="number" name="actualHours" step="0.5" required><br>
作業内容<input type="text" name="workDescription"><br>
<input type="submit" name="buttonId" value="キャンセル"><br>
<input type="submit" name="buttonId" value="登録"><br>
</form>
</div>
</main>
<%@ include file="footer.jsp" %> 
</body>
</html>