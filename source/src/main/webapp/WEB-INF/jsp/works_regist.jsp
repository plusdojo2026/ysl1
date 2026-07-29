<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>工数入力</title
 <link rel="stylesheet" href="<c:url value='/css/common.css' />"> 
<link rel="stylesheet" href="<c:url value='/css/works_regist.css' />">
</head>
<body>
	<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
	 <%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
<main>
<h1>工数入力</h1>
<div class="table" id="works">
<form action="${pageContext.request.contextPath}/Controller" method="post">
<input type="hidden" name="pageId" value="W001">
<input type="hidden" name="taskId" value="${task.taskId}">
<!-- 案件情報 -->
<div class="info-item">案件名：${task.caseName}</div>
<div class="info-item">タスク名：${task.taskName}</div>
<!-- 入力項目 -->
<div class="field-group"><label>作業日</label><input type="datetime-local" id="workDate" name="workDate" required></div>
<div class="field-group"><label>工数</label><input type="number" name="actualHours" min="0.5" max="24" step="0.5" required></div>
<div class="field-group"><label>作業内容</label><input type="text" name="workDescription"></div>

<div class="button-group">
<input type="submit" name="buttonId" value="キャンセル" id="btn-cancel">
<input type="submit" name="buttonId" value="工数入力" id="btn-submit">
</div>
</form>
</div>
</main>
	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script src="<c:url value='/js/common.js' />"></script>
<script>
document.addEventListener('DOMContentLoaded', () => {
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());

    document.getElementById('workDate').value =
        now.toISOString().substring(0, 16);
});
</script>
</body>
</html>