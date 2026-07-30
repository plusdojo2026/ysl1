<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<div class="area">
<title>案件一覧</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
<link rel="stylesheet" href="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.css"/>
<link rel="stylesheet" href="<c:url value='/css/cases.css' />">
</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
	<%@ include file="/WEB-INF/jsp/common/side_menu.jsp"%>
	<main>
	<!--検索系統 -->
	<div class="area">
	<h1 class="title">案件一覧</h1>

	<form action="${pageContext.request.contextPath}/Controller"
		method="get">
		<input type="hidden"name="pageId"value="C001">
		<!--  
		<label>キーワード:</label> <input type="text" name="keyword"> ステータス-->
		
	<!-- 検索条件 -->
	<div class="search_area">
		<!-- ステータスの選択 -->
		<div class="search">
		<label for="caseStatus">ステータス</label>
			<select id="caseStatus">
				<option value="">すべて</option>
				<option value="進行中">進行中</option>
				<option value="完了">完了</option>
				<option value="中止">中止</option>		
			</select>
		</div>
		
		<!-- 優先度の選択 -->
		<div class="search">
		<label for="casePriority">優先度</label>
			<select id="casePriority">
				<option value="">すべて</option>
				<option value="高">高</option>
				<option value="中">中</option>
				<option value="低">低</option>
		 	</select> 
		</div>
	</div>
			<div class="btnarea">
			<input type="submit" id="regist" name="buttonId"value="＋新規登録">
			</div>
	</div>
	</form>
	<!--案件一覧 -->
	<p style="color:red;">${msg}</p>
	<table class="table" id="cases" border="1" >

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
				<th>編集</th>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach var="e" items="${casesList}">
				<tr>
					<td><a href="${pageContext.request.contextPath}/Controller?pageId=C003&caseId=${e.caseId}">
					${e.caseCode}
					</td>
					<td><a href="${pageContext.request.contextPath}/Controller?pageId=C003&caseId=${e.caseId}">
					${e.caseName}</td>
					<td>${e.customerName}</td>
					<td>${e.caseStatus}</td>
					<td>${e.casePriority}</td>
					<td>${e.userName}</td>
					<td>${e.startDate.substring(0,10)}</td>
					<td>${e.plannedEndDate.substring(0,10)}</td>
					<td>
					<progress value="${e.caseNow}" max="${e.caseSum}"></progress>
					${e.caseNow}/${e.caseSum}
					</td>
					<td>${e.actualHoursSum}</td>
					<td>
						<!-- 編集ボタン -->
						<form method="POST" action="<c:url value='/Controller'/>">
							<input type="hidden" name="pageId" value="C001">
							 <input	type="hidden" name="id" value="${e.caseId}"> 
							 <input	type="submit" id="edit" name="buttonId" value="編集">
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>

	<%@ include file="/WEB-INF/jsp/common/footer.jsp"%>
	</main>
	<script src="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.js"></script>
	<script src="<c:url value='/js/case.js' />"></script>
	<script src="<c:url value='/js/common.js' />"></script>
</body>
</html>