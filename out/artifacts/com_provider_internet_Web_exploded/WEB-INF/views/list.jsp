<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
  <jsp:include page="/WEB-INF/views/snippets/header.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/snippets/navbar.jsp"/>

<c:if test="${not empty sessionScope.user }">

  <table class="table">
    <thead>
    <tr><h1><fmt:message key="manager.users"/></h1></tr>
    <tr class="d-flex">
      <th class="col-5" scope="col"><fmt:message key="user.login"/></th>
      <th class="col-3" scope="col"><fmt:message key="user.balance"/></th>
      <th class="col-1" scope="col"><fmt:message key="user.change.status"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${page.content}">
      <c:if test="${user.user}">
        <tr class="d-flex">
          <td class="col-5"><c:out value="${user.login}"/></td>
          <td class="col-3"><c:out value="${user.balance}"/><fmt:message key="currency"/></td>
          <td class="col-1">
            <form action="${pageContext.request.contextPath}/site/manager/users" method="post" >
              <input type="hidden" name="userId"
                     value="${user.id}"/>
              <c:if test="${user.active}">
                <button type="submit" class="btn btn-info"><fmt:message
                        key="user.block"/></button>
              </c:if>
              <c:if test="${user.blocked}">
                <button type="submit" class="btn btn-info"><fmt:message
                        key="user.unblock"/></button>
              </c:if>
            </form>
          </td>
        </tr>
      </c:if>
    </c:forEach>
    </tbody>
  </table>

</c:if>


<%--For displaying Previous link except for the 1st page --%>
<div class="pagination-div">
    <span th:if="${page.hasPrevious()}">
        <a th:href="@{/list(page=${page.number-1},size=${page.size})}">Previous</a>
    </span>
  <th:block th:each="i: ${numbers.sequence(0, page.totalPages - 1)}">
    <span th:if="${page.number == i}" class="selected">[[${i}+1]]</span>
    <span th:unless="${page.number == i}">
             <a th:href="@{/list(page=${i},size=${page.size})}">[[${i}+1]]</a>
        </span>
  </th:block>
  <span th:if="${page.hasNext()}">
        <a th:href="@{/list(page=${page.number+1},size=${page.size})}">Next</a>
    </span>
</div>

<jsp:include page="/WEB-INF/views/snippets/footer.jsp"/>
</body>
</html>