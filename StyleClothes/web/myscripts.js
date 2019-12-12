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

console.log("start");
var lastCounter = 0;
var firstCounter = 0;
var currentCategory = "main";
var isLastPage = false;
var isFirstPage = false;
var searchValue = null;
var num = 0;
function firstLoading() {
    loadCategories();
    if (currentCategory === "main") {
        accessCategory(currentCategory);
        var paging = document.getElementById('paging');
        paging.setAttribute("style", "display: none");
        var h3 = document.getElementById('mostseeh3');
        h3.setAttribute("style", "display: block");
    } else {
        currentCategory = sessionStorage.getItem("currentCategory");
        console.log("current category: " + currentCategory);
        lastCounter = sessionStorage.getItem("counter");
        console.log("last counter: " + lastCounter);
        isFirstPage = sessionStorage.getItem("isFirstPage");
        console.log("is first page: " + isFirstPage);
        searchValue = sessionStorage.getItem("searchValue");
        console.log("search value: " + searchValue);
        num = sessionStorage.getItem("num");
        console.log("num: " + num);
        if (currentCategory != null) {
            if (isFirstPage == 'true') {
                console.log("access category hihi");
                accessCategory(currentCategory);
            } else {
                console.log("access next category hihi");
                accessNextCategory();
                var currentNum = document.getElementById('currentNumber');
                currentNum.innerHTML = num;
                var preli = document.getElementById('preLi');
                preli.setAttribute("class", "page-item");
                console.log("done access next category hihi");
            }
        }
    }
}

function loadCategories() {
    request('POST', 'FirstController', {
        action: 'category'
    }, function (res) {
        console.log(res);
        var container = document.getElementById('ulCategories');
        var domparser = new DOMParser();
        var doc = domparser.parseFromString(res.responseText, "text/xml");
        var categories = doc.getElementsByTagName('category');
        var output = "";
        for (var i = 0; i < categories.length; i++) {
            var category = categories[i];
            var id = category.querySelector('id').textContent;
            id = "'" + id + "'";
            var name = category.querySelector('name').textContent;
            var upper = category.querySelector('upper').textContent;
            output += '<li class="nav-item"><a class="nav-link" onclick="accessCategory(' + id + ')">' + name + '</a></li>';
        }
        container.innerHTML = output;
    });
}

