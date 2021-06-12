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
    <!-- -----------------------------------account-info-option----------------------------------------------- -->
    <div id="accountInfo" class="container col-12 col-md-8 account-info-area"
         style="margin-left: auto; margin-right: auto; margin-top: 5rem;">

        <ul class="list-group list-group-flush">
            <li class="list-group-item"><fmt:message key="userInfo.name"/>:<span class="pull-right">${sessionScope.loggedUser.name}</span></li>
            <li class="list-group-item"><fmt:message key="userInfo.login"/>:<span class="pull-right">${sessionScope.loggedUser.login}</span></li>
            <li class="list-group-item"><fmt:message key="userInfo.currentBalance"/>:<span
                    class="pull-right">${sessionScope.loggedUser.wallet}</span></li>
            <li class="list-group-item"><fmt:message key="userInfo.status"/>:<span class="pull-right">
            <c:choose>
                <c:when test="${sessionScope.loggedUser.status == true}">
                    <fmt:message key="userInfo.userStatusActive"/>
                </c:when>
                <c:otherwise>
                    <fmt:message key="userInfo.userStatusBlocked"/>
                </c:otherwise>
            </c:choose>
            </span></li>
            <li class="list-group-item">
                <form action="controller" class="row">
                    <input type="hidden" name="command" value="topUpBalance">
                    <div class="mb-3 row">
                        <label for="inputAmount" class="col-auto col-form-label"><fmt:message key="userInfo.topUpBalance"/>:</label>
                        <div class="col-4">
                            <input type="number" name="amount" class="form-control" id="inputAmount">
                        </div>
                        <div class="col-auto">
                            <input type="submit" class="btn btn-primary mb-3 btn-custom-orange" value="<fmt:message key="userInfo.submitTopUp"/>">
                        </div>
                    </div>
                </form>
            </li>
        </ul>
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