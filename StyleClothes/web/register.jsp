<%-- 
    Document   : register
    Created on : Dec 4, 2019, 10:50:04 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <style>
            form {
                border: 3px solid #f1f1f1;
            }

            /* Full-width inputs */
            input[type=text], input[type=password] {
                width: 100%;
                padding: 12px 20px;
                margin: 8px 0;
                display: inline-block;
                border: 1px solid #ccc;
                box-sizing: border-box;
            }

            /* Set a style for all buttons */
            button {
                background-color: #4CAF50;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                border: none;
                cursor: pointer;
                width: 100%;
            }

            /* Add a hover effect for buttons */
            button:hover {
                opacity: 0.8;
            }

            /* Extra style for the cancel button (red) */
            .cancelbtn {
                width: auto;
                padding: 10px 18px;
                background-color: #f44336;
            }

            /* Center the avatar image inside this container */
            .imgcontainer {
                text-align: center;
                margin: 24px 0 12px 0;
            }

            /* Avatar image */
            img.avatar {
                width: 10%;
                border-radius: 50%;
            }

            /* Add padding to containers */
            .container {
                padding: 16px;
            }

            /* The "Forgot password" text */
            span.psw {
                float: right;
                padding-top: 16px;
            }

            /* Change styles for span and cancel button on extra small screens */
            @media screen and (max-width: 300px) {
                span.psw {
                    display: block;
                    float: none;
                }
                .cancelbtn {
                    width: 100%;
                }
            }
        </style>
    </head>

    <body>
        <form action="RegisterController" method="post">
            <div class="imgcontainer">
                <img src="men-street-style-outfits.png" class="avatar">
                <h1>Style Clothes register</h1>
                <>
            </div>

            <div class="container">
                <label for="uname"><b>Username</b></label>
                <input type="text" placeholder="Enter Username" name="uname" value="${requestScope.username}" required>
                <c:set var="nameError" value="${requestScope.nameerror}" />
                <c:if test="${not empty nameError}" >
                    <h5 style="color:red">${nameError}</h5>
                </c:if>
                <label for="psw"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="psw" value="${requestScope.passsword}" required>

                <label for="cpsw"><b>Confirm password</b></label>
                <input type="password" placeholder="Confirm Password" name="cpsw" required>
                <c:set var="repasswordError" value="${requestScope.repassworderror}" />
                <c:if test="${not empty repasswordError}" >
                    <h5 style="color:red">${repasswordError}</h5>
                </c:if>
                <label for="fullname"><b>Full Name</b></label>
                <input type="text" placeholder="Enter address" name="fullname" value="${requestScope.fullname}" required>
                <label for="add"><b>Address</b></label>
                <input type="text" placeholder="Enter address" name="add" value="${requestScope.address}" required>
                <label for="phonenum"><b>Phone number</b></label>
                <input type="text" placeholder="Enter address" name="phonenum" value="${requestScope.phonenumber}" required>
                <c:set var="phoneError" value="${requestScope.phoneerror}" />
                <c:if test="${not empty phoneError}" >
                    <h5 style="color:red">${phoneError}</h5>
                </c:if>
                <button type="submit">Sign up</button>
            </div>

            <div class="container" style="background-color:#f1f1f1">
                <button type="button" class="cancelbtn" onclick="window.location.href = 'login.jsp'">Cancel</button>
            </div>
        </form>
    </body>
</html>
