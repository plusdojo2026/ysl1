<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value='/css/common.css' />">

<aside class="sidebar">
<!-- サイドバーヘッダー -->
<div class="sidebar-header">
<div class="logo">
<img src="img_kuma">
</div>
<span class="title">Task Maneger</span>
</div>

<!-- ナビゲーション -->
<input type="hidden" name="pageId" value="side">
<nav class="sidebar-nav">
<ul>
	<li><a href="${pageContext.request.contextPath}/main?pageId=D001&buttonId=ダッシュボード"><span>ダッシュボード</span></a></li>
	<li><a href="${pageContext.request.contextPath}/main?pageId=C001&buttonId=案件一覧"><span>案件一覧</span></a></li>
	<li><a href="${pageContext.request.contextPath}/main?pageId=T001&buttonId=タスク管理"><span>タスク管理</span></a></li>
	<li><a href="${pageContext.request.contextPath}/main?pageId=M001&buttonId=月次集計"><span>月次集計</span></a></li>
	<li><a href="${pageContext.request.contextPath}/main?pageId=U003&buttonId=メンバー管理"><span>メンバー管理</span></a></li>
</ul>
</nav>
</aside>

