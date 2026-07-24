<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>案件詳細</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
</head>

<body>
	<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
	<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
	
 <main>
<h1>案件詳細</h1>
<h2></h2>
<div class="table" id="cases">
	<h2>基本情報</h2>
	<input type="hidden" name="page_id" value="C003">
	<table class="table" id="cases_table">
	
		<c:forEach var="c" items="${casesList}">
			<tr>
				<td>案件コード</td>
				<td>${c.caseCode}</td>
			</tr>

			<tr>
				<td>案件名</td>
				<td>${c.caseName}</td>
			</tr>
			<tr>
				<td>顧客名</td>
				<td>${c.customerName}</td>
			</tr>
			<tr>
				<td>PM</td>
				<td>${c.userName}</td>
			</tr>
			<tr>
				<td>ステータス</td>
				<td>${c.caseStatus}</td>
			</tr>
			<tr>
				<td>優先度</td>
				<td>${c.casePriority}</td>
			</tr>
		</c:forEach>
		<c:forEach var="t" items="${tasksList}">
			<tr>
				<td>期間</td>
				<td>${t.deadLine}</td>
			</tr>
		</c:forEach>
		<c:forEach var="c" items="${casesList}">
			<tr>
				<td>予定工数</td>
				<td>${c.casePlannedHours}</td>
		</c:forEach>
		<c:forEach var="t" items="${tasksList}">
			</tr>
			<!-- tasksから -->
			<tr>
				<td>実績工数</td>
				<td>${t.actualHours}</td>
			</tr>
			<tr>
				<td>タスク進捗</td>
				<td>${t.progressRate}</td>
			</tr>
		</c:forEach>
		<c:forEach var="c" items="${casesList}">
			<tr>
				<td>説明</td>
				<td>${c.caseDescription}</td>
				<td>編集</td>
			</tr>
		</c:forEach>
	</table>
	
</div>	

<div class="table" id="tasks">
	<h2>タスク一覧</h2>
	<table class="table" id="tasks_table">
		<c:forEach var="t" items="${tasksList}">
		<tr>
			<td>タスク名</td><td>${t.taskName}</td>
			<td>担当者</td><td>${t.userName}</td>
			<td>ステータス</td><td>${t.taskStatus}</td>
			<td>優先度</td><td>${t.taskPriority}</td>
			<td>期限</td><td>${t.deadLine}</td>
			<td>予定/実績工数</td>
			<td>${t.taskPlannedHours}</td>/<td>${t.actualHours}</td>
			<td>進捗</td><td>${t.progressRate}</td>
			<td>操作</td><input>
		</tr>
		</c:forEach>
	</table>
</div>

<div class="table" id="works">
	<h2>工数ログ</h2>
	<c:forEach var="w" items="${worksList}">
	<table class="table" id="works_table">
		<tr>
			<td>作業日</td><td>${w.workDate}</td>
			<td>タスク名</td><td>${w.taskName}</td>
			<td>担当者</td><td>${w.userName}</td>
			<td>工数</td><td>${w.actualHours}</td>
			<td>作業内容</td><td>${w.workDescription}</td>
		</tr>
	</table>
	</c:forEach>
</div>
</main>
	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

</body>
</html>