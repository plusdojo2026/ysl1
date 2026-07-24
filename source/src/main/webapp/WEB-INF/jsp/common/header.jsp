
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header">

    <div class="header__logo">
        <img alt="Tz"
             src="${pageContext.request.contextPath}/images/tennpurechann.png">
    </div>

    <c:if test="${empty sessionScope.user}">
        <nav class="header__auth">
            <form action="${pageContext.request.contextPath}/Controller" method="get">
                <button class="btn btn-outline" type="submit">ログイン</button>
            </form>
        </nav>
    </c:if>

    <c:if test="${not empty sessionScope.user}">
        <nav class="header__auth">

            <span class="header__welcome">
                ようこそ${sessionScope.user.userName}さん
            </span>

            <form action="${pageContext.request.contextPath}/SigninServlet" method="get">
                <button class="btn btn-outline" type="submit">
                    サインイン（新規作成）
                </button>
            </form>

            <form action="${pageContext.request.contextPath}/Forward" method="post">
                <input type="hidden" name="page" value="logout">
                <button class="btn btn-red" type="submit">ログアウト</button>
            </form>

        </nav>
    </c:if>

</header>