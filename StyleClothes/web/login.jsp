<%-- 
    Document   : login
    Created on : Dec 3, 2019, 10:31:13 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <title>Styles Clothes</title>
        <script src="request.js"></script>
        <link rel="stylesheet" href="styles.css">
        <style>
            /* Coded with love by Mutiullah Samim */
            body,
            html {
                margin: 0;
                padding: 0;
                height: 100%;
                background: #60a3bc !important;
            }
            .user_card {
                height: 400px;
                width: 350px;
                margin-top: auto;
                margin-bottom: auto;
                background: #f39c12;
                position: relative;
                display: flex;
                justify-content: center;
                flex-direction: column;
                padding: 10px;
                box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
                -webkit-box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
                -moz-box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
                border-radius: 5px;
            }
            .brand_logo_container {
                position: absolute;
                height: 170px;
                width: 170px;
                top: -75px;
                border-radius: 50%;
                background: #60a3bc;
                padding: 10px;
                text-align: center;
            }
            .brand_logo {
                height: 150px;
                width: 150px;
                border-radius: 50%;
                border: 2px solid white;
            }
            .form_container {
                margin-top: 100px;
            }
            .login_btn {
                width: 100%;
                background: #c0392b !important;
                color: white !important;
            }
            .login_btn:focus {
                box-shadow: none !important;
                outline: 0px !important;
            }
            .login_container {
                padding: 0 2rem;
            }
            .input-group-text {
                background: #c0392b !important;
                color: white !important;
                border: 0 !important;
                border-radius: 0.25rem 0 0 0.25rem !important;
            }
            .input_user,
            .input_pass:focus {
                box-shadow: none !important;
                outline: 0px !important;
            }
            .custom-checkbox .custom-control-input:checked~.custom-control-label::before {
                background-color: #c0392b !important;
            }
        </style>
    </head>
    <!--Coded with love by Mutiullah Samim-->
    <body>
        <div class="container h-100">
            <div class="d-flex justify-content-center h-100">
                <div class="user_card">
                    <div class="d-flex justify-content-center">
                        <div class="brand_logo_container">
                            <img src="men-street-style-outfits.png" class="brand_logo" alt="Logo">
                        </div>
                    </div>
                    <div class="justify-content-center form_container">
                        <form>
                            <div class="input-group mb-3">
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                                </div>
                                <input type="text" id="txtUsername" name="txtUsername" class="form-control input_user" value="" placeholder="username">
                                <label id="errorUsername" style="color:red" />
                            </div>

                            <div class="input-group mb-2" >
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="fas fa-key"></i></span>
                                </div>
                                <input type="password" id="txtPassword" name="txtPassword" class="form-control input_pass" value="" placeholder="password">
                                <label id="errorPassword"  style="color:red" />
                            </div>

                        </form>
                    </div>
                    <div class="d-flex justify-content-center mt-3 login_container">
                        <button type="button" onclick="login()" name="button" class="btn login_btn">Login</button>
                    </div>
                    <script>
                        // Get the modal
                        var modal = document.getElementById('id01');

// When the user clicks anywhere outside of the modal, close it
                        window.onclick = function (event) {
                            if (event.target == modal) {
                                modal.style.display = "none";
                            }
                        }
                        function login()
                        {
                            var validate = true;
                            var username = document.getElementById('txtUsername').value;
                            var password = document.getElementById('txtPassword').value;
                            if (username.length == 0)
                            {
                                var errorUsername = document.getElementById('errorUsername');
                                errorUsername.innerText = 'Required'
                                validate = false;
                            }
                            if (password.length == 0)
                            {
                                var errorPassword = document.getElementById('errorPassword');
                                errorPassword.innerText = 'Required'
                                validate = false;
                            } else {

                            }
                            if (validate)
                            {
                                request({
                                    action: 'login',
                                    username: username,
                                    password: password,
                                }, function (res) {
                                    window.location.href = 'index.jsp'
                                });
                            }
                            console.log(username);
                        }
                    </script>
                    <div class="mt-4">
                        <div class="d-flex justify-content-center links">
                            Don't have an account? <button onclick="document.getElementById('id01').style.display = 'block'">sign up</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <!-- Button to open the modal login form -->


        <!-- The Modal -->
        <div id="id01" class="modal">
            <!-- Modal Content -->
            <form class="modal-content animate" action="/action_page.php">
                <div class="container">
                    <label for="uname"><b>Username</b></label>
                    <input type="text" placeholder="Enter Username" name="uname" required>
                    <label for="psw"><b>Password</b></label>
                    <input type="password" placeholder="Enter Password" name="psw" required>
                    <label for="rpsw"><b>Rewrite Password</b></label>
                    <input type="password" placeholder="Rewrite Password" name="rpsw" required>
                    <label for="adr"><b>Address</b></label>
                    <input type="text" placeholder="Enter address" name="adr" required>
                    <label for="gmail"><b>Gmail</b></label>
                    <input type="text" placeholder="Enter gmail" name="gmail" required>

                    <button type="submit">sign up</button>
                    
                </div>

                <div class="container" style="background-color:#f1f1f1">
                    <button type="button" onclick="document.getElementById('id01').style.display = 'none'" class="cancelbtn">Cancel</button>
                </div>
            </form>
        </div>
    </body>
</html>
