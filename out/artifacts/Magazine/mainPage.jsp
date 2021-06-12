<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'en'}"/>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
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

<main class="container">
    <div class="row">
        <div class="col-12">
            <img src="./static/img/main_page-01.png" class="img-fluid" alt="IMG Not Found">
            <div class="text-center" style="margin-bottom: 50px; margin-top: 50px">
                <hr>
                <h2 style="margin-top: 20px; margin-bottom: 20px"><fmt:message key="mainPage.info"/></h2>
                <hr>
            </div>
            <p>
                Реалізувати роботу системи Періодичні видання. В системі існує перелік Видань, які згруповані за
                тематикою.
                Читач може оформити Передплату на одне або декілька видань. Для переліку видань необхідно
                реалізувати
                можливість:
            <ul>
                <li>сортування видань за назвою;</li>
                <li>сортування видань за ціною;</li>
                <li>вибірки видань за певною темою;</li>
                <li>пошуку видання за назвою.</li>
            </ul>
            Читач реєструється в системі і має особистий кабінет, в якому відображена інформація про видання, на які
            він підписаний. Незареэстрований користувач не може оформити підписку.
            Читач має персональний Рахунок, який він може поповнити. Кошти з рахунку знімаються під час підписки на
            видання.
            Адміністратор системи володіє правами:
            <ul>
                <li>додавання, видалення і редагування видання;</li>
                <li>блокування, розблокування користувача.</li>
            </ul>
            </p>
        </div>
    </div>
</main>

<!-- -----------------------------------register popup----------------------------------------------- -->
<div class="modal fade" id="signInBtnModal" aria-hidden="true" aria-labelledby="exampleModalToggleLabel"
     tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalToggleLabel"><fmt:message key="mainPage.loginLabel"/></h5>
                <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"
                        aria-hidden="true">
                </button>
            </div>
            <div class="modal-body">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="login">
                    <div class="mb-3">
                        <label for="inputLogin" class="form-label"><fmt:message key="mainPage.loginField"/></label>
                        <input type="text" name="login" class="form-control" id="inputLogin">
                    </div>
                    <div class="mb-3">
                        <label for="inputPassword" class="form-label"><fmt:message
                                key="mainPage.passwordField"/></label>
                        <input type="password" name="password" class="form-control" id="inputPassword">
                    </div>
                    <div class="mb-3">
                        <input type="submit" class="btn btn-success" value="<fmt:message key="mainPage.submit"/>">
                        <a href="#" class="card-link" data-target="#logInBtnModal" data-toggle="modal"
                           data-dismiss="modal"> <fmt:message key="mainPage.createAccount"/></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="logInBtnModal" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2"
     tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalToggleLabel2"><fmt:message key="mainPage.registerLabel"/></h5>
                <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"
                        aria-hidden="true">
                </button>
            </div>
            <div class="modal-body">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="register">
                    <div class="mb-3">
                        <label for="InputName" class="form-label"><fmt:message key="mainPage.nameSurname"/></label>
                        <input type="text" name="name" class="form-control" id="InputName">
                    </div>
                    <div class="mb-3">
                        <label for="inputLoginReg" class="form-label"><fmt:message key="mainPage.loginField"/></label>
                        <input type="text" name="login" class="form-control" id="inputLoginReg">
                    </div>
                    <div class="mb-3">
                        <label for="inputPasswordReg" class="form-label"><fmt:message
                                key="mainPage.passwordField"/></label>
                        <input type="password" name="password" class="form-control" id="inputPasswordReg">
                    </div>
                    <div class="mb-3">
                        <input type="submit" class="btn btn-success" value="<fmt:message key="mainPage.submit"/>">
                    </div>
                </form>
            </div>
        </div>
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