function accessCategory(id) {
    if (id !== "main") {

        var paging = document.getElementById('paging');
        paging.setAttribute("style", "display: block");
        var h3 = document.getElementById('mostseeh3');
        h3.setAttribute("style", "display: none");
    } else {

        var paging = document.getElementById('paging');
        paging.setAttribute("style", "display: none");
        var h3 = document.getElementById('mostseeh3');
        h3.setAttribute("style", "display: block");
    }
    currentCategory = id;
    sessionStorage.setItem("currentCategory", currentCategory);
    isLastPage = false;
    isFirstPage = false;
    sessionStorage.setItem("isFirstPage", isFirstPage);
    var currentNum = document.getElementById('currentNumber');
    currentNum.innerHTML = 1;
    sessionStorage.setItem("num", 1);
    console.log("start access category");
    request('POST', 'FirstController', {
        action: 'accessCategory',
        id: id,
        txtsearch: searchValue
    }, function (res) {
        var container = document.getElementById('productContainer');
        var domparser = new DOMParser();
        var doc = domparser.parseFromString(res.responseText, "text/xml");
        var products = doc.getElementsByTagName('product');
        var output = "";
        if (products.length < 24)
            isLastPage = true;
        for (var i = 0; i < products.length; i++) {
            var product = products[i];
            var id = product.childNodes[5].firstChild.nodeValue;
            var name = product.querySelector('name').textContent;
            var price = product.querySelector('price').textContent;
            var picture = product.querySelector('picture').textContent;
            var link = product.querySelector('link').textContent;
            var counter = product.querySelector('counter').textContent;
            if (i === 0) {
                firstCounter = counter;
                sessionStorage.setItem("counter", firstCounter - 1);
                console.log(firstCounter);
            }
            if (i === (products.length - 1)) {
                lastCounter = counter;
                console.log(lastCounter);
            }
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
        isFirstPage = true;
        sessionStorage.setItem("isFirstPage", isFirstPage);

        var preli = document.getElementById('preLi');
        preli.setAttribute("class", "page-item disabled");


        request('POST', 'FirstController', {
            action: 'accessNextCategory',
            id: currentCategory,
            lastCounter: lastCounter,
            txtsearch: searchValue
        }, function (res) {
            var domparser = new DOMParser();
            var doc = domparser.parseFromString(res.responseText, "text/xml");
            var products = doc.getElementsByTagName('product');
            console.log(products.length + " " + lastCounter);
            if (products.length <= 0) {
                isLastPage = true;
                var nextLi = document.getElementById('nextLi');
                nextLi.setAttribute("class", "page-item disabled");
            } else {
                isLastPage = false;
                var nextLi = document.getElementById('nextLi');
                nextLi.setAttribute("class", "page-item");
            }
        });



    });
}

function accessNextCategory() {
    console.log("start access next category");
    isFirstPage = false;
    sessionStorage.setItem("isFirstPage", isFirstPage);
    console.log("is first page: " + isFirstPage);
    console.log(isLastPage);

    var preli = document.getElementById('preLi');
    preli.setAttribute("class", "page-item");

    if (!isLastPage) {
        console.log("is not last page");
        var currentNum = document.getElementById('currentNumber');
        num = parseInt(currentNum.innerHTML, 10);
        request('POST', 'FirstController', {
            action: 'accessNextCategory',
            id: currentCategory,
            lastCounter: lastCounter,
            txtsearch: searchValue
        }, function (res) {
            var container = document.getElementById('productContainer');
            var domparser = new DOMParser();
            var doc = domparser.parseFromString(res.responseText, "text/xml");
            var products = doc.getElementsByTagName('product');
            var output = "";
            if (products.length > 0) {
                for (var i = 0; i < products.length; i++) {
                    var product = products[i];
                    var id = product.childNodes[5].firstChild.nodeValue;
                    var name = product.querySelector('name').textContent;
                    var price = product.querySelector('price').textContent;
                    var picture = product.querySelector('picture').textContent;
                    var link = product.querySelector('link').textContent;
                    var counter = product.querySelector('counter').textContent;
                    if (i === 0) {
                        firstCounter = counter;
                        sessionStorage.setItem("counter", firstCounter - 1);
                        console.log(firstCounter);
                    }
                    if (i === (products.length - 1)) {
                        lastCounter = counter;
                        console.log(lastCounter);
                    }
                    var idstr = "'" + id + "'";
                    output += '<div class="col mb-4">'
                            + '<div class="card" onclick="accessProduct(' + idstr + ')">'
                            + '<img src="' + picture + '" class="card-img-top" alt="...">'
                            + '<div class="card-body">'
                            + '<h5 class="card-title">' + name + '</h5>'
                            + '<p class="card-text">' + price + 'vnđ</p>'
                            + '</div>'
                            + '</div>'
                            + '</div>';
                }
                container.innerHTML = output;
                num = num + 1;
                currentNum.innerHTML = num;
                sessionStorage.setItem("num", num);
                isLastPage = false;
                console.log("load next success");


                request('POST', 'FirstController', {
                    action: 'accessNextCategory',
                    id: currentCategory,
                    lastCounter: lastCounter,
                    txtsearch: searchValue
                }, function (res) {
                    var domparser = new DOMParser();
                    var doc = domparser.parseFromString(res.responseText, "text/xml");
                    var products = doc.getElementsByTagName('product');
                    console.log(products.length + " " + lastCounter);
                    if (products.length <= 0) {
                        isLastPage = true;
                        var nextLi = document.getElementById('nextLi');
                        nextLi.setAttribute("class", "page-item disabled");
                    } else {
                        isLastPage = false;
                        var nextLi = document.getElementById('nextLi');
                        nextLi.setAttribute("class", "page-item");
                    }
                });


            } else {
                isLastPage = true;
                var nextli = document.getElementById('nextLi');
                nextli.setAttribute("class", "page-item disabled");
            }
        });
    }
}

function accessPreCategory() {
    console.log("start access pre category: " + currentCategory);
    isLastPage = false;
    console.log("is first page = " + isFirstPage);
    var nextli = document.getElementById('nextLi');
    nextli.setAttribute("class", "page-item");
    if (!isFirstPage) {
        console.log("is not first page");
        var currentNum = document.getElementById('currentNumber');
        var num = parseInt(currentNum.innerHTML, 10);
        request('POST', 'FirstController', {
            action: 'accessPreCategory',
            id: currentCategory,
            firstCounter: firstCounter,
            txtsearch: searchValue
        }, function (res) {
            var container = document.getElementById('productContainer');
            var domparser = new DOMParser();
            var doc = domparser.parseFromString(res.responseText, "text/xml");
            var products = doc.getElementsByTagName('product');
            var output = "";
            if (products.length > 0) {
                for (var i = 0; i < products.length; i++) {
                    var product = products[i];
                    var id = product.childNodes[5].firstChild.nodeValue;
                    var name = product.querySelector('name').textContent;
                    var price = product.querySelector('price').textContent;
                    var picture = product.querySelector('picture').textContent;
                    var link = product.querySelector('link').textContent;
                    var counter = product.querySelector('counter').textContent;
                    if (i === 0) {
                        firstCounter = counter;
                        sessionStorage.setItem("counter", firstCounter - 1);
                        console.log(firstCounter);
                    }
                    if (i === (products.length - 1)) {
                        lastCounter = counter;
                        console.log(lastCounter);
                    }
                    var idstr = "'" + id + "'";
                    output += '<div class="col mb-4">'
                            + '<div class="card" onclick="accessProduct(' + idstr + ')">'
                            + '<img src="' + picture + '" class="card-img-top" alt="..." >'
                            + '<div class="card-body">'
                            + '<h5 class="card-title">' + name + '</h5>'
                            + '<p class="card-text">' + price + 'vnđ</p>'
                            + '</div>'
                            + '</div>'
                            + '</div>';
                }
                container.innerHTML = output;
                num = num - 1;
                currentNum.innerHTML = num;
                sessionStorage.setItem("num", num);
                isFirstPage = false;
                sessionStorage.setItem("isFirstPage", isFirstPage);
                console.log("load pre success");


                request('POST', 'FirstController', {
                    action: 'accessPreCategory',
                    id: currentCategory,
                    firstCounter: firstCounter,
                    txtsearch: searchValue
                }, function (res) {
                    var domparser = new DOMParser();
                    var doc = domparser.parseFromString(res.responseText, "text/xml");
                    var products = doc.getElementsByTagName('product');
                    if (products.length <= 0) {
                        isFirstPage = true;
                        sessionStorage.setItem("isFirstPage", isFirstPage);

                        var preli = document.getElementById('preLi');
                        preli.setAttribute("class", "page-item disabled");
                    } else {
                        isFirstPage = false;
                        sessionStorage.setItem("isFirstPage", isFirstPage);

                        var preli = document.getElementById('preLi');
                        preli.setAttribute("class", "page-item");
                    }
                });


            } else {
                isFirstPage = true;
                sessionStorage.setItem("isFirstPage", isFirstPage);

                var preli = document.getElementById('preLi');
                preli.setAttribute("class", "page-item disabled");
            }
        });
    }
}


function search() {
    console.log("search started");
    searchValue = document.getElementById('txtsearch').value;
    sessionStorage.setItem("searchValue", searchValue);
    console.log('search value: ' + searchValue);
    accessCategory(currentCategory);
}

function accessProduct(id) {
    window.location.href = "/StyleClothes/FirstController?"
            + "action=accessProduct"
            + "&id=" + id;
//    request('POST', 'FirstController', {
//        action: 'accessProduct',
//        id: id
//    }, function (res) {
//        console.log("access product");
//        var domparser = new DOMParser();
//        var doc = domparser.parseFromString(res.responseText, "text/xml");
//        console.log(doc);
//        var products = doc.getElementsByTagName('product');
//        console.log(res.responseText);
//        window.location.href = "detail.jsp";
//    });
}



//========================================================================================
