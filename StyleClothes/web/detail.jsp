<%-- 
    Document   : detail
    Created on : Dec 10, 2019, 9:33:06 AM
    Author     : Dell
--%>

<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail page</title>
        <link rel="stylesheet" href="styles.css">
        <script>
            function request(method, controller, params, callback) {
                var xhttp = new XMLHttpRequest();
                if (!xhttp) {
                    try {
                        xmlHttp = new ActiveXObject('Msxml12.XMLHTTP');
                    } catch (e) {
                        xmlHttp = new ActiveXObject('Microsoft.XMLHTTP');
                    }
                }
                xhttp.onreadystatechange = function () {
                    if (this.readyState === 4 && this.status === 200) {
                        callback(xhttp);
                    }
                };
                xhttp.open(method, controller, true);
                xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                if (params !== null) {
                    xhttp.send(querialize(params));
                } else {
                    xhttp.send("abc");
                }
            }

            function querialize(params) {
                var list = [];
                for (var key in params) {
                    list.push(encodeURIComponent(key) + "=" + encodeURIComponent(params[key]));
                }
                return list.join("&");
            }

            function access(link, id) {
                request('POST', 'DetailController', {
                    action: 'accessLink',
                    id: id
                }, function (res) {
                    console.log("access link product");
                    window.location.href = link;
                });
            }




            function loading(id) {
                console.log("loading .....");
                request('POST', 'DetailController', {
                    action: 'firstAccess',
                    id: id
                }, function (res) {
                    var container = document.getElementById('relativeProductContainer');
                    var domparser = new DOMParser();
                    var doc = domparser.parseFromString(res.responseText, "text/xml");
                    var products = doc.getElementsByTagName('product');
                    var output = "";
                    for (var i = 0; i < products.length; i++) {
                        var product = products[i];
                        var id = product.childNodes[5].firstChild.nodeValue;
                        var name = product.querySelector('name').textContent;
                        var price = product.querySelector('price').textContent;
                        var picture = product.querySelector('picture').textContent;
                        var link = product.querySelector('link').textContent;
                        var counter = product.querySelector('counter').textContent;
                        var idstr = "'" + id + "'";
                        output += '<div class="col mb-4">'
                                + '<div class="card"  onclick="accessProduct(' + idstr + ')">'
                                + '<img src="' + picture + '" class="card-img-top" alt="...">'
                                + '<div class="card-body">'
                                + '<h5 class="card-title">' + name + '</h5>'
                                + '<p class="card-text">' + price + 'vnđ</p>'
                                + '</div>'
                                + '</div>'
                                + '</div>';
                    }
                    container.innerHTML = output;
                });

                request('POST', 'DetailController', {
                    action: 'secondAccess',
                    id: id
                }, function (res) {
                    var container = document.getElementById('seeNextProductContainer');
                    var domparser = new DOMParser();
                    var doc = domparser.parseFromString(res.responseText, "text/xml");
                    var products = doc.getElementsByTagName('product');
                    var output = "";
                    for (var i = 0; i < products.length; i++) {
                        var product = products[i];
                        var id = product.childNodes[5].firstChild.nodeValue;
                        var name = product.querySelector('name').textContent;
                        var price = product.querySelector('price').textContent;
                        var picture = product.querySelector('picture').textContent;
                        var link = product.querySelector('link').textContent;
                        var counter = product.querySelector('counter').textContent;
                        var idstr = "'" + id + "'";
                        output += '<div class="col mb-4">'
                                + '<div class="card"  onclick="accessProduct(' + idstr + ')">'
                                + '<img src="' + picture + '" class="card-img-top" alt="...">'
                                + '<div class="card-body">'
                                + '<h5 class="card-title">' + name + '</h5>'
                                + '<p class="card-text">' + price + 'vnđ</p>'
                                + '</div>'
                                + '</div>'
                                + '</div>';
                    }
                    container.innerHTML = output;
                });

            }
            function accessProduct(id) {
                window.location.href = "/StyleClothes/FirstController?"
                        + "action=accessProduct"
                        + "&id=" + id;
            }
        </script>
    </head>
    <body> 
        <c:set var="doc" value="${requestScope.DOC}" />
        <c:set var="userid" value="${sessionScope.USERID}" />
        <x:set var="product" select="$doc//product" />
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <img class="img-fluid" src="<x:out select="$product/picture" />" alt="">
                </div>

                <div class="col-md-4">
                    <h3 class="my-3"><x:out select="$product/name" /></h3>
                    <ul>
                        <li>Màu sắc: <x:out select="$product/colorId/vietnamName" /></li>
                        <li>Phân loại: <x:out select="$product/typeId/name" /></li><br/>
                        <button type="button" class="btn btn-outline-success" onclick="access('<x:out select="$product/link" />', '<x:out select="$product/id" />')">truy cập sản phẩm</button>
                        <a href="index.jsp" >back to home page</a>
                    </ul>
                </div>

            </div>
            <!-- /.row -->

            <!-- Related Projects Row -->
            <h3 class="my-4">Related Projects</h3>

            <div class="row row-cols-1 row-cols-md-4" style="margin: 20px" id="relativeProductContainer">
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
                            <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                        </div>
                    </div>
                </div>

            </div>

            <h3 class="my-4">Should see next</h3>

            <div class="row row-cols-1 row-cols-md-4" style="margin: 20px" id="seeNextProductContainer">
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
                            <p class="card-text">This is a longer card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                        </div>
                    </div>
                </div>

            </div>

        </div>
    </body>
    <script>
        loading('<x:out select="$product/id" />')
    </script>
</html>
