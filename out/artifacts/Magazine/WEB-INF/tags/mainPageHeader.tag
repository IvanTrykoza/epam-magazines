<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>







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

<header>
    <div class="container" style="margin-bottom: 20px">
        <nav class="navbar navbar-light">
            <div class="container-fluid">
                <!-- -----------------------------------logo----------------------------------------------- -->
                <a class="navbar-brand header-brand-name " href="mainPage.jsp"><fmt:message key="app.logo"/></a>
                <ul class="nav justify-content-end">

                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${sessionScope.loggedUser != null && sessionScope.loggedUser.roleId == 1}">
                                <a class="btn btn-admin-set" type="button"
                                   href="controller?command=showAllUsers&currentPage=1"> <fmt:message
                                        key="mainPage.adminSetting"/></a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-magazine" type="button"
                                   href="controller?command=showAllMagazine&currentPage=1"> <fmt:message
                                        key="mainPage.magazines"/></a>
                            </c:otherwise>
                        </c:choose>
                    </li>

                    <li class="nav-item">
                        <c:if test="${sessionScope.loggedUser != null && sessionScope.loggedUser.roleId != 1}">
                            <a class="btn btn-ac-info" type="button"
                               href="controller?command=accountInfo">
                                <fmt:message key="mainPage.accountInfo"/></a>
                        </c:if>
                    </li>

                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${sessionScope.loggedUser != null}">
                                <a class="btn btn-logOut" type="button"
                                   href="controller?command=logOut">
                                    <fmt:message key="mainPage.logOut"/></a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-logIn" type="button" data-toggle="modal"
                                   data-target="#signInBtnModal" href="#"> <fmt:message key="app.logIn"/></a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-dark" type="button" href="controller?command=changeLocale&locale=en"
                           style="margin: 3px"> EN </a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-dark" type="button" href="controller?command=changeLocale&locale=ru"
                           style="margin: 3px"> RU </a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</header>