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
var listProducts = new Array();
var lr = new Array();
var nummax = 0;
var isSearch = false;
function firstLoading() {
    loadCategories();
    currentCategory = sessionStorage.getItem("currentCategory");
    if (currentCategory === "main" || currentCategory == null) {
        currentCategory = "main";
        accessCategory(currentCategory);
        var paging = document.getElementById('paging');
        paging.setAttribute("style", "display: none");
        var h3 = document.getElementById('mostseeh3');
        h3.setAttribute("style", "display: block");
    } else {
        num = sessionStorage.getItem("num");
        if (num == "1") {
            accessCategory(currentCategory);
        } else
            loadLastListProduct(sessionStorage.getItem("num"));
//        currentCategory = sessionStorage.getItem("currentCategory");
//        console.log("current category: " + currentCategory);
//        lastCounter = sessionStorage.getItem("counter");
//        console.log("last counter: " + lastCounter);
//        isFirstPage = sessionStorage.getItem("isFirstPage");
//        console.log("is first page: " + isFirstPage);
//        searchValue = sessionStorage.getItem("searchValue");
//        console.log("search value: " + searchValue);
//        num = sessionStorage.getItem("num");
//        console.log("num: " + num);
//        if (currentCategory != null) {
//            if (isFirstPage == 'true') {
//                console.log("access category hihi");
//                accessCategory(currentCategory);
//            } else {
//                console.log("access next category hihi");
//                accessNextCategory();
//                var currentNum = document.getElementById('currentNumber');
//                currentNum.innerHTML = num;
//                var preli = document.getElementById('preLi');
//                preli.setAttribute("class", "page-item");
//                console.log("done access next category hihi");
//            }
//        }
    }
}

function loadCategories() {
    request('POST', 'FirstController', {
        action: 'category'
    }, function (res) {
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

    listProducts = [];
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
    num = 1;
    currentNum.innerHTML = num;
    sessionStorage.setItem("num", num);
    request('POST', 'FirstController', {
        action: 'accessCategory',
        id: id,
        txtsearch: searchValue
    }, function (res) {
        var container = document.getElementById('productContainer');
        var domparser = new DOMParser();
        var doc = domparser.parseFromString(res.responseText, "text/xml");
        var products = doc.getElementsByTagName('product');
        listProducts.push(products);
        nummax = 1;
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
            }
            if (i === (products.length - 1)) {
                lastCounter = counter;
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
            if (products.length <= 0) {
                isLastPage = true;
                var nextLi = document.getElementById('nextLi');
                nextLi.setAttribute("class", "page-item disabled");
            } else {
                isLastPage = false;
                var nextLi = document.getElementById('nextLi');
                nextLi.setAttribute("class", "page-item");
                listProducts.push(products);
                nummax = num + 1;
            }
        });



    });
}

