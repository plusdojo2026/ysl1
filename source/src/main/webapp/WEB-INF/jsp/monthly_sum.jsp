<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>月次集計</title>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>

<main>
<h1>月次集計</h1>
<div class="layout">

	<!-- 上部操作エリア -->
	<div class="top-area">
		<form action="${pageContext.request.contextPath}/Controller" method="post">
		<input type="hidden" name="pageId" value="M001">
		<!-- 月選択 -->
		<div class="month-select">
		<input type="month" name="month" value="${selectMonth}">
		<input type="submit" name="buttonId" value="集計">
		<input type="submit" name="buttonId" value="工数一覧"> 
	
		</div>
		
	
		<!-- CSV出力 -->
		<input type="submit" name="buttonId" value="CSV出力">
		</form>

	</div>
	
	<c:if test="${displayMode == 'summary'}">
	<!-- サマリーカード -->
	<div class="summary-cards">
		<!-- 月合計工数(h) -->
		<div class="summary-card">
		<div class="summary-title">月合計工数(h)</div>
		<div class="summary-value">${monthlyTotalHours}
		<span>h</span>
		</div>
		</div>
	
		<!-- 集計案件数 -->
		<div class="summary-card">
		<div class="summary-title">集計案件数</div>
		<div class="summary-value">${caseCount}
		<span>件</span>
		</div>
		</div>
		
		<!-- 稼働メンバー数 -->
		<div class="summary-card">
		<div class="summary-title">稼働メンバー数</div>
		<div class="summary-value">${memberCount}
		<span>人</span>
		</div>
		</div>
	</div>
	
	<!-- 集計テーブル -->
	<div class="table-area">
	
		<!-- 案件別集計 -->
		<section class="table-section">
		<h2>案件別集計</h2>
		<div class="table-wrapper">
		
		<table>
		
			<thead>
			<tr>
				<th>案件コード</th>
				<th>案件名</th>
				<th>実績工数</th>
				<th>予算工数</th>
				<th>達成率</th>
			</tr>
			</thead>
			
			<tbody>
			<c:forEach var="caseData" items="${caseSumList}">
			<tr>
				<td>${caseData.caseCode}</td>
				<td>${caseData.caseName}</td>
				<td>${caseData.actualHoursSum}</td>
				<td>${caseData.casePlannedHours}h</td>
				<td>
				<div class="progress-bar">
				<div class="progress-value" style="width: ${caseData.caseProgressRate}%"></div>
				</div>
				<span>${caseData.caseProgressRate}%</span>
				</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
		<p class="note">
		※達成率 = 実績工数 / 予算工数 × 100(小数点以下切り捨て)
		</p>
		</section>
		
		<!-- メンバー別集計 -->
		<section class="table-section">
		<h2>メンバー別集計</h2>
		<div class="table-wrapper">
		
		<table>
		
			<thead>
			<tr>
				<th>担当者名</th>
				<th>工数</th>
				<th>全体に占める割合</th>
			</tr>
			</thead>
			
			<tbody>
			<c:forEach var="memberData" items="${memberSumList}">
			<tr>
				<td>${memberData.userName}</td>
				<td>${memberData.actualHours}h</td>
				<td>
				<div class="progress-bar">
				<div class="progress-value" style="width: ${memberData.workRate}%"></div>
				</div>
				<span>${memberData.workRate}%</span>
				</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
		<p class="note">
		※全体に占める割合 = 実績工数 / 月の全メンバーの実績工数×100(小数点切り捨て）
		</p>
		</section>
	</div>
	
	</c:if>
	
	
	<c:if test="${displayMode == 'workList'}">
	<!-- 工数ログ -->
	<h2>工数ログ一覧</h2>
	
	<table>
		<thead>
			<tr>
				<th>作業日</th>
				<th>案件名</th>
				<th>タスク名</th>
				<th>担当者</th>
				<th>工数</th>
				<th>作業内容</th>
			</tr>
		</thead>
		
		<tbody>
		<c:forEach var="workData" items="${workList}">
		<tr>
			<td>${workData.workDate}</td>
			<td>${workData.caseName}</td>
			<td>${workData.taskName}</td>
			<td>${workData.userName}</td>
			<td>${workData.actulHours}</td>
			<td>${workData.workDescription}</td>
		</tr>
		</c:forEach>
		</tbody>		
	
	</table>
	
	</c:if>
	
	
	
	
</div>

</main>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %> 
</body>
</html>