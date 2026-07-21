<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
<link rel="stylesheet" href="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.css"/>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
	<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>

 <main>	
<h1>タスク一覧</h1>
<!-- 検索条件 -->
<div class="search_erea">
	<!-- 検索条件の入力フォーム -->
	<label for="keyword">キーワードを検索</label>
		<input type="text" name="keyword" id="keyword" placeholder="キーワードを入力してください">
	
	<!-- 案件名の選択 -->
	<label for="caseName">案件名</label>
		<select id="caseName">
			<option value="">すべて</option>
			<c:forEach var="e" items="${caseNameList}">
				<!-- casename　に "e" という名前をつけたよ-->
				<option value="${e}">${e}</option>	
				<!-- valueはシステムに送る値 ${e}は画面に表示するものを示す-->
			</c:forEach>
		</select>

	<!-- ステータスの選択 -->
	<label for="taskStatus">ステータス</label>
		<select id="taskStatus">
			<option value="">すべて</option>
			<option value="進行中">進行中</option>
			<option value="完了">完了</option>
			<option value="保留">保留</option>
			<option value="未着手">未着手</option>			
		</select>

	<!-- 担当者の選択 -->
	<label for="managerId">担当者</label>
		<select id="managerId">
			<option value="">すべて</option>
			<c:forEach var="e" items="${managerList}">
				<option value="${e}">${e}</option>
			</c:forEach>
		</select>
</div>

<!-- タスクの一覧表示 -->
<table class="table" id="tasks_table" border="1">	
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
		<tr>
			<td>${e.caseName}</td>
			<td>${e.taskName}</td>
			<td>${e.managerId}</td>
			<td>${e.taskStatus}</td>
			<td>${e.taskPriority}</td>
			<td>${e.deadline}</td>
			<td>
				${e.taskPlannedHours}/${e.actualHours}
			</td>
			<td>${e.progressRate}</td>
			<td>
				<!-- 編集ボタン -->
				<form method="POST" action="<c:url value='/Controller'/>">
					<input type="hidden" name="pageId" value="T001">
					<input type="hidden" name="taskId" value="${e.taskId}"> 
					<input type="hidden" name="buttonId" value="編集">
					<input type="image"  src="<c:url value='/images/edit.png'/>" alt="編集">
				</form>
			</td>
		</tr>
	</c:forEach>	
	</tbody>
</table>
</div>
</main>
	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
<script src="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.js"></script>
<script src="<c:url value='/js/tasks.js' />"></script>
<script src="<c:url value='/js/common.js' />"></script>
</body>
</html>
	