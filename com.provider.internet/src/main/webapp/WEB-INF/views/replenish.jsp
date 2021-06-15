<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/snippets/header.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/snippets/navbar.jsp"/>

<main class="form-signin">
    <c:if test="${not empty requestScope.errors}">
        <div class="alert alert-danger">
            <c:forEach items="${requestScope.errors}" var="error">
                <strong><fmt:message key="error"/></strong> <fmt:message key="${error}"/><br>
            </c:forEach>
        </div>
    </c:if>
        <sec:authorize access="isAuthenticated()">
            <h1 class="title"><fmt:message key="replenish"/></h1>
            <form class="form-signin" method="post">
                <div class="form-floating">
                    <input type="increase" class="form-control" name="cost" id="cost" placeholder="<fmt:message key="enter.amount"/>">
                    <label for="cost"><fmt:message key="amount"/></label>
                </div>
                <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="replenish" /></button>
            </form>
        </sec:authorize>
</main>
<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
