<%@ page contentType="text/html;charset=UTF-8"  language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Conversation manager</title>

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
  </head>
  <body>
    <div class="main-wrapper">
        <!-- content -->
        <div class="content main_content">
            <!-- sidebar group -->
            <div class="sidebar-group left-sidebar">
                <!-- Chats sidebar -->
                <div id="chats" class="left-sidebar-wrap sidebar active">
                    
                    <%@ include file="header.jsp" %>
                    
                    <div class="search_chat has-search">
                        <span class="fas fa-search form-control-feedback"></span>
                        <input class="form-control chat_input" id="search-contact" type="text" placeholder="">
                    </div>

                </div>
                <!-- /Chats sidebar -->
            </div>
            <!-- /Sidebar group -->

            <!-- Chat -->
            <div class="chat" id="middle">
                <div class="chat-header">
                    <div class="user-details">
                        <div class="d-lg-none ml-2">
                            <ul class="list-inline mt-2 mr-2">
                                <li class="list-inline-item">
                                    <a class="text-muted px-0 left_side" href="#" data-chat="open">
                                        <i class="fas fa-arrow-left"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <figure class="avatar ml-1">
                            <img src="template_website/img/carousel1.jpg" class="rounded-circle" alt="image">
                        </figure>
                        <div class="mt-1">
                            <h5 class="mb-1">Quản lý dịch vụ</h5>

                        </div>
                    </div>

                </div>
                <div class="chat-body">


                    <div class="sidebar-body" id="chatsidebar">
                        <ul class="user-list">
                        
                        	<c:if test="${not empty listOfCategory}">
                        		<c:forEach var="item" items="${listOfCategory}">
	                        		<li class="user-list-item">
	                        			<div class="avatar">
		                                    <img src="data:image/jpeg;base64,${item.avatar}" class="rounded-circle" alt="image">
		                                </div>
		                                
		                                <div class="users-list-body">
		                                    <div>
		                                        <h5><a class="nav-link active" href="callbot?category_id=${item.id}">${item.name} </a></h5>
		                                        <p></p>
		                                    </div>
		                                    
		                                </div>
	                        		</li>
	                        	</c:forEach>
								
							</c:if>	
                          
                        </ul>
                    </div>

                </div>

            </div>

            <!-- Right Sidebar -->
            
            <%@ include file="change_password.jsp" %>
        </div>
        <!-- /Content -->
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
    <script src="template_website/assest/change_password.js"></script>
  </body>
</html>
