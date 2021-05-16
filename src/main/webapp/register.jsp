<%-- 
    Document   : register
    Created on : Apr 30, 2021, 12:22:16 AM
    Author     : kanye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Register - CMS</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="assets/fonts/fontawesome5-overrides.min.css">
    <link rel="stylesheet" href="assets/css/styles.css">
</head>

<body class="bg-gradient-primary" style="background: var(--indigo);">
    <div class="container" id="register-container" style="height: 100%;">
        <div class="card shadow-lg o-hidden border-0 my-5">
            <div class="card-body p-0">
                <div class="row">
                    <div class="col-lg-5 d-none d-lg-flex">
                        <div class="flex-grow-1 bg-register-image" style="background-image: url(&quot;dogs/image2.jpeg&quot;);"></div>
                    </div>
                    <div class="col-lg-7">
                        <div class="p-5">
                            <div class="text-center">
                                <h4 class="text-dark mb-4">Create an Account!</h4>
                            </div>
                            <form class="user">
                                <hr>
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control form-control-user" type="text" id="firstName" placeholder="First Name" name="first_name" required=""></div>
                                    <div class="col-sm-6"><input class="form-control form-control-user" type="text" id="lastName" placeholder="Last Name" name="last_name" required=""></div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control form-control-user" type="email" id="inputEmail" aria-describedby="emailHelp" placeholder="Email Address" name="email" required=""></div>
                                    <div class="col-sm-6"><input class="form-control form-control-user" type="text" id="inputUsername" name="username" placeholder="Username" required="" minlength="5"></div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3 mb-sm-0"><label>Gender</label><select class="form-control" style="border-radius:32px;">
                                            <optgroup label="Gender" >
                                                <option name="gender" value="female" selected="">Male</option>
                                                <option name="gender" value="female">Female</option>
                                                <option name="gender" value="other">Other</option>
                                            </optgroup>
                                        </select></div>
                                    <div class="col-sm-6"><label>Date of Birth</label><input class="form-control form-control-user" id="inputDateAndTime" type="date" name="date_of_birth" style="border-radius:32px;"></div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3 mb-sm-0"><input class="form-control form-control-user" type="password" id="examplePasswordInput" placeholder="Password" name="password" required="" minlength="8" maxlength="20"></div>
                                    <div class="col-sm-6"><input class="form-control form-control-user" type="password" id="exampleRepeatPasswordInput" placeholder="Repeat Password" name="password_repeat" required="" minlength="8" maxlength="20"></div>
                                </div>
                                
                                <!-- JSP for password check -->
                                <%
                                    String message = (String) request.getAttribute("errorMessage");
                                    if(message != null) { %>
                                    <div class="form-group" style="text-align: center; color: red"><%= message %></div>
                                    <% }
                                %>
                                <!-- JSP End -->
                                
                                <button class="btn btn-primary btn-block text-white btn-user" type="submit" name="action" value="register" style="background: var(--purple);">Register Account</button>
                                <hr>
                            </form>
                            <div class="text-center"><a class="small" href="forgot-password.html" style="color: var(--purple);">Forgot Password?</a></div>
                            <div class="text-center"><a class="small" href="login.html" style="color: var(--purple);">Already have an account? Login!</a></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/chart.min.js"></script>
    <script src="assets/js/bs-init.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.js"></script>
    <script src="assets/js/theme.js"></script>
</body>

</html>