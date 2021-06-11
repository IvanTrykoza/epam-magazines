<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <a class="navbar-brand header-brand-name " href="mainPage.jsp">Subscriptions</a>
            </div>
        </nav>
    </div>
</header>
<div class="container">
    <!-- -----------------------------------header-category----------------------------------------------- -->
    <nav class="nav justify-content-center category-block">
        <a class="nav-link category-block-link" href="controller?command=accountInfo">Personal information and wallet</a>
        <a class="nav-link category-block-link" href="controller?command=getUsersSubscriptions">Subscriptions</a>
    </nav>
    <!-- -----------------------------------account-info-option----------------------------------------------- -->
    <div id="accountInfo" class="container col-12 col-md-8 account-info-area"
         style="margin-left: auto; margin-right: auto; margin-top: 5rem;">

        <ul class="list-group list-group-flush">
            <li class="list-group-item">Name:<span class="pull-right">${sessionScope.loggedUser.name}</span></li>
            <li class="list-group-item">Login:<span class="pull-right">${sessionScope.loggedUser.login}</span></li>
            <li class="list-group-item">Current balance:<span
                    class="pull-right">${sessionScope.loggedUser.wallet}</span></li>
            <li class="list-group-item">Status:<span class="pull-right">
            <c:choose>
                <c:when test="${sessionScope.loggedUser.status == true}">
                    Active
                </c:when>
                <c:otherwise>
                    Blocked
                </c:otherwise>
            </c:choose>
            </span></li>
            <li class="list-group-item">
                <form action="controller" class="row">
                    <input type="hidden" name="command" value="topUpBalance">
                    <div class="mb-3 row">
                        <label for="inputAmount" class="col-auto col-form-label">Top up balance:</label>
                        <div class="col-4">
                            <input type="number" name="amount" class="form-control" id="inputAmount">
                        </div>
                        <div class="col-auto">
                            <input type="submit" class="btn btn-primary mb-3 btn-custom-orange" value="Top Up">
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