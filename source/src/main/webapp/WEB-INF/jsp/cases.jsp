<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>案件一覧</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
</head>
<body>

	<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
	<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
 <!--検索系統 -->	
	<h1>案件一覧</h1>
	
		<form action="${pageContext.request.contextPath}/Controller" method="get">
		    <label>キーワード:</label>
		    <input type="text" name="keyword">
		    
		    ステータス
		    <select name="status">
			    <option value="すべて">すべて</option>
			    <option value="進行中">進行中</option>
			    <option value="完了">完了</option>
			    <option value="中止">中止</option>
		    </select>
		    
		    優先度
		    <select name="priority">
			    <option value="すべて">すべて</option>
			    <option value="高">高</option>
			    <option value="中">中</option>
			    <option value="低">低</option>
		    </select>
		    <input type="submit" value="クリア">
		    <input type="submit" value="検索">
		
			<input type="button" value="新規登録">
		</form>
<!--案件一覧 -->
<table class="table" id="cases"border="1" >
	
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
					<td>${e.ActualHoursSum}</td>
					<td>
				<!-- 編集ボタン -->
				<form method="POST" action="<c:url value='/Controller'/>">
					<input type="hidden" name="pageId" value="C002">
					<input type="hidden" name="caseId" value="${e.caseId}"> 
					<input type="submit" name="buttonId" value="編集">
				</form>
			</td>
				</tr>
			</c:forEach>
		</tbody>
	
</table>

	<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>
</body>
</html>