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
<nav class="sidebar-nav">
<ul>
	<li><input type="button" value="ダッシュボード" name="buttonId">
	<a href="${pageContext.request.contextPath}/main?pageId=D001"><span>ダッシュボード</span></a></li>
	
	
	
	<li><a href="cases.jsp"><span>案件一覧</span></a></li>
	<li><a href="tasks.jsp"><span>タスク管理</span></a></li>
	<li><a href="monthly_sum.jsp"><span>月次集計</span></a></li>
	<li><a href="user.jsp"><span>メンバー管理</span></a></li>
</ul>
</nav>
</aside>

