<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header">

<%-- 各メニューのURLを生成 --%>
<c:url var="dashboardUrl" value="/Controller">
    <c:param name="pageId" value="side" />
    <c:param name="buttonId" value="ダッシュボード" />
 </c:url>   
    
<c:url var="loginUrl" value="/Controller">
    <c:param name="pageId" value="header" />
    <c:param name="buttonId" value="ログイン" />
 </c:url>   
    <c:url var="resetPWUrl" value="/Controller">
    <c:param name="pageId" value="header" />
    <c:param name="buttonId" value="パスワード変更" />
    </c:url>
      <c:url var="logoutUrl" value="/Controller">
    <c:param name="pageId" value="header" />
    <c:param name="buttonId" value="ログアウト" />
</c:url>
    <div class="header__logo">
    <a href="${dashboardUrl}">
        <img alt="Tz"
             src="${pageContext.request.contextPath}/images/tennpurechann.png" >
             </a>
    </div>

    <c:if test="${empty sessionScope.user}">
        <div class="header__auth">
                 <button onclick="location.href='${loginUrl}'" class="btn btn-outline">ログイン</button>
        </div>
    </c:if>

    <c:if test="${not empty sessionScope.user}">
        <nav class="header__auth">

            <span class="header__welcome">
                ようこそ${sessionScope.user.userName}さん
            </span>

           
              <button onclick="location.href='${resetPWUrl}'" class="btn btn-outline">パスワード変更</button>
          

          
                <input type="hidden" name="page" value="logout">
                <button onclick="location.href='${logoutUrl}'" class="btn btn-outline">ログアウト</button>
           

        </nav>
    </c:if>

</header>