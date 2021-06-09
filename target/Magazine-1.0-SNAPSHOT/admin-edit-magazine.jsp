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
                    <h5>User: ${sessionScope.loggedUser.name}</h5>
                </c:if>
            </div>
        </nav>
    </div>
</header>

<!-- -----------------------------------header-category----------------------------------------------- -->
<nav class="nav justify-content-center category-block">
    <a class="nav-link category-block-link" href="controller?command=showAllUsers&currentPage=1">User Setting</a>
    <a class="nav-link category-block-link"
       href="controller?command=showAllMagazine&currentPage=1">Magazine Setting</a>
</nav>
<!-- -----------------------------------product-list----------------------------------------------- -->
<div id="cart" class="container">
    <a class="btn btn-primary" href="#" role="button" data-toggle="modal"
       data-target="#addMagazineBtnModal">Add magazine to DataBase</a>
    <a class="btn btn-primary" href="#" role="button" data-toggle="modal"
       data-target="#addCategoryBtnModal">Add new category</a>
    <hr>
    <table class="table table-hover align-middle">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
            <th scope="col">Category</th>
            <th scope="col">Price</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody style="overflow: hidden;">
        <c:forEach items="${sessionScope.magazines}" var="magazine" varStatus="vs">
            <tr>
                <td>${magazine.name}</td>
                <td class="text-truncate" style="max-width: 450px;"
                    title="${magazine.description}">${magazine.description}</td>
                <td class="text-truncate" style="max-width: 80px;"
                    title="${magazine.categoryName}">${magazine.categoryName}</td>
                <td>${magazine.price}</td>
                <td>

                    <a class="btn btn-warning" href="#" role="button" data-toggle="modal"
                       data-target="#editMagazineBtnModal${vs.index}">Edit</a>
                    <!-- --------------------------------- Edit-journal-popup-------------------------------- -->
                    <div class="modal fade" id="editMagazineBtnModal${vs.index}" aria-hidden="true"
                         aria-labelledby="exampleModalToggleLabel"
                         tabindex="-1">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Edit Magazine ID#${magazine.id}</h5>
                                    <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"
                                            aria-hidden="true">
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form action="controller" method="post">
                                        <input type="hidden" name="command" value="adminMagazineSetting">
                                        <input type="hidden" name="action" value="edit">
                                        <input type="hidden" name="magazineId" value=${magazine.id}>
                                        <div class="mb-3">
                                            <label for="inputName" class="form-label">Name</label>
                                            <input name="magazineName" type="text" class="form-control" id="inputName"
                                                   value="${magazine.name}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="inputDescription" class="form-label">Description</label>
                                            <input name="magazineDescription" type="text" class="form-control"
                                                   id="inputDescription" value="${magazine.description}" required>
                                        </div>
                                        <div class="mb-3">
                                            <label for="inputCategory" class="form-label">Category</label>
                                            <select id="inputCategory" class="form-select"
                                                    name="magazineCategory" required>
                                                <option value="">Open this select menu</option>
                                                <c:forEach var="category" items="${categories}">
                                                    <option value="${category.id}">${category.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-3">
                                            <label for="inputPrice" class="form-label">Price</label>
                                            <input name="magazinePrice" type="text" class="form-control" id="inputPrice"
                                                   value="${magazine.price}"
                                                   required>
                                        </div>
                                        <div class="mb-3">
                                            <input type="submit" class="btn btn-primary btn-custom-orange"
                                                   value="Submit"/>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <form action="controller" method="post" style="display: inline-block;">
                        <input type="hidden" name="command" value="adminMagazineSetting">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="magazineId" value=${magazine.id}>
                        <input type="submit" class="btn btn-danger" value="Remove">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <nav aria-label="NavigationMagazine">
        <ul class="pagination justify-content-center">
            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link"
                                         href="controller?command=showAllMagazine&currentPage=${currentPage-1}"
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
                                                 href="controller?command=showAllMagazine&currentPage=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item"><a class="page-link"
                                         href="controller?command=showAllMagazine&currentPage=${currentPage+1}"
                                         aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>

<!-- --------------------------------- ADD-journal-popup-------------------------------- -->
<div class="modal fade" id="addMagazineBtnModal" aria-hidden="true"
     aria-labelledby="exampleModalToggleLabel"
     tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Add Magazine</h5>
                <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"
                        aria-hidden="true">
                </button>
            </div>
            <div class="modal-body">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="adminMagazineSetting">
                    <input type="hidden" name="action" value="add">
                    <div class="mb-3">
                        <label for="addInputName" class="form-label">Name</label>
                        <input type="text" name="magazineName" class="form-control"
                               id="addInputName" required>
                    </div>
                    <div class="mb-3">
                        <label for="addInputDescription" class="form-label">Description</label>
                        <input type="text" name="magazineDescription" class="form-control"
                               id="addInputDescription" required>
                    </div>
                    <div class="mb-3">
                        <label for="addInputGenre" class="form-label">Category</label>
                        <select id="addInputGenre" class="form-select"
                                name="magazineCategory" required>
                            <option value="">Open this select menu</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="addInputPrice" class="form-label">Price</label>
                        <input type="text" name="magazinePrice" class="form-control"
                               id="addInputPrice" required>
                    </div>
                    <div class="mb-3">
                        <input type="submit" class="btn btn-primary btn-custom-orange"
                               value="Submit">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- --------------------------------- ADD-genre-popup-------------------------------- -->
<div class="modal fade" id="addCategoryBtnModal" aria-hidden="true"
     aria-labelledby="exampleModalToggleLabel"
     tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Add Genre</h5>
                <button type="button" class="btn-close" data-dismiss="modal" aria-label="Close"
                        aria-hidden="true">
                </button>
            </div>
            <div class="modal-body">
                <form action="controller" name="settingMagazine" method="post">
                    <input type="hidden" name="command" value="adminMagazineSetting">
                    <input type="hidden" name="action" value="addCategory">
                    <div class="mb-3">
                        <label for="addGenreName" class="form-label">Category name</label>
                        <input type="text" name="categoryName" class="form-control"
                               id="addGenreName" required>
                    </div>
                    <div class="mb-3">
                        <input type="submit" class="btn btn-primary btn-custom-orange"
                               value="Submit">
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