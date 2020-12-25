<%@ page contentType="text/html;charset=UTF-8"  language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
   <!-- Favicon -->
    <link rel="icon" href="template_website/img/favicon.ico">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="template_website/css/bootstrap.min.css">
    <link href="template_website/css/dataTables.bootstrap.min.css" rel="stylesheet">

    <!-- Fontawesome CSS -->
    <link rel="stylesheet" href="template_website/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="template_website/plugins/fontawesome/css/all.min.css">

    <!-- Custom scroll CSS -->
    <link rel="stylesheet" href="template_website/plugins/mcustomscroll/jquery.mCustomScrollbar.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">

    <!-- App styles -->
    <link rel="stylesheet" href="template_website/css/app.css">
    
    <title>Conversation manager</title>
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
                    <div class="sidebar-body" id="chatsidebar">
                        <ul class="user-list">
                        
                        	<c:if test="${not empty listOfCallBot}">
                        		<c:forEach var="item" items="${listOfCallBot}">
	                        		<li class="user-list-item">
	                        			<c:if test="${callbot.id == item.id}">
										    <div class="avatar avatar-online">
			                                    <img src="data:image/jpeg;base64,${item.avatar}" class="rounded-circle" alt="image">
			                                </div>
			                                <div class="users-list-body">
			                                    <div>
			                                        <h5><a href="conversation?callbot_id=${item.id}">${item.name}</a></h5>
			                                    </div>
			                                   
			                                </div>
										</c:if>
										<c:if test="${callbot.id != item.id}">
										    <div class="avatar avatar-away">
			                                    <img src="data:image/jpeg;base64,${item.avatar}" class="rounded-circle" alt="image">
			                                </div>
			                                <div class="users-list-body">
			                                    <div>
			                                        <h5><a href="conversation?callbot_id=${item.id}">${item.name}</a></h5>
			                                    </div>
			                                   
			                                </div>
										</c:if>
		                                
		                            </li>
	                            </c:forEach>
                        	
							</c:if>                        

                        </ul>
                    </div>
                </div>
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
                            <h5 class="mb-1">Quản lý cuộc gọi</h5>
                            <small class="text-muted mb-2" style="font-size: 14px">Dịch vụ: ${callbot.name}</small><br>
                            <small class="text-muted mb-2" style="font-size: 14px">Loại: Call Out </small><br>
                        </div>
                    </div>
                    <div class="chat-options">
                        <ul class="list-inline">
                            <li class="list-inline-item list-group-item-primary" data-toggle="tooltip" title="" data-original-title="Creat call">
                                <a href="javascript:void(0)" class="btn btn-outline-light" data-toggle="modal" data-target="#callModal">
                                    <i class="fas fa-phone-alt voice_chat_phone"></i>
                                </a>
                            </li>

                            <li class="list-inline-item list-group-item-success" data-toggle="tooltip" title="tải tệp lên" data-original-title="Tải lên">
                                <a href="javascript:void(0)" class="btn btn-outline-light" data-toggle="modal" data-target="#uploadFileModal">
                                    <i class="fas fa-paperclip"></i>
                                </a>
                            </li>
                            <li class="list-inline-item list-group-item-warning" data-toggle="tooltip" title="tải file về" data-original-title="Tải file về">
                                <a href="download/${callbot.fileTemplateName}" class="btn btn-outline-light">
                                    <i class="fas fa-file-download"></i>
                                </a>
                            </li>
                            <li class="list-inline-item list-group-item-secondary">
                                <a class="btn btn-outline-light" href="#" data-toggle="dropdown">
                                    <i class="fas fa-ellipsis-h"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right">
                                	<form method="get" action="${pageContext.request.contextPath}/export" id="exportFileForm" autocomplete="off" enctype="multipart/form-data">
                                    	<input type="hidden" value="${callbot.id}" name="exportFileCallbotID" id="exportFileCallbotID"/> 
                                    	<input type="hidden" name="exportFileMsisdn" id="exportFileMsisdn"/> 
                                    	<input type="hidden" name="exportFileStartDate" id="exportFileStartDate"/> 
                                    	<input type="hidden" name="exportFileEndDate" id="exportFileEndDate"/> 
                                    	<a href="javascript:void(0)" id="export_file" class="dropdown-item dream_profile_menu">Xuất file báo cáo</a>
                                    </form>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="chat-body">
                    <div class="missed-call-widget mt-0">
                        <div class="call-log-header">
                            <div class="row">
                                <div class="col">
                                    <h4>Danh sách cuộc gọi</h4>
                                </div>
                                
                            </div>
                        </div>
                        
                        <form method="get" action="${pageContext.request.contextPath}/search_conversation" id="searchConversationForm" autocomplete="off" enctype="multipart/form-data">
	                        <div class="row">
		                    	<div class="col-md-11">
		                       		<div class="row">
		                       			
		                       			<input type="hidden" value="${callbot.id}" name="searchCallbotID" id="searchCallbotID"/> 
		                       			
		                       			<div class="col-md-4">
											<div class="input-group">
											  	<div class="input-group-prepend">
											    	<span class="input-group-text" id="basic-addon1">Số điện thoại</span>
											  	</div>
											  	<input type="text" class="form-control" aria-describedby="basic-addon1" id="msisdn" name="msisdn" pattern=".{0,15}" onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
												
											</div>
				                      	</div>
	
		                             	<div class="col-md-4">
											<div class="input-group">
											  	<div class="input-group-prepend">
											    	<span class="input-group-text" id="basic-addon2">Ngày bắt đầu</span>
											  	</div>
											  	<input type="date" class="form-control" aria-describedby="basic-addon2" id="startDate" name="startDate">
											</div>
				                       	</div>
				                                                
				                    	<div class="col-md-4">
											<div class="input-group">
											  	<div class="input-group-prepend">
											    	<span class="input-group-text" id="basic-addon3">Ngày kết thúc</span>
											  	</div>
											  	<input type="date" class="form-control" aria-describedby="basic-addon3" id="endDate" name="endDate">
											</div>
				                      	</div>
		                        	</div>
		                    	</div>
		                     	<div class="col-md-1">
		                       		<div class="btn-group pull-right">
				                   		<button id="btnSearch" type="button" class="btn btn-primary"> Search
				                      		<i style="padding-left: 2px" class="fa fa-search"></i>
				                   		</button>
				                	</div>
		                      	</div>
		                                               
		                  	</div>
		                </form>
                        
                        <table id="conversationTable" class="table">
                            <thead class="thead-dark">
                                <tr>
									<th style="text-align: center;">Thời gian tạo</th>
					                <th style="text-align: center;">Mã cuộc gọi</th>
					                <th style="text-align: center;">Điện thoại khách hàng</th>
					                <th style="text-align: center;">Trạng thái</th>						               
                                </tr>
                            </thead>
                            <tbody>
                               <c:forEach items="${listOfConversationDto}" var="conversationDto">
									<tr>
										<td>${conversationDto.createTime}</td>
										<td><a href="detail?conversation_id=${conversationDto.conversationId}">${conversationDto.conversationId}</a></td>
										<td>${conversationDto.customer}</td>
										<td>${conversationDto.status}</td>
									</tr>
								</c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- /Chat -->

            <!-- Upload Documents -->
            <div id="uploadFileModal" class="modal fade" role="dialog">
            	<form method="post" action="${pageContext.request.contextPath}/upload_file" id="uploadFileForm" autocomplete="off"  enctype="multipart/form-data">
	                <div class="modal-dialog modal-md modal-dialog-centered">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <h4 class="modal-title">Nhập file danh sách cuộc gọi để tạo chiến dịch </h4>
	                            <button type="button" class="close" data-dismiss="modal">&times;</button>
	                        </div>
	                       <div class="modal-body">
							<input type="hidden" name="callbotId" value="${callbot.id}">
							<div class="form-group" >
								<label for="exampleInputFile">Upload File (Only accept excel file) </label> 
								<input type="file" id="exampleInputFile" name="exampleInputFile" required accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
								<div class="invalid-feedback">
									<span>File upload không được để trống</span><br>
								</div>
								
							</div>
						
						</div>
	
						<div class="modal-footer">
							<button type="button" class="btn btn-white" data-dismiss="modal">Đóng</button>
							<button type="button" id="btnUpload" class="btn btn-primary">Tải lên</button>
						</div>
	                    </div>
	                </div>
	       		</form>
            </div>
            <!-- /Upload Documents -->

            <!-- create call -->
            <div class="modal fade" id="callModal">
            	<form method="post" action="${pageContext.request.contextPath}/call" id="createAndCallForm" class="needs-validation" novalidate>
	                <div class="modal-dialog modal-dialog-centered">
	                    <div class="modal-content">
	                        <div class="modal-header">
	                            <h5 class="modal-title">
	                                Tạo cuộc gọi
	                            </h5>
	                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				                    <i class="fas fa-times close_icon"></i>
				                </button>
	                        </div>
	                        <div class="modal-body">
					      		<input type="hidden" name="callbotId" value="${callbot.id}">
					      		<div class="form-group">
									<label for="text">Số điện thoại</label> 
									<input type="text" class="form-control" name="customer_phone" required pattern=".{0,15}" onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
									<div class="invalid-feedback">
										<span>Số điện thoại không được để trống</span><br>
										<span>Số điện thoại phải bé hơn 15 ký tự </span>
									</div>
								</div>
								
								<!-- <div class="form-group">
									<label for="text">Khu vực</label> 
									<select class="form-control" name="customer_area">
								      	<option value="NORTH">Phía Bắc</option>
								      	<option value="CENTRAL">Phía Trung </option>
								      	<option value="SOUTH">Phía Nam</option>
								    </select>
								</div>
								 -->
								 
					        	<c:forEach items="${listOfField}" var="field">
					        		<div class="form-group">
					        			<label for="text">${field.vnName}</label> 
					        			<c:choose>
										    <c:when test="${field.type=='1'}">
										    	<input type="text" class="form-control" name="${field.enName}" required pattern=".{${field.minLength},${field.maxLength}}">
										    	<div class="invalid-feedback">
										    		<span>${field.vnName} không được để trống</span><br>
										    		<span>${field.vnName} phải ít hơn ${field.maxLength} ký tự</span>
										    	</div>
										    </c:when>    
										    <c:when test="${field.type=='2'}">
										       	<input type="number" class="form-control" name="${field.enName}" required min="${field.minValue}" max="${field.maxValue}">
										       	<div class="invalid-feedback">
										       		<span>${field.vnName} không được để trống</span><br>
										    		<span>${field.vnName} phải bé hơn ${field.maxValue} </span>
										       	</div>
										    </c:when>
										    <c:when test="${field.type=='3'}">
										    	<select class="form-control" name="${field.enName}">
										    		<c:forEach var="item" items="${fn:split(field.defaultValue,',')}">
										    			<c:set var="item_detail" value="${fn:split(item, '/')}" />
														<option value="${item_detail[0]}">${item_detail[1]}</option>
													</c:forEach>
										    	</select>
										    </c:when> 
										</c:choose>
										
										
									</div>
					        	</c:forEach>
					      	</div>
					      	<div class="modal-footer">
					        	<button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
					        	<button type="button" id="btnCall" class="btn btn-primary">Gọi</button>
					      	</div>
	                    </div>
	                </div>
                </form>
            </div>
            <!-- /Chat New Modal -->
            
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

    <script src="template_website/js/jquery.dataTables.min.js"></script>
	<script src="template_website/js/dataTables.bootstrap.min.js"></script>
	<script src="template_website/js/bootstrapValidator.js"></script>
	<script src="template_website/js/moment.min.js"></script>

    <!-- Custom Scroll JS -->
    <script src="template_website/js/jquery.nicescroll.min.js"></script>
    <script src="template_website/plugins/mcustomscroll/jquery.mCustomScrollbar.js"></script>

    <!-- Custom JS -->
    <script src="template_website/js/script.js"></script>
    <script src="template_website/assest/conversation.js"></script>
	<script src="template_website/assest/change_password.js"></script>
  </body>
</html>
