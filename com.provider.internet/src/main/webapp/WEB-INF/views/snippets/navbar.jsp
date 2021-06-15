<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="customTag" uri="/WEB-INF/customTags/selectedPageTag" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value='${sessionScope.locale}'/>
<fmt:setBundle basename="i18n.lang"/>

<c:set var="servicePage" scope="page" value="/WEB-INF/views/service.jsp"/>
<c:set var="managerPage" scope="page" value="/WEB-INF/views/users.jsp"/>
<c:set var="includedOptionPage" scope="page" value="/WEB-INF/views/includedOption.jsp"/>
<c:set var="createOptionPage" scope="page" value="/WEB-INF/views/createOption.jsp"/>
<c:set var="createTariffPage" scope="page" value="/WEB-INF/views/createTariff.jsp"/>
<c:set var="createUserPage" scope="page" value="/WEB-INF/views/createUser.jsp"/>
<c:set var="createServicePage" scope="page" value="/WEB-INF/views/createService.jsp"/>
<c:set var="usersPage" scope="page" value="/WEB-INF/views/users.jsp"/>

<c:set var="currPage" scope="page">
    <customTag:currPage/>
</c:set>

<nav class="navbar navbar-expand-md navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <sec:authorize access="hasRole('ADMIN')">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/site/manager/users">Internet Provider</a>
        </sec:authorize>
        <sec:authorize access="hasRole('USER')">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/site/user/account">Internet Provider</a>
        </sec:authorize>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav me-auto mb-2 mb-md-0">

                <sec:authorize access="hasRole('ADMIN')">
                    <li class="nav-item">
                    <c:choose>
                        <c:when test="${not managerPage.equals(currPage)}">
                            <a class="btn btn-success" href="${pageContext.request.contextPath}/site/manager/users" role="button"><fmt:message key="manager.users"/></a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/manager/users" role="button"><fmt:message key="manager.users"/></a>
                        </c:otherwise>
                    </c:choose>
                    </li>
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${not createUserPage.equals(currPage)}">
                                <a class="btn btn-success" href="${pageContext.request.contextPath}/site/manager/create_user" role="button"><fmt:message key="create.user"/></a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/manager/create_user" role="button"><fmt:message key="create.user"/></a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </sec:authorize>
                <li class="nav-item">
                    <c:choose>
                        <c:when test="${not servicePage.equals(currPage)}">
                            <a class="btn btn-success" href="${pageContext.request.contextPath}/site/service" role="button"><fmt:message key="service"/></a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/service" role="button"><fmt:message key="service"/></a>
                        </c:otherwise>
                    </c:choose>
                </li>
                <sec:authorize access="hasRole('ADMIN')">
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${not createServicePage.equals(currPage)}">
                                <a class="btn btn-success" href="${pageContext.request.contextPath}/site/manager/create_service" role="button"><fmt:message key="manager.create.service"/></a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/manager/create_service" role="button"><fmt:message key="manager.create.service"/></a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${not createTariffPage.equals(currPage)}">
                                <a class="btn btn-success" href="${pageContext.request.contextPath}/site/manager/create_tariff" role="button"><fmt:message key="create.tariff"/></a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/manager/create_tariff" role="button"><fmt:message key="create.tariff"/></a>
                            </c:otherwise>
                        </c:choose>
                </li>
                <li class="nav-item">
                    <c:choose>
                        <c:when test="${not includedOptionPage.equals(currPage)}">
                            <a class="btn btn-success" href="${pageContext.request.contextPath}/site/manager/included_option" role="button"><fmt:message key="option"/></a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/manager/included_option" role="button"><fmt:message key="option"/></a>
                        </c:otherwise>
                    </c:choose>
                </li>
                <li class="nav-item">
                    <c:choose>
                        <c:when test="${not createOptionPage.equals(currPage)}">
                            <a class="btn btn-success" href="${pageContext.request.contextPath}/site/manager/create_option" role="button"><fmt:message key="create.option"/></a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/site/manager/create_option" role="button"><fmt:message key="create.option"/></a>
                        </c:otherwise>
                    </c:choose>
                </li>
                </sec:authorize>
            </ul>
        </div>
        <div class="collapse navbar-collapse" id="navbarCollapse2">
        <ul class="navbar-nav ms-auto mb-2 mb-md-0">
            <sec:authorize access="isAuthenticated()">
                <sec:authorize access="hasRole('ADMIN')">
                    <li>
                        <a class="nav-link" href="#"><fmt:message key="welcome"/> ${pageContext.request.userPrincipal.name}</a>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('USER')">
                    <li>
                        <a class="nav-link" href="#"><fmt:message key="welcome"/> </a>
                    </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="dropdown05" data-bs-toggle="dropdown" aria-expanded="false">${pageContext.request.userPrincipal.name}</a>
                   <ul class="dropdown-menu" aria-labelledby="dropdown05">
                        <li><a class="btn btn-link" href="${pageContext.request.contextPath}/site/user/replenish" role="button"><fmt:message key="replenish"/></a></li>
                    </ul>
                    </sec:authorize>

            </sec:authorize>

        </ul>
        <ul class="navbar-nav ms-auto mb-2 mb-md-0">
            <sec:authorize access="!isAuthenticated()">
                <li>
                <a class="btn btn-success" href="${pageContext.request.contextPath}/site/login" role="button"><fmt:message key="login"/></a>
                </li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <li>
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/logout" role="button"><fmt:message key="logout"/></a>
                </li>
            </sec:authorize>
        </ul>
        <ul class = "navbar-nav ms-auto mb-2 mb-md-1">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdown03" data-bs-toggle="dropdown" aria-expanded="false">${sessionScope.locale.getLanguage().toUpperCase()}</a>
                <ul class="dropdown-menu" aria-labelledby="dropdown03">
                    <c:forEach items="${applicationScope.supportedLocales}" var="lang">
                        <li><a class="dropdown-item" href="?lang=${lang}">${lang.toUpperCase()}</a></li>
                    </c:forEach>
                </ul>
            </li>
        </ul>
        </div>
    </div>
</nav>

