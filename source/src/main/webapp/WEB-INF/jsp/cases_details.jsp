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
	<table class="table" id="cases_table">
	
		<c:forEach var="c" items="${casesList}">
			<tr>
				<td>案件コード</td>
				<td>${c.caseCode}</td>
			</tr>

			<tr>
				<td>案件名</td>
			</tr>
		
		
			<tr>
				<td>顧客名</td>
			</tr>
			<tr>
				<td>PM</td>
			</tr>
			<tr>
				<td>ステータス</td>
			</tr>
			<tr>
				<td>優先度</td>
			</tr>
			<tr>
				<td>期間</td>
			</tr>
			<tr>
				<td>予定工数</td>
			</tr>
			<tr>
				<td>実績工数</td>
			</tr>
			<tr>
				<td>タスク進捗</td>
				<td>${}</td>
			</tr>
			<tr>
				<td>説明</td>
				<td></td>
			</tr>
		</c:forEach>
	</table>
	
</div>	

<div class="table" id="tasks">
	<h2>タスク一覧</h2>
	<table class="table" id="tasks_table">
		<tr>
			<td>タスク名</td>
			<td>担当者</td>
			<td>ステータス</td>
			<td>優先度</td>
			<td>期限</td>
			<td>予定/実績工数</td>
			<td>進捗</td>
			<td>操作</td>
		</tr>
	</table>
</div>

<div class="table" id="works">
	<h2>工数ログ</h2>
	<table class="table" id="works_table">
		<tr>
			<td>作業日</td>
			<td>タスク名</td>
			<td>担当者</td>
			<td>工数</td>
			<td>作業内容</td>
		</tr>
	</table>
</div>
</main>
	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

</body>
</html>