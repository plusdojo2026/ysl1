<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ユーザー編集</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css">
</head>
<body>

<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>

<main>
    <h2>編集</h2>

    <form action="${pageContext.request.contextPath}/Controller" method="post">
        <!-- サーバー側で画面を判定するためのページ識別子 -->
        <input type="hidden" name="pageId" value="U005">

        <!-- 更新対象ユーザーのID -->
        <input type="hidden"
               name="userId"
               value="${updateUser.userId}">

        <!-- ログインID -->
        <input type="hidden"
               name="loginId"
               value="${updateUser.loginId}">

        <!-- 氏名 -->
        <div class="formItem">
            <label for="userName">氏名</label>
            <input type="text"
                   name="userName"
                   id="userName"
                   class="userName"
                   value="${updateUser.userName}"
                   placeholder="氏名を入力"
                   autocomplete="name"
                   required>

            <c:if test="${not empty errorMsgName}">
                <div class="error">
                    <c:out value="${errorMsgName}"/>
                </div>
            </c:if>
        </div>

        <!-- メールアドレス -->
        <div class="formItem">
            <label for="mailAddress">メールアドレス</label>
            <input type="email"
                   name="mailAddress"
                   id="mailAddress"
                   value="${updateUser.mailAddress}"
                   placeholder="メールアドレスを入力"
                   autocomplete="email"
                   required>

            <c:if test="${not empty errorMsgMail}">
                <div class="error">
                    <c:out value="${errorMsgMail}"/>
                </div>
            </c:if>
        </div>

        <!-- パスワードリセットエリア展開ボタン -->
        <div class="formItem">
            <button type="button" id="resetAreaButton">
                パスワードリセット
            </button>
        </div>

        <!-- 初期状態では非表示 -->
        <div id="passwordResetArea" data-has-password-error="${not empty errorMsgPw}" hidden>
            <div class="formItem">
                <label for="loginPw">新しいパスワード</label>
                <input type="password"
                       name="loginPw"
                       id="loginPw"
                       placeholder="新しいパスワードを入力"
                       autocomplete="new-password">
            </div>

            <div class="formItem">
                <label for="loginPwCheck">パスワードを確認</label>
                <input type="password"
                       name="loginPwCheck"
                       id="loginPwCheck"
                       placeholder="新しいパスワードをもう一度入力"
                       autocomplete="new-password">
            </div>

            <c:if test="${not empty errorMsgPw}">
                <div class="error">
                    <c:out value="${errorMsgPw}"/>
                </div>
            </c:if>
        </div>

        <!-- 権限：false = 管理者、true = 一般 -->
        <fieldset class="formItem">
            <legend>権限</legend>
			<div class="radioArea">
            <input type="radio"
                   name="authority"
                   id="authorityAdmin"
                   value="0"
                   ${not updateUser.authority ? 'checked' : ''}>
            <label for="authorityAdmin">管理者</label>

            <input type="radio"
                   name="authority"
                   id="authorityGeneral"
                   value="1"
                   ${updateUser.authority ? 'checked' : ''}>
            <label for="authorityGeneral">一般</label>
			</div>
            <c:if test="${not empty errorMsgAuthority}">
                <div class="error">
                    <c:out value="${errorMsgAuthority}"/>
                </div>
            </c:if>
        </fieldset>

        <!-- 状態：false = 無効、true = 有効 -->
        <fieldset class="formItem">
            <legend>状態</legend>
			<div class="radioArea">
            <input type="radio"
                   name="userStatus"
                   id="userStatusDisabled"
                   value="0"
                   ${not updateUser.active ? 'checked' : ''}>
            <label for="userStatusDisabled">無効</label>

            <input type="radio"
                   name="userStatus"
                   id="userStatusEnabled"
                   value="1"
                   ${updateUser.active ? 'checked' : ''}>
            <label for="userStatusEnabled">有効</label>
			</div>
            <c:if test="${not empty errorMsgStatus}">
                <div class="error">
                    <c:out value="${errorMsgStatus}"/>
                </div>
            </c:if>
        </fieldset>

        <div id="submitArea">
            <input type="submit"
                   name="buttonId"
                   value="保存"
                   data-confirm="この内容でよろしいでしょうか？">

            <button type="button" class="js-back-page">戻る</button>
        </div>
    </form>
</main>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script src="${pageContext.request.contextPath}/js/user_update.js"></script>

</body>
</html>
