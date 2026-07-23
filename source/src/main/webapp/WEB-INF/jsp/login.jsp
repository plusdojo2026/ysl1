<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
<main>
<!-- ログイン画面 -->
 <h2>ログイン</h2>
<form action="${pageContext.request.contextPath}/Controller" method="post">

    <!-- フォーム開始: Controller に POST 送信 -->
    <!-- ページ識別子: サーバ側で画面判定に使用 -->
    <input type="hidden" name="pageId" value="U001">
    <!-- ログインID入力欄: ユーザーのログインIDを入力 -->
    <label for="loginId">ログインID</label>
    <input type="text" name="loginId" id="loginId" placeholder="ログインIDを入力" autocomplete="username" required>

    <!-- ログインIDのエラーメッセージ表示（存在する場合のみ表示） -->
    <c:if test="${not empty errorMsgId}">
        <div class="error"><c:out value="${errorMsgId}"/></div>
    </c:if>

    <!-- パスワード入力欄: ユーザーのパスワードを入力 -->
    <label for="loginPw">パスワード</label>
    <input type="password" name="loginPw" id="loginPw" placeholder="パスワードを入力" autocomplete="current-password" required>

    <!-- パスワードのエラーメッセージ表示（存在する場合のみ表示） -->
    <c:if test="${not empty errorMsgPw}">
        <div class="error"><c:out value="${errorMsgPw}"/></div>
    </c:if>
    <!-- 送信ボタン: フォームを送信 -->
    <input type="submit" name="buttonId" value="ログイン">
</form>
</main>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

</body>
</html>
