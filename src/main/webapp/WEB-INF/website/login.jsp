<%@ page contentType="text/html;charset=UTF-8"  language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	<title>CallBot</title>
	<!-- Meta tag Keywords -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="Online Login Form Responsive Widget,Login form widgets, Sign up Web forms , Login signup Responsive web form,Flat Pricing table,Flat Drop downs,Registration Forms,News letter Forms,Elements" />
	
	<title>Call-bot</title>

    <!-- Favicon -->
    <link rel="icon" href="template_website/img/favicon.ico">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="template_website/css/bootstrap.min.css">

    <!-- Fontawesome CSS -->
    <link rel="stylesheet" href="template_website/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="template_website/plugins/fontawesome/css/all.min.css">

    <!-- Custom scroll CSS -->
    <link rel="stylesheet" href="template_website/plugins/mcustomscroll/jquery.mCustomScrollbar.css">

    <!-- App styles -->
    <link rel="stylesheet" href="template_website/css/app.css">
	
	<style type="text/css">
		.error{
			color: red;
		}
	</style>
	
</head>
<body class="account-page">

    <!-- Main Wrapper -->
    <div class="main-wrapper">
        <!-- Page Content -->
        <div class="content align-items-center">
            <div class="container-fluid">

                <div class="row">
                    <div class="col-md-8 offset-md-2">

                        <!-- Login Tab Content -->
                        <div class="account-content">
                            <div class="row align-items-center justify-content-center">
                                <div class="col-md-12 col-lg-6 login-right">
                                    <div class="login-header">

                                        <a href="index-2.html">
                                            <img src="template_website/img/logo.png" alt="" class="header_image">
                                        </a>
                                    </div>
                                    <form method="post" action="${pageContext.request.contextPath}/j_spring_security_check" id="loginForm" autocomplete="off">
                                        <div class="form-group">
                                            <label>Tài khoản</label>
                                            <input placeholder="Nhập tài khoản" name="userName"  id="userName" class="form-control form-control-lg group_formcontrol" type="text"> 
                                        </div>
                                        <div class="form-group">
                                            <label for="new-chat-topic">Mật khẩu</label>
                                            <input placeholder="Password" name="password" id="password" class="form-control form-control-lg group_formcontrol"  type="password" >
                                        </div>
                                        
                                        <c:if test="${param.error == 'true'}">
											<div class="error">
												<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
											</div>
										</c:if>
                                        
                                        <div class="pt-1">
                                            <div class="text-center">
                                                <button class="btn newgroup_create btn-block d-block w-100" type="submit">Đăng nhập</button>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="login-or">
                                        <span class="or-line"></span>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                        <!-- /Login Tab Content -->
                    </div>
                </div>
            </div>
        </div>
        <!-- /Page Content -->
    </div>
    <!-- /Main Wrapper -->

    <!-- jQuery -->
    <script src="template_website/js/jquery-3.4.1.min.js"></script>

    <!-- Bootstrap Core JS -->
    <script src="template_website/js/popper.min.js"></script>
    <script src="template_website/js/bootstrap.min.js"></script>

    <!-- Custom Scroll JS -->
    <script src="template_website/js/jquery.nicescroll.min.js"></script>
    <script src="template_website/plugins/mcustomscroll/jquery.mCustomScrollbar.js"></script>

    <!-- Custom JS -->
    <script src="template_website/js/script.js"></script>

</body>
</html>