function accessNextCategory() {
    isFirstPage = false;
    sessionStorage.setItem("isFirstPage", isFirstPage);

    var preli = document.getElementById('preLi');
    preli.setAttribute("class", "page-item");

    if (!isLastPage) {
        var currentNum = document.getElementById('currentNumber');
        num = parseInt(currentNum.innerHTML, 10);
//        request('POST', 'FirstController', {
//            action: 'accessNextCategory',
//            id: currentCategory,
//            lastCounter: lastCounter,
//            txtsearch: searchValue
//        }, function (res) {

        var container = document.getElementById('productContainer');
//        var domparser = new DOMParser();
//        var doc = domparser.parseFromString(res.responseText, "text/xml");
//        var products = doc.getElementsByTagName('product');
        var products = [];
        if (isSearch) {
            products = lr[num];
            console.log("take from list result");
        } else {
            products = listProducts[num];
        }
        var output = "";
        if (products != null) {
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
                    }
                    if (i === (products.length - 1)) {
                        lastCounter = counter;
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


                if (num === (nummax)) {
                    request('POST', 'FirstController', {
                        action: 'accessNextCategory',
                        id: currentCategory,
                        lastCounter: lastCounter,
                        txtsearch: searchValue
                    }, function (res) {
                        var domparser = new DOMParser();
                        var doc = domparser.parseFromString(res.responseText, "text/xml");
                        var products = doc.getElementsByTagName('product');
                        if (products.length <= 0) {
                            isLastPage = true;
                            var nextLi = document.getElementById('nextLi');
                            nextLi.setAttribute("class", "page-item disabled");
                        } else {
                            isLastPage = false;
                            var nextLi = document.getElementById('nextLi');
                            nextLi.setAttribute("class", "page-item");
                            listProducts.push(products);
                            nummax = nummax + 1;
                        }
                    });
                }


            } else {
                isLastPage = true;
                var nextli = document.getElementById('nextLi');
                nextli.setAttribute("class", "page-item disabled");
            }
        } else {
            request('POST', 'FirstController', {
                action: 'accessNextCategory',
                id: currentCategory,
                lastCounter: lastCounter,
                txtsearch: searchValue
            }, function (res) {
                var domparser = new DOMParser();
                var doc = domparser.parseFromString(res.responseText, "text/xml");
                var products = doc.getElementsByTagName('product');
                if (products.length <= 0) {
                    isLastPage = true;
                    var nextLi = document.getElementById('nextLi');
                    nextLi.setAttribute("class", "page-item disabled");
                } else {
                    isLastPage = false;
                    if(isSearch){
                        lr.push(products);
                    }else listProducts.push(products);
                    nummax = nummax + 1;
                }
            });
        }

//        });
    }
}

function accessPreCategory() {
    isLastPage = false;
    var nextli = document.getElementById('nextLi');
    nextli.setAttribute("class", "page-item");
    if (!isFirstPage) {
        var currentNum = document.getElementById('currentNumber');
        var num = parseInt(currentNum.innerHTML, 10);
//        request('POST', 'FirstController', {
//            action: 'accessPreCategory',
//            id: currentCategory,
//            firstCounter: firstCounter,
//            txtsearch: searchValue
//        }, function (res) {
        var container = document.getElementById('productContainer');
//        var domparser = new DOMParser();
//        var doc = domparser.parseFromString(res.responseText, "text/xml");
//        var products = doc.getElementsByTagName('product');
        if ((num - 1) > 0) {
            var products = [];
            if (isSearch) {
                products = lr[num - 2];
            } else {
                products = listProducts[num - 2];
            }
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
                    }
                    if (i === (products.length - 1)) {
                        lastCounter = counter;
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
            }

            if (num === 1) {
                isFirstPage = true;
                sessionStorage.setItem("isFirstPage", isFirstPage);

                var preli = document.getElementById('preLi');
                preli.setAttribute("class", "page-item disabled");
            }

//            request('POST', 'FirstController', {
//                action: 'accessPreCategory',
//                id: currentCategory,
//                firstCounter: firstCounter,
//                txtsearch: searchValue
//            }, function (res) {
//                var domparser = new DOMParser();
//                var doc = domparser.parseFromString(res.responseText, "text/xml");
//                var products = doc.getElementsByTagName('product');
//                if (products.length <= 0) {
//                    isFirstPage = true;
//                    sessionStorage.setItem("isFirstPage", isFirstPage);
//
//                    var preli = document.getElementById('preLi');
//                    preli.setAttribute("class", "page-item disabled");
//                } else {
//                    isFirstPage = false;
//                    sessionStorage.setItem("isFirstPage", isFirstPage);
//
//                    var preli = document.getElementById('preLi');
//                    preli.setAttribute("class", "page-item");
//                }
//            });


        }
//        else {
//            isFirstPage = true;
//            sessionStorage.setItem("isFirstPage", isFirstPage);
//
//            var preli = document.getElementById('preLi');
//            preli.setAttribute("class", "page-item disabled");
//        }
//        });
    }
}


