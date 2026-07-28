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
	<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
	<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
<main>
<h1>工数入力</h1>
<form action="${pageContext.request.contextPath}/Controller" method="post">
<input type="hidden" name="pageId" value="W001">
<input type="hidden" name="taskId" value="${task.taskId}">
<div class="table" id="works">
案件名：${task.caseName}<br>
タスク名：${task.taskName}<br>
作業日<input type="datetime-local" name="workDate" required><br>
工数<input type="number" name="actualHours" step="0.5" required><br>
作業内容<input type="text" name="workDescription"><br>
<input type="submit" name="buttonId" value="キャンセル"><br>
<input type="submit" name="buttonId" value="工数入力"><br>
</form>
</div>
</main>
	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>s
<script src="<c:url value='/js/common.js' />"></script>
</body>
</html>