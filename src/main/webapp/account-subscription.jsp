<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <script src="https://use.fontawesome.com/4a84054dc4.js"></script>
    <link rel="stylesheet" href="static/style/app.css">

    <title>FinalProject</title>
</head>

<body>
<header>
    <div class="container">
        <nav class="navbar navbar-light">
            <div class="container-fluid">
                <!-- -----------------------------------logo----------------------------------------------- -->
                <a class="navbar-brand header-brand-name " href="mainPage.jsp"><fmt:message key="app.logo"/></a>
                <c:if test="${sessionScope.loggedUser != null}">
                    <a type="button" class="btn" href="account-info.jsp"><fmt:message key="app.user"/>: ${sessionScope.loggedUser.name}</a>
                </c:if>
            </div>
        </nav>
    </div>
</header>
<div class="container">
    <!-- -----------------------------------header-category----------------------------------------------- -->
    <nav class="nav justify-content-center category-block">
        <a class="nav-link category-block-link" href="controller?command=accountInfo"><fmt:message key="user.personalPage"/></a>
        <a class="nav-link category-block-link" href="controller?command=getUsersSubscriptions"><fmt:message key="user.subscriptions"/></a>
    </nav>
    <!-- -----------------------------------subscribtion info----------------------------------------------- -->

    <div id="subscription" class="container">
        <c:choose>
            <c:when test="${subscriptions.isEmpty()}">
                <div class="alert alert-warning" role="alert">
                    <fmt:message key="userSub.alert"/>
                </div>
                <div class="text-center">
                    <a type="button" class="btn btn-primary"
                       href="controller?command=showAllMagazine&currentPage=1"><fmt:message key="userSub.goToMagazinePg"/></a>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table table-hover align-middle text-truncate">
                    <thead>
                    <tr>
                        <th scope="col"><fmt:message key="userSub.tableName"/></th>
                        <th scope="col"><fmt:message key="userSub.tableDateStart"/></th>
                        <th scope="col"><fmt:message key="userSub.tableDateEnd"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${subscriptions}" var="subscription">
                        <tr>
                            <td>${subscription.magazineName}</td>
                            <td>${subscription.startDate}</td>
                            <td>${subscription.endDate}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
</script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js"
        integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/" crossorigin="anonymous">
</script>
</body>

</html>