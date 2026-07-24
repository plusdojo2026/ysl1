<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- CSSのURL --%>
<c:url var="commonCssUrl" value="/css/common.css" />

<%-- ロゴ画像のURL。実際の保存場所に合わせて変更する --%>
<c:url var="logoUrl" value="/img/img_kuma.png" />

<%-- サイドバーの遷移先URL --%>
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

${commonCssUrl}

<aside class="sidebar">

    <!-- サイドバーヘッダー -->
    <div class="sidebar-header">
        <div class="logo">
            ${logoUrl}
        </div>

        <span class="title">Task Manager</span>
    </div>

    <!-- ナビゲーション -->
    <nav class="sidebar-nav">
        <ul>
            <li>
                ${dashboardUrl}
                    <span>ダッシュボード</span>
                </a>
            </li>

            <li>
                ${caseListUrl}
                    <span>案件一覧</span>
                </a>
            </li>

            <li>
                ${taskListUrl}
                    <span>タスク管理</span>
                </a>
            </li>

            <li>
                ${monthlyUrl}
                    <span>月次集計</span>
                </a>
            </li>

            <li>
                ${memberListUrl}
                    <span>メンバー管理</span>
                </a>
            </li>
        </ul>
    </nav>

</aside>