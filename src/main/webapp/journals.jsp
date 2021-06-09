<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        <a class="nav-link category-block-link"
           href="controller?command=showAllMagazine&currentPage=1">All</a>
        <c:forEach var="category" items="${categories}">
            <a class="nav-link category-block-link"
               href="controller?command=sortMagazineByCategory&categoryName=${category.name}&currentPage=1">${category.name}</a>
        </c:forEach>
    </nav>
    <!-- -----------------------------------sorted-option----------------------------------------------- -->
    <nav class="navbar navbar-light">
        <div class="container-fluid">
            <div class="btn-drop">
                <a type="button" class="btn btn-drop-name"
                   href="controller?command=sortMagazineByName&prevRequest=<%= request.getParameter("command") %>&currentPage=<%= request.getParameter("currentPage") %>">By
                    name</a>
                <div class="dropdown">
                    <button class="btn btn-custom-drop btn-drop-price" type="button"
                            data-toggle="dropdown" aria-expanded="false">
                        By price
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <li><a class="dropdown-item"
                               href="controller?command=sortMagazineByPriceLH&prevRequest=<%= request.getParameter("command") %>&currentPage=<%= request.getParameter("currentPage") %>">Price:
                            Low to High</a>
                        </li>
                        <li><a class="dropdown-item"
                               href="controller?command=sortMagazineByPriceHL&prevRequest=<%= request.getParameter("command") %>&currentPage=<%= request.getParameter("currentPage") %>">Price:
                            High to Low</a></li>
                    </ul>
                </div>
            </div>

            <form class="d-flex col-12 col-sm-5 col-md-5" method="get">
                <input type="hidden" name="command" value="findMagazineByName">
                <input type="hidden" name="currentPage" value="1">
                <input class="form-control mr-2" type="search" name="magazineName" placeholder="Search query"
                       aria-label="Search">
                <input type="submit" class="btn btn-primary" value="Search">
            </form>
        </div>
    </nav>

    <!-- -----------------------------------product-list----------------------------------------------- -->
    <div id="productList">
        <div class="row">

            <c:choose>
                <c:when test="${magazines.size() == 0}">
                    <div class="alert alert-warning" role="alert">
                        No result!!
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${magazines}" var="magazine">
                        <div class="col-12 col-sm-6 col-md-4 col-lg-3 card-castom">
                            <!-- -----------------------------------product-data----------------------------------------------- -->
                            <div id="product278009" class="card">
                                <div class="card-body">
                                    <h5 class="card-title name" style="height: 60px">${magazine.name}</h5>
                                    <p class="card-text description" style="height: 50px;"
                                       title="${magazine.description}">${magazine.description}</p>
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">Category:<span
                                                class="pull-right">${magazine.categoryName}</span>
                                        </li>
                                        <li class="list-group-item">Price:<span
                                                class="pull-right">${magazine.price}</span></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="text-center">
                                <a id="addToCart" class="btn btn-primary btn-custom-orange btn-addTo-cart" role="button"
                                   data-id-product="278009">Add</a>
                            </div>
                        </div>
                    </c:forEach>
                    <!-- -----------------------------------pagination----------------------------------------------- -->
                    <nav aria-label="NavigationMagazine">
                        <ul class="pagination justify-content-center">
                            <c:if test="${currentPage != 1}">
                                <li class="page-item"><a class="page-link"
                                                         href="controller?command=<%= request.getParameter("command") %>&currentPage=${currentPage-1}"
                                                         aria-label="Previous"><span
                                        aria-hidden="true">&laquo;</span></a>
                                </li>
                            </c:if>

                            <c:forEach begin="1" end="${noOfPages}" var="i">
                                <c:choose>
                                    <c:when test="${currentPage eq i}">
                                        <li class="page-item active"><a class="page-link">${i}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link"
                                                                 href="controller?command=<%= request.getParameter("command") %>&currentPage=${i}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <c:if test="${currentPage lt noOfPages}">
                                <li class="page-item"><a class="page-link"
                                                         href="controller?command=<%= request.getParameter("command") %>&currentPage=${currentPage+1}"
                                                         aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                    <!-- -----------------------------------pagination----------------------------------------------- -->
                </c:otherwise>
            </c:choose>
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