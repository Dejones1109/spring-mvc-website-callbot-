<%@ page contentType="text/html;charset=UTF-8"  language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
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
    <title>Conversation manager</title>
    
    <style type="text/css">
    	
	    .playAudio {
	        color: black;
	    }
	    .playAudio:hover {
	        color: black;
	        text-decoration: none;
	    }

    </style>
  </head>
  <body>
    <!-- Main Wrapper -->
    <div class="main-wrapper">
        <!-- content -->
        <div class="content main_content">
            <!-- sidebar group -->
            <div class="sidebar-group left-sidebar chat_sidebar">
                <!-- Chats sidebar -->
                <div id="chats" class="left-sidebar-wrap sidebar active">
                    
                   <%@ include file="header.jsp" %>

                </div>
                <!-- / Chats sidebar -->
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
                        <div class="mt-1">
                            <h4 class="mb-1">Chi tiết cuộc gọi</h4>
                            <small class="text-muted mb-2" style="font-size: 14px">Mã cuộc gọi: ${conversation.conversationId}</small><br>
                            <small class="text-muted mb-2" style="font-size: 14px">Số tổng đài: ${conversation.callCenter}</small><br>
                            <small class="text-muted mb-2" style="font-size: 14px">Số khách hàng: ${conversation.customer}</small><br>
                            <small class="text-muted mb-2" style="font-size: 14px">Gọi ra lúc: ${conversation.callAt}</small><br>
                            <small class="text-muted mb-2" style="font-size: 14px">Thời gian chờ: ${conversation.waitTime}</small><br>
                            <small class="text-muted mb-2" style="font-size: 14px">Thời gian cuộc gọi: ${conversation.callTime}</small><br>
                            <small class="text-muted mb-2" style="font-size: 14px">Trạng thái: ${conversation.status}</small><br>
                            <audio id="audio-player" autoplay="" controls="" title="Audio" style="width: 100%">
					            <source src="${conversation.audioUrl}" type="audio/mpeg">
					        </audio>
                        </div>
                        
                    </div>

                </div>
                <div class="chat-body">
                    <div class="messages">
                    
                    	<c:forEach items="${listOfContentDetail}" var="contentDetail">
			             	<c:if test="${contentDetail.type == 1}">
			             	
			             		<div class="chats">
		                            <div class="chat-avatar">
		                                <a href="#" class="playAudio" start_time="${contentDetail.secondInAudio}"><i class="fa fa-play-circle"></i></a>
		                            </div>
		                            <div class="chat-content">
		                                <div class="message-content">
		                                    ${contentDetail.text}
		                                </div>
		                                <div class="chat-time">
		                                    <div>
		                                        <div class="time">${contentDetail.time}</div>
		                                    </div>
		                                </div>
		                            </div>
		                            
		                        </div>
			             	
							</c:if>
							
							<c:if test="${contentDetail.type == 2}">
							
								<div class="chats chats-right">
		                        	<div class="chat-avatar">
		                                <a href="#" class="playAudio" start_time="${contentDetail.secondInAudio}"><i class="fa fa-play-circle"></i></a>
		                            </div>
		                            <div class="chat-content">
		                                <div class="message-content">
		                                    ${contentDetail.text}
		                                </div>
		                                <div class="chat-time">
		                                    <div>
		                                        <div class="time">${contentDetail.time} </div>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
							
							 	
							</c:if>
			             </c:forEach>
                    
                        
                        
                    </div>
                </div>
                
            </div>
                     
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
    <script src="template_website/assest/call_detail.js"></script>
    <script src="template_website/assest/change_password.js"></script>
	
  </body>
</html>
