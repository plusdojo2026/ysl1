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


    <c:if test="${empty sessionScope.user}">
        <div class="header__auth">
                 <button onclick="location.href='${loginUrl}'" class="btn btn-outline">ログイン</button>
        </div>
    </c:if>

    <c:if test="${not empty sessionScope.user}">
        <nav class="header__auth">

            <span class="header__welcome">
            <div></div>
            <!-- 
            <img alt="userIcon" src="${pageContext.request.contextPath}/images/user.png">
                ようこそ${sessionScope.user.userName}さん
     
 -->
           
              <button onclick="location.href='${resetPWUrl}'" class="btn btn-outline">パスワード変更</button>
          

          
                <input type="hidden" name="page" value="logout">
                <button onclick="location.href='${logoutUrl}'" class="btn btn-outline" id="logout-btn">ログアウト</button>
        </nav>
    </c:if>

</header>