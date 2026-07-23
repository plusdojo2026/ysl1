<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>パスワードリセット</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
<main>
<!-- パスワードリセット画面 -->
<h2>パスワードリセット</h2>
<div id="requestMsg" class="request-message"></div>
<form action="${pageContext.request.contextPath}/Controller" method="post">

    <!-- フォーム開始: Controller に POST 送信 -->
    <!-- ページ識別子: サーバ側で画面判定に使用 -->
    <input type="hidden" name="pageId" value="U002">
<!-- エラーメッセージ表示（存在する場合のみ表示） -->
    <!-- 現在のパスワード入力欄: ユーザーの現在のパスワードを入力 -->
    <label for="passwordNow">現在のパスワード</label>
    <input type="password" name="passwordNow" id="passwordNow" placeholder="現在のパスワードを入力" autocomplete="current-password" required>

    <!-- 現在のパスワードのエラーメッセージ表示（存在する場合のみ表示） -->
    <c:if test="${not empty errorMsgId}">
        <div class="error"><c:out value="${errorMsgId}"/></div>
    </c:if>

    <!-- パスワード入力欄: ユーザーのパスワードを入力 -->
    <label for="loginPw">新しいパスワード</label>
    <input type="password" name="loginPw" id="loginPw" placeholder="新しいパスワードを入力" autocomplete="current-password" required>

    <!-- パスワードのエラーメッセージ表示（存在する場合のみ表示） -->
    <c:if test="${not empty errorMsgPw}">
        <div class="error"><c:out value="${errorMsgPw}"/></div>
    </c:if>

    <label for="loginPwConfirm">新しいパスワードを確認</label>
    <input type="password" name="loginPwConfirm" id="loginPwConfirm" placeholder="新しいパスワードを入力" autocomplete="current-password" required>
    <!-- パスワードのエラーメッセージ表示（存在する場合のみ表示） -->
    <c:if test="${not empty errorMsgPwConfirm}">
        <div class="error"><c:out value="${errorMsgPwConfirm}"/></div>
    </c:if>
    <!-- 送信ボタン: フォームを送信 -->
    <input type="submit" name="buttonId" value="パスワードをリセット" data-confirm="この内容でパスワードをリセットしますか？">
    <input type="button" class="js-form-reset" data-confirm="フォームをリセットしますか？" value="リセット" name="buttonId">
    <button type="button" class="js-back-page" name="buttonId" value="戻る">戻る</button>
</form>
</main>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

</body>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
</html>
