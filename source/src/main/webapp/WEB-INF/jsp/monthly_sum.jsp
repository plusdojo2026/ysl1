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
<form method="POST" action="/ysl1/ControllerServlet">
<input type="hidden" name="pageId" value="M001">
<div class="layout">

	<!-- 上部操作エリア -->
	<div class="top-area">
		<form action="${pageContext.request.contextPath}/Controller" method="post">
	
		<!-- 月選択 -->
		<div class="month-select">
		<input type="month" name="month" value="${selectMonth}">
		<input type="submit" name="buttonId" value="集計">
		<input type="submit" name="buttonId" value="工数一覧"> 
	
		</div>
		</form>
	
		<!-- CSV出力 -->
		<form action="${pageContext.request.contextPath}/Controller" method="post">
		<input type="hidden" name="month" value="${selectedMonth}">
		<input type="submit" name="byttonId" value="CSV出力">
		</form>

	</div>
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
			<c:forEach var="caseData" items="${caseSummaryList}">
			<tr>
				<td>${caseData.caseCode}</td>
				<td>${caseData.caseName}</td>
				<td>${caseData.actualHours}h</td>
				<td>${caseData.casePlannedHours}h</td>
				<td>
				<div class="progress-bar">
				<div class="progress-value" style="width: ${caseData.progressRate}%"></div>
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
			<c:forEach var="memberData" items="${memberSummaryList}">
			<tr>
				<td>${memberData.userName}</td>
				<td>${memberData.actualHours}h</td>
				<td>
				<div class="progress-bar">
				<div class="progress-value" style="width: ${memberData.progressRate}%"></div>
				</div>
				<span>${memberData.progressRate}%</span>
				</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
		<p class="note">
		※全体に占める割合 = 実績工数 / タスクの予算工数 × 100(小数点以下切り捨て)
		</p>
		</section>
	</div>
	
	<!-- 集計条件 -->
	<div class="condition-area">
		<div class="condition-title">集計条件</div>
		
		<div>
		集計対象期間：${startDate}～${endDate}
		</div>
		<div>
		集計日時：${currentDateTime}
		</div>
		
	</div>
	
</div>

</main>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %> 
</body>
</html>