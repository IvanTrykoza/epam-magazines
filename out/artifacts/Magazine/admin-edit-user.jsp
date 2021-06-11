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
<div class="container">
    <header>

        <nav class="navbar navbar-light">
            <div class="container-fluid">
                <!-- -----------------------------------logo----------------------------------------------- -->
                <a class="navbar-brand header-brand-name " href="mainPage.jsp">Subscriptions</a>
                <c:if test="${sessionScope.loggedUser != null}">
                    <h5>User: ${sessionScope.loggedUser.name}</h5>
                </c:if>
            </div>
        </nav>

    </header>

    <!-- -----------------------------------header-category----------------------------------------------- -->
    <nav class="nav justify-content-center category-block">
        <a class="nav-link category-block-link" href="controller?command=showAllUsers&currentPage=1">User Setting</a>
        <a class="nav-link category-block-link" href="controller?command=showAllMagazine&currentPage=1">Magazine
            Setting</a>
    </nav>


    <table class="table table-hover align-middle text-truncate">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Login</th>
            <th scope="col">Name</th>
            <th scope="col">Status</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody style="white-space: nowrap; overflow: hidden;">
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.name}</td>
                <td>
                    <c:choose>
                    <c:when test="${user.status == true}">
                    Active
                    </c:when>
                    <c:otherwise>
                    Blocked
                    </c:otherwise>
                    </c:choose>
                <td>
                    <form action="controller" method="post" style="display: inline-block;">
                        <input type="hidden" name="command" value="adminUserSetting">
                        <input type="hidden" name="setStatus" value="block">
                        <input type="hidden" name="userId" value=${user.id}>
                        <div class="col-auto">
                            <input type="submit" class="btn btn-danger" value="Block">
                        </div>
                    </form>
                    <form action="controller" method="post" style="display: inline-block;">
                        <input type="hidden" name="command" value="adminUserSetting">
                        <input type="hidden" name="setStatus" value="unblock">
                        <input type="hidden" name="userId" value=${user.id}>
                        <input type="submit" class="btn btn-success" value="Unblock">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%--    ----------------------------------------------------------NAV--------------------------------------------------------------    --%>

    <nav aria-label="NavigationUsers">
        <ul class="pagination justify-content-center">
            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link"
                                         href="controller?command=showAllUsers&currentPage=${currentPage-1}"
                                         aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link">${i}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link"
                                                 href="controller?command=showAllUsers&currentPage=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item"><a class="page-link"
                                         href="controller?command=showAllUsers&currentPage=${currentPage+1}"
                                         aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
                </li>
            </c:if>
        </ul>
    </nav>

    <%--    ----------------------------------------------------------NAV--------------------------------------------------------------    --%>

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js"
            integrity="sha384-oesi62hOLfzrys4LxRF63OJCXdXDipiYWBnvTl9Y9/TRlw5xlKIEHpNyvvDShgf/" crossorigin="anonymous">
    </script>
</body>

</html>