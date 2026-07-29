<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>新規作成</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css">
</head>

<body>

<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
<main>

    <h2>新規作成</h2>

    <form action="${pageContext.request.contextPath}/Controller" method="post" id="userRegistForm" novalidate>
     <!-- フォーム開始: Controller に POST 送信 -->
    <!-- ページ識別子: サーバ側で画面判定に使用 -->

        <input type="hidden" name="pageId" value="U004">


        <!-- ログインID -->
        <label for="loginId">ログインID</label>

        <input type="text"
               name="loginId"
               id="loginId"
               placeholder="ログインIDを入力"
              autocomplete="username"
               value="<c:out value='${param.loginId}'/>">


        <div class="error" id="errorMsgLoginId">
            <c:out value="${errorMsgLoginId}"/>
        </div>


        <!-- 氏名 -->
        <label for="userName">氏名</label>

        <input type="text"
               name="userName"
               id="userName"
               class="userName"
               placeholder="氏名を入力"
               autocomplete="name"
               value="<c:out value='${param.userName}'/>">

        <div class="error" id="errorMsgName">
            <c:out value="${errorMsgName}"/>
        </div>


        <!-- メールアドレス -->
        <label for="mailAddress">メールアドレス</label>

        <input type="text"
               name="mailAddress"
               id="mailAddress"
               placeholder="メールアドレスを入力"
               autocomplete="email"
               value="<c:out value='${param.mailAddress}'/>">

        <div class="error" id="errorMsgMail">
            <c:out value="${errorMsgMail}"/>
        </div>


        <!-- 初期パスワード -->
        <label for="loginPw">初期パスワード</label>

        <input type="password"
               name="loginPw"
               id="loginPw"
               placeholder="新しいパスワードを入力"
               autocomplete="new-password">

        <div class="error" id="errorMsgLoginPw">
            <c:out value="${errorMsgLoginPw}"/>
        </div>


        <!-- 権限 -->
        <div class="radioArea">

            <span class="title">権限</span>
            <div class="radioGroup">
       		<span class="authorityAdmin">
            <input type="radio"
                   name="authority"
                   id="authorityAdmin"
                   value="0"
                   <c:if test="${param.authority == '0'}">
                       checked
                   </c:if>>
            <label for="authorityAdmin">管理者</label></span>
            <span class="authorityUser">
                <input type="radio"
                   name="authority"
                   id="authorityUser"
                   value="1"
                   <c:if test="${param.authority == '1'}">
                       checked
                   </c:if>> <label for="authorityUser">一般</label>
                   </span>
            </div>
        </div>

        <div class="error" id="errorMsgAuthority">
            <c:out value="${errorMsgAuthority}"/>
        </div>

        <div id="submitArea">

            <input type="submit"
                   name="buttonId"
                   value="登録"
                   data-confirm="この内容でよろしいでしょうか？">

            <button type="button" class="js-back-page">
                戻る
            </button>

        </div>

    </form>

</main>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script src="${pageContext.request.contextPath}/js/user_regist.js"></script>

</body>
</html>
