<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ログイン</title>

    <link rel="stylesheet"
        href="<c:url value='/css/common.css' />">
    <link rel="stylesheet"
        href="<c:url value='/css/login.css' />">
</head>
<body class="login-page">
    <%@ include file="/WEB-INF/jsp/common/header.jsp" %>

    <main class="login-main">
        <section class="login-card" aria-labelledby="loginTitle">
            <div class="login-card__header">
                <p class="login-card__eyebrow">TASK MANAGER</p>
                <h2 id="loginTitle" class="login-card__title">ログイン</h2>
                <p class="login-card__description">
                    ログインIDとパスワードを入力してください。
                </p>
            </div>

            <!-- システムエラー -->
            <c:if test="${not empty errorMsgSystem}">
                <div class="login-message login-message--system"
                    role="alert" aria-live="assertive">
                    <span class="login-message__icon" aria-hidden="true">!</span>
                    <div class="login-message__content">
                        <p class="login-message__title">システムエラー</p>
                        <p class="login-message__text">
                            <c:out value="${errorMsgSystem}" />
                        </p>
                    </div>
                </div>
            </c:if>

            <!-- ログイン認証エラー -->
            <c:if test="${not empty errorMsgLogin}">
                <div class="login-message login-message--authentication"
                    role="alert" aria-live="assertive">
                    <span class="login-message__icon" aria-hidden="true">!</span>
                    <div class="login-message__content">
                        <p class="login-message__title">ログインできませんでした</p>
                        <p class="login-message__text">
                            <c:out value="${errorMsgLogin}" />
                        </p>
                    </div>
                </div>
            </c:if>

            <c:url var="controllerUrl" value="/Controller" />

            <form class="login-form"
                action="${controllerUrl}"
                method="post"
                novalidate>

                <!-- サーバー側の画面識別子 -->
                <input type="hidden" name="pageId" value="U001">

                <!-- ログインID -->
                <div class="login-field">
                    <label class="login-field__label" for="loginId">
                        ログインID
                    </label>

                    <div class="login-field__control">
                        <input
                            class="login-field__input ${not empty errorMsgId ? 'input-error' : ''}"
                            type="text"
                            name="loginId"
                            id="loginId"
                            value="<c:out value='${param.loginId}' />"
                            placeholder="ログインIDを入力"
                            autocomplete="username"
                            maxlength="20"
                            aria-invalid="${not empty errorMsgId}"
                            aria-describedby="${not empty errorMsgId ? 'loginIdError' : ''}"
                            required>
                    </div>

                    <!-- ログインIDの入力エラー -->
                    <c:if test="${not empty errorMsgId}">
                        <div class="login-field-error"
                            id="loginIdError"
                            role="alert">
                            <span class="login-field-error__icon"
                                aria-hidden="true">!</span>
                            <span class="login-field-error__text">
                                <c:out value="${errorMsgId}" />
                            </span>
                        </div>
                    </c:if>
                </div>

                <!-- パスワード -->
                <div class="login-field">
                    <label class="login-field__label" for="loginPw">
                        パスワード
                    </label>

                    <div class="login-field__control">
                        <input
                            class="login-field__input ${not empty errorMsgPw ? 'input-error' : ''}"
                            type="password"
                            name="loginPw"
                            id="loginPw"
                            placeholder="パスワードを入力"
                            autocomplete="current-password"
                            maxlength="20"
                            aria-invalid="${not empty errorMsgPw}"
                            aria-describedby="${not empty errorMsgPw ? 'loginPwError' : ''}"
                            required>
                    </div>

                    <!-- パスワードの入力エラー -->
                    <c:if test="${not empty errorMsgPw}">
                        <div class="login-field-error"
                            id="loginPwError"
                            role="alert">
                            <span class="login-field-error__icon"
                                aria-hidden="true">!</span>
                            <span class="login-field-error__text">
                                <c:out value="${errorMsgPw}" />
                            </span>
                        </div>
                    </c:if>
                </div>

                <div class="login-actions">
                    <input class="login-submit"
                        type="submit"
                        name="buttonId"
                        value="ログイン">
                </div>
            </form>
        </section>
    </main>

    <%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

    <script src="<c:url value='/js/common.js' />"></script>
</body>
</html>