function search() {
    lr = [];
    searchValue = document.getElementById('txtsearch').value;
    if (searchValue === "") {
        isSearch = false;
        accessCategory(currentCategory);
    } else {
        isSearch = true;
        let productsSearch = [];
        var countproduct = 0;
        for (var j = 0; j < listProducts.length; j++) {
            var products = listProducts[j];
            for (var i = 0; i < products.length; i++) {
                var product = products[i];
                var name = product.querySelector('name').textContent;
                if (name.toString().indexOf(searchValue, 0) > -1) {
                    countproduct = countproduct + 1;
                    productsSearch.push(product);
                    if ((countproduct % 24) === 0) {
                        lr.push(productsSearch);
                        while (productsSearch.length > 0) {
                            productsSearch.pop();
                        }
                    }
                }
            }
        }
        if (productsSearch.length > 0) {
            lr.push(productsSearch);
        }
        sessionStorage.setItem("searchValue", searchValue);
        nummax = lr.length;
//    accessCategory(currentCategory);
        loadFirstSearch(lr[0]);
    }
}

function loadFirstSearch(values) {
    var container = document.getElementById('productContainer');
    var currentNum = document.getElementById('currentNumber');
    num = 1;
    currentNum.innerHTML = num;
    sessionStorage.setItem("num", num);
    var products = values;
    if (isSearch) {
    } else {
        products = listProducts[0];
    }
    var output = "";
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
        }
        if (i === (products.length - 1)) {
            lastCounter = counter;
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


function loadLastListProduct(end) {
    listProducts = [];
    var id = sessionStorage.getItem("currentCategory");
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
    isLastPage = false;
    isFirstPage = false;
    sessionStorage.setItem("isFirstPage", isFirstPage);
    var currentNum = document.getElementById('currentNumber');
    num = end - 1;
    currentNum.innerHTML = num;
    sessionStorage.setItem("num", num);
    request('POST', 'FirstController', {
        action: 'accessCategory',
        id: id,
        txtsearch: searchValue
    }, function (res) {
        var domparser = new DOMParser();
        var doc = domparser.parseFromString(res.responseText, "text/xml");
        var products = doc.getElementsByTagName('product');
        listProducts.push(products);
        nummax = listProducts.length;
        if (products.length < 24)
            isLastPage = true;
        for (var i = 0; i < products.length; i++) {
            var product = products[i];
            var counter = product.querySelector('counter').textContent;
            if (i === 0) {
                firstCounter = counter;
                sessionStorage.setItem("counter", firstCounter - 1);
            }
            if (i === (products.length - 1)) {
                lastCounter = counter;
            }
        }
        isFirstPage = true;
        sessionStorage.setItem("isFirstPage", isFirstPage);
        var preli = document.getElementById('preLi');
        preli.setAttribute("class", "page-item disabled");
        if (nummax <= end) {
            loadNext(end);
        }
    });
}

function loadNext(end) {
    request('POST', 'FirstController', {
        action: 'accessNextCategory',
        id: currentCategory,
        lastCounter: lastCounter,
        txtsearch: searchValue
    }, function (res) {
        var domparser = new DOMParser();
        var doc = domparser.parseFromString(res.responseText, "text/xml");
        var products = doc.getElementsByTagName('product');
        if (products.length <= 0) {
            accessNextCategory();
//            isLastPage = true;
//            var nextLi = document.getElementById('nextLi');
//            nextLi.setAttribute("class", "page-item disabled");
        } else {
            isLastPage = false;
            var nextLi = document.getElementById('nextLi');
            nextLi.setAttribute("class", "page-item");
            listProducts.push(products);
            nummax = listProducts.length;
            for (var i = 0; i < products.length; i++) {
                var product = products[i];
                var counter = product.querySelector('counter').textContent;
                if (i === 0) {
                    firstCounter = counter;
                    sessionStorage.setItem("counter", firstCounter - 1);
                }
                if (i === (products.length - 1)) {
                    lastCounter = counter;
                }
            }
            if (nummax <= end) {
                loadNext(end);
            } else {
                accessNextCategory();
            }
        }
    });
}
//========================================================================================
