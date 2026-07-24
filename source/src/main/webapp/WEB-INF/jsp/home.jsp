<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ダッシュボード</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
<link rel="stylesheet" href="<c:url value='/css/home.css' />">
</head>
<body>
	<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
	<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>

 <main>
 <div class ="count_area">
 <div class ="count">
 	<p>進行中の案件</p>
	 ${count.inProgressCase} 件
 </div>
 
 <div class ="count">
 	<p>担当タスク</p>
 	${count.myTask} 件
 </div>
 
 <div class ="count">
 	<p>期限超過件数</p>
 	${count.overDueTasks} 件
 </div>
 </div>
 
<!--案件一覧 -->
<p>案件一覧</p>
<table class="table" id="casesTable" border="1">
		<thead>
			<tr>
				<th>案件コード</th>
				<th>案件名</th>
				<th>顧客名</th>
				<th>ステータス</th>
				<th>優先度</th>
				<th>PM名</th>
				<th>開始日</th>
				<th>終了予定日</th>
				<th>タスク進捗</th>
				<th>実績工数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="e" items="${CasesList}" >
				<tr>
					<td>${e.caseCode}</td>
					<td>${e.caseName}</td>
					<td>${e.customerName}</td>
					<td>${e.caseStatus}</td>
					<td>${e.casePriority}</td>
					<td>${e.pmId}</td>
					<td>${e.startDate}</td>
					<td>${e.plannedEndDate}</td>
					<td>${e.caseNow}/${e.caseSum}</td>
					<td>${e.actualHours}</td>
				</tr>
			</c:forEach>
		</tbody>
</table>

<!-- タスクの一覧表示 -->
<p class="mytask">担当タスク一覧</p>
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
				<th>工数入力</th>
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
			<td>${e.progressRate}</td>
			<td>
			<form method="POST" action="<c:url value='/Controller'/>">
				<input type="hidden" name="page_id" value="W001">
				<input type="hidden" name="taskId" value="${e.taskId}">							
				<input type="submit" name="button_id" value="工数入力">
			</form>
			</td>
		</tr>
	</c:forEach>	
	</tbody>
</table>
</main>
	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

<script src="<c:url value='/js/common.js' />"></script>
</body>
</html>