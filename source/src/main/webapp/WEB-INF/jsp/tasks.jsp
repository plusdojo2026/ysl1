<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
<link rel="stylesheet" href="<c:url value='/css/tasks.css' />">
<link rel="stylesheet" href="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.css"/>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
	<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>

 <main>	
<h1 class="title">タスク一覧</h1>


<!-- タスクの一覧表示 -->
<table class="table" id="tasksTable" border="1">	
	<thead>		<!-- thead:テーブルの見出しを強調表示するもの -->
			<tr>
				<th>案件名</th>
				<th>タスク名</th>
				<th>担当者</th>
				<th>ステータス</th>
				<th>優先度</th>
				<th>期限</th>
				<th>実績工数/見積</th>
				<th>進捗</th>
				<th>編集</th>
			</tr>
	</thead>

	<tbody>
	<c:forEach var="e" items="${taskList}">
		<tr 
		data-manager="${e.userName}"
		data-case="${e.caseName}"
		data-status="${e.taskStatus}">
		
			<td>${e.caseName}</td>
			<td>${e.taskName}</td>
			<td>${e.userName}</td>
			<td>${e.taskStatus}</td>
			<td>${e.taskPriority}</td>
			<td>${e.deadline}</td>
			<td>
				${e.taskPlannedHours}/${e.actualHours}
			</td>
			<td>${e.caseProgressRate}</td>
			<td>
				<!-- 編集ボタン -->
				<form method="POST" action="<c:url value='/Controller'/>">
					<input type="hidden" name="pageId" value="T001">
					<input type="hidden" name="taskId" value="${e.taskId}"> 
					<input type="submit" name="buttonId" value="編集">
					<!-- <input type="image"  src="<c:url value='/images/edit.png'/>" alt="編集"> -->
				</form>
			</td>
		</tr>
	</c:forEach>	
	</tbody>
</table>
</main>
	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script src="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.js"></script>
<script src="<c:url value='/js/task.js' />"></script>
<script src="<c:url value='/js/common.js' />"></script>
</body>
</html>
	