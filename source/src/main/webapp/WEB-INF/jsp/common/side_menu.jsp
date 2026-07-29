<%@ page pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- CSS --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">


<%-- 各メニューのURLを生成 --%>
<c:url var="dashboardUrl" value="/Controller">
    <c:param name="pageId" value="side" />
    <c:param name="buttonId" value="ダッシュボード" />
</c:url>

<c:url var="caseListUrl" value="/Controller">
    <c:param name="pageId" value="side" />
    <c:param name="buttonId" value="案件一覧" />
</c:url>

<c:url var="taskListUrl" value="/Controller">
    <c:param name="pageId" value="side" />
    <c:param name="buttonId" value="タスク管理" />
</c:url>

<c:url var="monthlyUrl" value="/Controller">
    <c:param name="pageId" value="side" />
    <c:param name="buttonId" value="月次集計" />
</c:url>

<c:url var="memberListUrl" value="/Controller">
    <c:param name="pageId" value="side" />
    <c:param name="buttonId" value="メンバー管理" />
</c:url>

<aside class="sidebar">

    <!-- サイドバーヘッダー -->
    <div class="sidebar-header">
        <div class="logo">
         
        </div>
		<img src="${pageContext.request.contextPath}/images/tennpurechann.png">
        <span class="title">Task Manager</span>
    </div>

    <!-- サイドバーナビゲーション -->
    <nav class="sidebar-nav">
        <ul>
            <li>
            <button onclick="location.href='${dashboardUrl}'">
                    <img src="${pageContext.request.contextPath}/images/home-icon.png">
                    <span class="sidebar-label">ダッシュボード</span>
              </button>
            </li>

            <li>
                <button onclick="location.href='${caseListUrl}'">
                	<img src="${pageContext.request.contextPath}/images/cases-icon.png">
                    <span class="sidebar-label">案件一覧</span>
                </a>
            </li>

            <li>
                <button onclick="location.href='${taskListUrl}'">
                	<img src="${pageContext.request.contextPath}/images/tasks-icon.png">
                    <span class="sidebar-label">タスク管理</span>
                </a>
            </li>

            <li>
                <button onclick="location.href='${monthlyUrl}'">
                	<img src="${pageContext.request.contextPath}/images/monthly-icon.png">
                    <span class="sidebar-label">月次集計</span>
                </a>
            </li>
			<c:if test="${sessionScope.user.authority==false}">
            <li>
                 <button onclick="location.href='${memberListUrl}'">
                 	<img src="${pageContext.request.contextPath}/images/member-icon.png">
                    <span class="sidebar-label">メンバー管理</span>
                </a>
            </li>
            </c:if>
        </ul>
    </nav>

<div class="decoration">
<img alt="navi" src="${pageContext.request.contextPath}/images/flowerLeft.png" id="flowerLeft">
</div>
</aside>