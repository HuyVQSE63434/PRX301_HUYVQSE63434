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
var currentCategory = null;
var isLastPage = false;
var isFirstPage = false;
var searchValue = null;
function firstLoading() {
    loadCategories();
    currentCategory = sessionStorage.getItem("currentCategory");
    lastCounter = sessionStorage.getItem("counter");
    isFirstPage = sessionStorage.getItem("isFirstPage");
    searchValue = sessionStorage.getItem("searchValue");
    if (currentCategory !== null) {
        if (isFirstPage) {
            accessCategory(currentCategory);
        } else {
            accessNextCategory();
            var currentNum = document.getElementById('currentNumber');
            currentNum.innerHTML = sessionStorage.getItem("num");
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
    currentCategory = id;
    sessionStorage.setItem("currentCategory", currentCategory);
    isLastPage = false;
    isFirstPage = false;
    sessionStorage.setItem("isFirstPage", isFirstPage);
    var currentNum = document.getElementById('currentNumber');
    currentNum.innerHTML = 1;
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
    });
}

function accessNextCategory() {
    console.log("start access next category");
    isFirstPage = false;
    sessionStorage.setItem("isFirstPage", isFirstPage);
    console.log(isLastPage);
    if (!isLastPage) {
        console.log("is not last page");
        var currentNum = document.getElementById('currentNumber');
        var num = parseInt(currentNum.innerHTML, 10);
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
            } else {
                isLastPage = true;
                var nextbutton = document.getElementById("nextButton");
                nextbutton.setAttribute("aria-disabled", "true");
            }
        });
    }
}

function accessPreCategory() {
    console.log("start access pre category: "+currentCategory);
    isLastPage = false;
    console.log("is first page = "+isFirstPage);
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
            } else {
                isFirstPage = true;
                sessionStorage.setItem("isFirstPage", isFirstPage);
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
