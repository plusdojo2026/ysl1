<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>パスワードリセット</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/resetPassword.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
<main>
<!-- パスワードリセット画面 -->
<h2>パスワードリセット</h2>
<!-- パスワード表示 -->
<div class="show-password-wrapper">
    <input type="checkbox" id="show-password">
    <label for="show-password"
        class="show-password-label">
        パスワードを表示する
    </label>
</div>
<div id="requestMsg" class="request-message"></div>
<form action="${pageContext.request.contextPath}/Controller" method="post">

    <!-- フォーム開始: Controller に POST 送信 -->
    <!-- ページ識別子: サーバ側で画面判定に使用 -->
    <input type="hidden" name="pageId" value="U002">
<!-- エラーメッセージ表示（存在する場合のみ表示） -->
 <!-- 現在のパスワード -->
<label for="passwordNow">
    現在のパスワード
</label>

<input
    type="password"
    name="passwordNow"
    id="passwordNow"
    placeholder="現在のパスワードを入力"
    autocomplete="current-password"
    required>



<!-- 現在のパスワードエラー -->
<div class="form-error${not empty errorMsgPasswordNow ? ' is-visible' : ''}"
    id="passwordNowError"
    role="alert">

    <span class="form-error__icon">!</span>

    <span class="form-error__text">
        <c:out value="${errorMsgPasswordNow}" />
    </span>

</div>

<!-- 新しいパスワード -->
<label for="loginPw">
    新しいパスワード
</label>

<input
    type="password"
    name="loginPw"
    id="loginPw"
    placeholder="新しいパスワードを入力"
    autocomplete="new-password"
    required>

<!-- 新しいパスワードエラー -->
<div class="form-error${not empty errorMsgPw ? ' is-visible' : ''}"
    id="loginPwError"
    role="alert">

    <span class="form-error__icon">!</span>

    <span class="form-error__text">
        <c:out value="${errorMsgPw}" />
    </span>

</div>

<!-- 確認用パスワード -->
<label for="loginPwConfirm">
    新しいパスワード（確認）
</label>

<input
    type="password"
    name="loginPwConfirm"
    id="loginPwConfirm"
    placeholder="新しいパスワードを再入力"
    autocomplete="new-password"
    required>

<!-- 確認用パスワードエラー -->
<div class="form-error${not empty errorMsgPwConfirm ? ' is-visible' : ''}"
    id="loginPwConfirmError"
    role="alert">

    <span class="form-error__icon">!</span>

    <span class="form-error__text">
        <c:out value="${errorMsgPwConfirm}" />
    </span>

</div>


    <!-- 送信ボタン: フォームを送信 -->
  <input
    type="submit"
    name="buttonId"
    value="保存">
    <input type="button" class="js-form-reset" data-confirm="フォームをリセットしますか？" value="リセット" name="buttonId">
    <button type="button" class="js-back-page" name="buttonId" value="戻る">戻る</button>
</form>
</main>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

</body>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script src="${pageContext.request.contextPath}/js/resetPassword.js"></script>
</html>
