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
loadCategories();
var lastCounter = 0;
var firstCounter = 0;
var currentCategory = null;
var isLastPage = false;
var isFirstPage = false;
var searchValue = null;
function loadCategories() {
    request('POST', 'FirstController', {
        action: 'category'
    }, function (res) {
        console.log(res);
        var container = document.getElementById('ulCategories');
        var domparser = new DOMParser();
        var doc = domparser.parseFromString(res.responseText, "text/xml");
        var categories = doc.getElementsByTagName('category');
        console.log(res.responseText);
        var output = "";
        for (var i = 0; i < categories.length; i++) {
            console.log(123);
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
    isLastPage = false;
    isFirstPage = false;
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
        console.log(res.responseText);
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
    });
}

function accessNextCategory() {
    console.log("start access next category");
    isFirstPage = false;
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
                isLastPage = false;
            } else {
                isLastPage = true;
                var nextbutton = document.getElementById("nextButton");
                nextbutton.setAttribute("aria-disabled", "true");
            }
        });
    }
}

function accessPreCategory() {
    console.log("start access pre category");
    isLastPage = false;
    console.log(isFirstPage);
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
                isFirstPage = false;
            } else {
                isFirstPage = true;
            }
        });
    }
}


function search() {
    console.log("search started");
    searchValue = document.getElementById('txtsearch').value;
    console.log('search value: ' + searchValue);
    accessCategory(currentCategory);
}

function accessProduct(id) {
     window.location.href = "/StyleClothes/FirstController?action=accessProduct&id="+id;
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
