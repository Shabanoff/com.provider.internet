<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${requestScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<head>
    <jsp:include page="/WEB-INF/views/snippets/header.jsp"/>
    <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet">
</head>
<body class="text-center">
<sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/"); %>
</sec:authorize>
<main class="form-signin">
    <form class="form-signin" method="POST" action="${pageContext.request.contextPath}/site/login">
        <div class="form-floating">
            <input  class="form-control" name="username" id="username" placeholder="<fmt:message key="enter.login"/>"
                   value="<c:out value="${requestScope.user.getLogin()}" />" required autofocus>
            <label for="username"><fmt:message key="enter.login"/></label>
        </div>

        <div class="form-floating">
            <input type="password" class="form-control" name="password" id="password" placeholder="<fmt:message key="enter.password"/>" required>
            <label for="password"><fmt:message key="enter.password"/></label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="login" /></button>
    </form>
</main>

<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>