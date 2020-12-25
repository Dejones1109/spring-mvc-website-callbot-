<%@ page contentType="text/html;charset=UTF-8"  language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="header">
	<div class="header-top">
		<div class="logo ml-2 mt-3">
			<a href="index-2.html"> <img src="template_website/img/logo.png"
				alt="" class="header_image">
			</a>
		</div>
		<ul class="header-action mt-4">
        	<li>
            	<a href="#" data-toggle="dropdown">
                 	<i class="fas fa-ellipsis-h ellipse_header"></i>
             	</a>
              	<div class="dropdown-menu dropdown-menu-right header_drop_icon">
              		<sec:authorize access="hasRole('ROLE_ADMIN')">
					 	<a class="dropdown-item" href="${pageContext.request.contextPath}/user"> Quản lý tài khoản </a>
					</sec:authorize>
              		<a class="dropdown-item" data-toggle="modal" data-target="#settings_modal">Thay đổi mật khẩu</a>
             		<a href="${pageContext.request.contextPath}/logout" class="dropdown-item">Đăng xuất</a>
          		</div>
         	</li>
        </ul>
	</div>

</div>

