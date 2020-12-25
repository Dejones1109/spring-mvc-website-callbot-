<%@ page contentType="text/html;charset=UTF-8"  language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="modal fade" id="settings_modal">
	<div class="modal-dialog modal-dialog-centered">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Thay đổi mật khẩu</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"> <i class="fas fa-times close_icon"></i>
				</button>
			</div>
			<div class="modal-body">
				<form method="post" action="${pageContext.request.contextPath}/change_password" id="changePasswordForm">
					<div class="mt-4">
						<label for="profile-name">Mật khẩu cũ</label> 
						<input class="form-control form-control-lg group_formcontrol"  type="password" id="old_password" name="old_password" placeholder="Current Password" required pattern=".{5,}">
						<div class="invalid-feedback">
							<span>Mật khẩu cũ không được để trống</span><br>
							<span>Mật khẩu cũ phải lớn hơn 5 ký tự</span>
						</div>
					</div>
					<div class="mt-4">
						<label for="profile-name">Mật khẩu mới</label> 
						<input class="form-control form-control-lg group_formcontrol" type="password"  id="new_password" name="new_password0" placeholder="New Password" required pattern=".{5,}">
						<div class="invalid-feedback">
							<span>Mật khẩu mới không được để trống</span><br>
							<span>Mật khẩu mới phải lớn hơn 5 ký tự</span>
						</div>
					</div>
					<div class="mt-4">
						<label for="profile-name">Xác nhận mật khẩu</label> 
						<input class="form-control form-control-lg group_formcontrol" type="password"  id="confirm_new_password" name="confirm_new_password" placeholder="Verify Password" required pattern=".{5,}">
						<div class="invalid-feedback">
							<span>Xác nhận mật khẩu không được để trống</span><br>
							<span>Xác nhận mật khẩu phải lớn hơn 5 ký tự</span>
						</div>
					</div>
					<div class="mt-3 text-center">
						<button class="btn btn-block newgroup_create mb-0" type="button" id="btnChangePassword">Thay đổi</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>