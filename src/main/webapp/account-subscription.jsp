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
                <c:if test="${sessionScope.loggedUser != null}">
                    <a type="button" class="btn" href="account-info.jsp">User: ${sessionScope.loggedUser.name}</a>
                </c:if>
            </div>
        </nav>
    </div>
</header>
<div class="container">
    <!-- -----------------------------------header-category----------------------------------------------- -->
    <nav class="nav justify-content-center category-block">
        <a class="nav-link category-block-link" href="controller?command=accountInfo">Personal information and
            wallet</a>
        <a class="nav-link category-block-link" href="controller?command=getUsersSubscriptions">Subscriptions</a>
    </nav>
    <!-- -----------------------------------subscribtion info----------------------------------------------- -->

    <div id="subscription" class="container">
        <table class="table table-hover align-middle text-truncate">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Date_Start</th>
                <th scope="col">Date_End</th>
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