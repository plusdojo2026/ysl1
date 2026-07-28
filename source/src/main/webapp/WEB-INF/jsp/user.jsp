<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp" %>
<%@ include file="/WEB-INF/jsp/common/side_menu.jsp" %>
<main>

    <div>
        <form action="Controller" method="get">
         <input type="hidden" name="pageId" value="U003">
        <input type="submit" id="regist" value="+新規登録" name="buttonId">
        </form>
    </div>
<table class="table" id="userTable" border="1">
		<thead>
			<tr>
				<th>ユーザーID</th>
				<th>ログインID</th>
				<th>ユーザー名</th>
				<th>メールアドレス</th>
				<th>ユーザー権限</th>
				<th>有効状態</th>
				<th>作成日時</th>
				<th>更新日時</th>
                <th>無効化</th>
                <th>詳細</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="u" items="${userList}" >
                <form action="Controller" method="get">
                    <input type="hidden" name="pageId" value="U003">
                    <input type="hidden" name="userId" value="${u.userId}">
				<tr>
					<td>${u.userId}</td>
					<td>${u.loginId}</td>
					<td>${u.userName}</td>
					<td>${u.mailAddress}</td>
					<td><c:if test="${u.authority}">ユーザー</c:if><c:if test="${!u.authority}">管理者</c:if></td>
					<td><c:if test="${u.active}">有効</c:if><c:if test="${!u.active}">無効</c:if></td>
					<td>${u.createdAt}</td>
					<td>${u.updateAt}</td>
                    <td><button name="buttonId" value="無効" type="submit">無効化</button></td>
                    <td><button name="buttonId" type="submit" value="編集"> 編集</button></td>
				</tr>
                </form>
			</c:forEach>
		</tbody>
</table>


</main>
<%@ include file="/WEB-INF/jsp/common/footer.jsp" %>

</body>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
</html>
