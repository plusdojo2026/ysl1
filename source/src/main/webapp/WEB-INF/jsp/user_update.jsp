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
<!-- 編集画面 -->
 <h2>編集</h2>
 <!--パスワードresetエリア展開用ボタン-->
<input type="button" name="resetAreaButton" value="パスワードリセット">
 <form action="${pageContext.request.contextPath}/Controller" method="post">
     <!-- フォーム開始: Controller に POST 送信 -->
    <!-- ページ識別子: サーバ側で画面判定に使用 -->
 <input type="hidden" name="pageId"  value="U005">
 <!--userid-->
<input type="hidden" name="userId" value="${user.userId}">
<!--userName-->
 <label for="userName">氏名</label>
 <input type="text" name="userName" id="usreName" class="userName" placeholder="氏名を入力" autocomplete="userName" required>

     <!-- 氏名のエラーメッセージ表示（存在する場合のみ表示） -->
    <c:if test="${not empty errorMsgName}">
        <div class="error"><c:out value="${errorMsgName}"/></div>
    </c:if>
  <!-- メールアドレス確認欄: ユーザーのメールアドレスを入力 -->
    <label for="mailAdress">メールアドレス</label>
    <input type="text" name="mailAdress" id="mailAdress" placeholder="メールアドレスを入力" autocomplete="current-mailAdress" >
      <!-- パスワードのエラーメッセージ表示（存在する場合のみ表示） -->
    <c:if test="${not empty errorMsgMail}">
        <div class="error"><c:out value="${errorMsgMail}"/></div>
    </c:if>

     <!-- パスワード入力欄: ユーザーのパスワードを入力 -->
    <label for="loginPw">新しいパスワード</label>
    <input type="password" name="loginPw" id="loginPw" placeholder="新しいパスワードを入力" autocomplete="current-password" required>

     <!-- パスワード確認欄: ユーザーのパスワードを入力 -->
    <label for="loginPw">パスワードを確認</label>
    <input type="password" name="loginPwCheck" id="loginPwCheck" placeholder="新しいパスワードを入力" autocomplete="current-password-check" required>
      <!-- パスワードのエラーメッセージ表示（存在する場合のみ表示） -->
    <c:if test="${not empty errorMsgPw}">
        <div class="error"><c:out value="${errorMsgPw}"/></div>
    </c:if>

    <!--権限-->
    <label for="authority">権限</label>
    <input type="radio" name="authority" id="authority" value="0">管理者
       <input type="radio" name="authority" id="authority" value="1">一般


    <!--状態-->
    <label for="userStatus">状態</label>
    <input type="radio" name="userStatus" id="userStatus" value="0">無効
       <input type="radio" name="userStatus" id="userStatus" value="1">有効

       <div id="submitArea">
    <!-- 送信ボタン: フォームを送信 -->

    <input type="submit" name="buttonId" value="保存" data-confirm="この内容でよろしいでしょうか？" >
     <button type="button" class="js-back-page">戻る</button>
    </div>
</form>
</main>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

</body>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
</html>
