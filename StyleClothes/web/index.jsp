<%-- 
    Document   : index
    Created on : Nov 28, 2019, 10:12:46 AM
    Author     : Dell
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>     
        <link rel="stylesheet" href="styles.css">
        <script src="myscripts.js"></script>
    </head>
    <body>
        <c:set var="fullname" value="${sessionScope.FULLNAME}" />
        <div style="overflow: hidden; padding: 10px">
            <h1 style="float: left">
                Hello 
                <span class="badge badge-secondary">
                    <c:out value="${fullname}"/><br/>
                </span>
            </h1>           
            <c:if test="${fullname == 'GUEST'}">
                <a href="login.jsp" style="float: right" class="btn btn-secondary btn-lg active" role="button" aria-pressed="true">login</a>
            </c:if> 
            <c:if test="${fullname != 'GUEST'}">
                <form action="LoginController" method="post">
                    <input type="submit" style="float: right" class="btn btn-secondary btn-lg active" role="button" aria-pressed="true" value="logout"/>  
                </form>
            </c:if>
        </div>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">

            <a class="navbar-brand" onclick="accessCategory('main')">STYLE CLOTHES</a>

            <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0" id="ulCategories">
                    <li class="nav-item">
                        <a class="nav-link">Link</a>
                    </li>
                </ul>
                <div class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" id="txtsearch"/>
                    <button class="btn btn-outline-success my-2 my-sm-0" name="btnAction" value="search" onclick="search()">Search</button>
                </div>
            </div>
        </nav>


        <h3 class="my-4" id="mostseeh3">Most see products</h3>
        <div class="row row-cols-1 row-cols-md-4" style="margin: 20px" id="productContainer">
            <div class="col mb-4">
                <div class="card">
                    <img src="..." class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                    </div>
                </div>
            </div>
            <div class="col mb-4">
                <div class="card">
                    <img src="..." class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                    </div>
                </div>
            </div>
            <div class="col mb-4">
                <div class="card">
                    <img src="..." class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                    </div>
                </div>
            </div>
            <div class="col mb-4">
                <div class="card">
                    <img src="..." class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content.</p>
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${fullname != 'GUEST'}">
            <h3 class="my-4">Related Projects</h3>
        </c:if>
        <div class="row row-cols-1 row-cols-md-4" style="margin: 20px" id="historyProductContainer">
        </div>
        <nav aria-label="Page navigation example" id="paging" style="display: block">
            <ul class="pagination justify-content-center">
                <li class="page-item" id="preLi">
                    <a class="page-link" href="#" aria-disabled="true" onclick="accessPreCategory()" id="preButton">Previous</a>
                </li>
                <li class="page-item"><a class="page-link" href="#" id="currentNumber">1</a></li>
                <li class="page-item" id="nextLi">
                    <a class="page-link" href="#" aria-disabled="false" onclick="accessNextCategory()" id="nextButton">Next</a>
                </li>
            </ul>
        </nav>
    </body>
    <script>
        firstLoading();
    </script>
</html>
