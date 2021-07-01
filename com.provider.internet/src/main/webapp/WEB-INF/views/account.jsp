<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${requestScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/snippets/header.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/snippets/navbar.jsp"/>

<sec:authorize access="isAuthenticated()">

    <div class="jumbotron">
        <div class="container">
            <h1><fmt:message key="user.balance"/><c:out value="${user.balance}"/><fmt:message key="currency"/></h1>
        </div>
    </div>
    <c:forEach var="includedPackage" items="${requestScope.includedPackages}">
        <div class="jumbotron">
            <div class="container">
                <h1>${includedPackage.service.serviceName}</h1>
            </div>
        </div>
    <table class="table">
        <thead>
        <tr>
            <th class="col-1" scope="col"><fmt:message key="tariff.name"/></th>
            <th class="col-2" scope="col"><fmt:message key="tariff.description"/></th>
            <th class="col-1" scope="col"><fmt:message key="tariff.cost"/></th>
            <th class="col-2" scope="col"><fmt:message key="subscription.date"/></th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td class="col-1" ><c:out value="${includedPackage.tariff.tariffName}"/></td>
                <td class="col-2" >
                    <ul>
                    <c:forEach var="option" items="${includedPackage.tariff.includedOptions}">
                        <li><c:out value="${option.definition}"/></li>
                    </c:forEach>
                    </ul>
                </td>
                <td class="col-1" ><c:out value="${includedPackage.tariff.cost}"/></td>
                <td class="col-2" ><c:out value="${includedPackage.subscriptionDate}"/></td>
                <td class="col-1" >
                    <form action="${pageContext.request.contextPath}/site/user/account" method="post" >
                <input type="hidden" name="command" value="delete"/>
                <input type="hidden" name="includedPackageId"
                       value="${includedPackage.id}"/>
                <button type="submit" class="btn btn-info"><fmt:message
                        key="disable"/></button>
                     </form>
                </td>
            </tr>
        </tbody>
    </table>
    </c:forEach>
</sec:authorize>

<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>
