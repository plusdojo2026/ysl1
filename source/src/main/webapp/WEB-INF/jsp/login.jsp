<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>

<input type="hidden" name="pageId" value="U001">
<form action="Controller" method="post">
    <label for="loginId">ログインID</label>
    <input type="text" name="loginId" id="loginId" placeholder="ログインIDを入力">
    <input type="hidden" name="errorMsgId" value="${errorMsgId}">
    <label for="loginPw">パスワード</label>
    <input type="password" name="loginPw" id="loginPw" placeholder="パスワードを入力">
    <input type="hidden" name="errorMsgPw" value="${errorMsgPw}">
    <input type="submit" name="buttonId" value="ログイン">
</form>

<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

</body>
</html>
