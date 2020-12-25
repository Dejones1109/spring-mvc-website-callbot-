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
                    
                    
                    <div class="sidebar-body" id="chatsidebar">
                        <ul class="user-list">
                        
                        	                       

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
                            <h5 class="mb-1">Quản lý tài khoản</h5>
                        </div>
                    </div>
                    <div class="chat-options">
                        <ul class="list-inline">
                            <li class="list-inline-item list-group-item-primary" data-toggle="tooltip" title="" data-original-title="Creat account">
                                <a href="javascript:void(0)" class="btn btn-outline-light" data-toggle="modal" data-target="#createAccountModal">
                                    <i class="fas fa-plus"></i>
                                </a>
                            </li>

                     
                        </ul>
                    </div>
                </div>

                <div class="chat-body">
                    <div class="missed-call-widget mt-0">
                        <div class="call-log-header">
                            <div class="row">
                                <div class="col">
                                    <h4>Danh sách tài khoản</h4>
                                </div>
                                
                            </div>
                        </div>
                    
                        <table id="accountTable" class="table">
                            <thead class="thead-dark">
                                <tr>
									<th style="text-align: center;">Tên đăng nhập</th>
					                <th style="text-align: center;">Vai trò</th>
					                <th style="text-align: center;">Trạng thái</th>
					                <th style="text-align: center;">Thao tác</th>	
					                <th style="display: none">Password</th>					               
                                </tr>
                            </thead>
                            <tbody>
                               <c:forEach items="${listOfAccountDto}" var="accountDto">
									<tr>
										<td>${accountDto.userName}</td>
										<td>${accountDto.roleName}</td>
										<td>${accountDto.status}</td>
										<td>
											<a type="button" class="edit" data-toggle="modal" data-target="#editAccountModal"><i class="fa fa-edit"></i> Sửa</a>
											&nbsp&nbsp&nbsp 
											<a type="button" class="reset" data-toggle="modal" data-target="#resetPasswordModal"><i class="fa fa-refresh"></i> Đặt lại mật khẩu</a>
										</td>
										<td class="HiddentAccountId" style="display:none">${accountDto.id}</td>
									</tr>
								</c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- /Chat -->

           <div class="modal" id="createAccountModal" tabindex="-1" role="dialog">
		   		<form method="post" action="${pageContext.request.contextPath}/create_account" id="createAccountForm">
				  	<div class="modal-dialog" role="document">
				    	<div class="modal-content">
				      		<div class="modal-header">
				        		<h5 class="modal-title">Tạo tài khoản mới</h5>
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				      		</div>
					      	<div class="modal-body">
				                		                
					      		<div class="form-group">
									<label for="text">Tên đăng nhập</label> 
									<input type="text" class="form-control" name="user_name" id="user_name" required pattern=".{3,}">
									<div class="invalid-feedback">
										  <span>Tên đăng nhập không được để trống</span><br>
										  <span>Tên đăng nhập phải lớn hơn 3 ký tự</span>
									</div>
								</div>
								
								<div class="form-group">
									<label for="text">Mật khẩu</label> 
									<input type="password" class="form-control" id="password" name="password" required pattern=".{5,}">
									<div class="invalid-feedback">
										  <span>Mật khẩu không được để trống</span><br>
										  <span>Mật khẩu phải lớn hơn 5 ký tự</span>
									</div>
								</div>
								
								<div class="form-group">
									<label for="text">Xác nhận mật khẩu</label> 
									<input type="password" class="form-control" id="confirm_password" name="confirm_password"  required pattern=".{5,}">
									<div class="invalid-feedback">
										  <span>Xác nhận mật khẩu không được để trống</span><br>
										  <span>Xác nhận mật khẩu phải lớn hơn 5 ký tự</span>
									</div>
								</div>
								
								<div class="form-group">
									<label for="text">Trạng thái</label> 
									<select class="form-control" name="role">
										<c:forEach var="item" items="${listOfRoleEntity}">
											<option value="${item.id}">${item.roleName}</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="form-group">
									<label for="text">Trạng thái</label> 
									<select class="form-control" name="status">
										<option value="0">Vô hiệu hóa</option>
										<option value="1">Kích hoạt</option>
									</select>
								</div>
		
					        	
					      	</div>
					      	<div class="modal-footer">
					        	<button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
					        	<button type="button" id="btnCreate" class="btn btn-primary">Tạo mới</button>
					      	</div>
				    	</div>
				  	</div>
				  </form>
			</div>
			
			<div class="modal" id="editAccountModal" tabindex="-1" role="dialog">
		   		<form method="post" action="${pageContext.request.contextPath}/edit_account" id="editAccountForm">
				  	<div class="modal-dialog" role="document">
				    	<div class="modal-content">
				      		<div class="modal-header">
				        		<h5 class="modal-title">Sửa tài khoản</h5>
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				      		</div>
					      	<div class="modal-body">
				                
				                <input type="hidden" name="edit_id" id="edit_id">
				                
					      		<div class="form-group">
									<label for="text">Tên đăng nhập</label> 
									<input type="text" class="form-control" name="edit_user_name" id="edit_user_name" required pattern=".{3,}">
									<div class="invalid-feedback">
										  <span>Tên đăng nhập không được để trống</span><br>
										  <span>Tên đăng nhập phải lớn hơn 3 ký tự</span>
									</div>
								</div>						
								
								<div class="form-group">
									<label for="text">Trạng thái</label> 
									<select class="form-control" name="edit_role" id="edit_role">
										<c:forEach var="item" items="${listOfRoleEntity}">
											<option value="${item.id}">${item.roleName}</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="form-group">
									<label for="text">Trạng thái</label> 
									<select class="form-control" name="edit_status" id="edit_status">
										<option value="0">Vô hiệu hóa</option>
										<option value="1">Kích hoạt</option>
									</select>
								</div>
		
					        	
					      	</div>
					      	<div class="modal-footer">
					        	<button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
					        	<button type="button" id="btnEdit" class="btn btn-primary">Sửa</button>
					      	</div>
				    	</div>
				  	</div>
				  </form>
			</div>
			
			<div class="modal" id="resetPasswordModal" tabindex="-1" role="dialog">
		   		<form method="post" action="${pageContext.request.contextPath}/reset_password" id="resetPasswordForm">
				  	<div class="modal-dialog" role="document">
				    	<div class="modal-content">
				      		<div class="modal-header">
				        		<h5 class="modal-title">Đặt lại mật khẩu</h5>
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				      		</div>
					      	<div class="modal-body">
				                
				                <input type="hidden" name="reset_id" id="reset_id">
				                
					      		<p>Bạn chắc chắn muốn đặt lại mật khẩu cho tài khoản.</p>
		
					        	
					      	</div>
					      	<div class="modal-footer">
					        	<button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
					        	<button type="button" id="btnResetPassword" class="btn btn-primary">Đồng ý</button>
					      	</div>
				    	</div>
				  	</div>
				  </form>
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
	<script src="template_website/assest/account.js"></script>
	<script src="template_website/assest/change_password.js"></script>
  </body>
</html>
