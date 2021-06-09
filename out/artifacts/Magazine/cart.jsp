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
                <a class="navbar-brand header-brand-name " href="mainPage.jsp">Subscribtions</a>
                <c:if test="${sessionScope.loggedUser != null}">
                    <a type="button" class="btn" href="account-info.jsp">User: ${sessionScope.loggedUser.name}</a>
                </c:if>
            </div>
        </nav>
    </div>
</header>

<div id="cart" class="container">


    <c:if test="${sessionScope.loggedUser == null}">
        <div class="alert alert-warning" role="alert">
            To make order, please sign in!
        </div>
    </c:if>

    <table class="table table-hover align-middle">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Price</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>?</td>
            <td>?</td>
            <td>
                <a id="cartItemRemove" class="btn btn-danger" href="#" role="button" data-id-product="278009">Remove</a>
            </td>
        </tr>
        </tbody>
    </table>

    <c:choose>
        <c:when test="${sessionScope.loggedUser != null && sessionScope.loggedUser.roleId != 1}">
            <div class="row">
                <div class="text-center">
                    <a class="btn btn-primary col-12 col-md-2">Subscribe</a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row">
                <div class="text-center">
                    <a id="buySignIn" class="btn btn-primary col-12 col-md-2" data-toggle="modal"
                       data-target="#signInBtnModal"> Sign In</a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<!-- -----------------------------------register popup----------------------------------------------- -->
<div class="modal fade" id="signInBtnModal" aria-hidden="true" aria-labelledby="exampleModalToggleLabel"
     tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalToggleLabel">Log In</h5>
                <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"
                        aria-hidden="true">
                </button>
            </div>
            <div class="modal-body">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="login">
                    <div class="mb-3">
                        <label for="inputLogin" class="form-label">Login</label>
                        <input type="text" name="login" class="form-control" id="inputLogin">
                    </div>
                    <div class="mb-3">
                        <label for="inputPassword" class="form-label">Password</label>
                        <input type="password" name="password" class="form-control" id="inputPassword">
                    </div>
                    <div class="mb-3">
                        <input type="submit" class="btn btn-success" value="Submit">
                        <a href="#" class="card-link" data-target="#logInBtnModal" data-toggle="modal"
                           data-dismiss="modal">or create account</a>
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
                <h5 class="modal-title" id="exampleModalToggleLabel2">Register</h5>
                <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"
                        aria-hidden="true">
                </button>
            </div>
            <div class="modal-body">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="register">
                    <div class="mb-3">
                        <label for="InputName" class="form-label">Name and Surname</label>
                        <input type="text" name="name" class="form-control" id="InputName">
                    </div>
                    <div class="mb-3">
                        <label for="inputLoginReg" class="form-label">Login</label>
                        <input type="text" name="login" class="form-control" id="inputLoginReg">
                    </div>
                    <div class="mb-3">
                        <label for="inputPasswordReg" class="form-label">Password</label>
                        <input type="password" name="password" class="form-control" id="inputPasswordReg">
                    </div>
                    <div class="mb-3">
                        <input type="submit" class="btn btn-success" value="Submit">
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