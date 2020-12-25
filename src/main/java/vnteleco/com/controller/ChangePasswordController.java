package vnteleco.com.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;

import vnteleco.com.entity.UserEntity;
import vnteleco.com.entity.dto.AccountDto;
import vnteleco.com.entity.response.ResponseDto;
import vnteleco.com.mapper.AccountMapper;
import vnteleco.com.service.UserAccountService;
import vnteleco.com.util.Constant;

@Controller
@Transactional
@EnableWebMvc
@PropertySource("classpath:config.properties")
public class ChangePasswordController {
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
    AccountMapper accountMapper;
	
	@Value("${reset_passord}")
    private String resetPassord;
	
	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);
	}

	@RequestMapping(value = { "/get_change_password" }, method = RequestMethod.GET)
	public String getAllUser(Model model, HttpServletRequest request) {
				
		return "change_password";
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/change_password")
	public @ResponseBody String changePassword(HttpServletRequest request) {
					
		ResponseDto<AccountDto> responseDto = new ResponseDto<AccountDto>();
		
		//set default
		Gson gson = new Gson();
		try {
			
			String oldPassword = request.getParameter("old_password");
			String newPassword = request.getParameter("new_password");
			String confirmNewPassword = request.getParameter("confirm_new_password");
				
			//get user
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			
			UserEntity userEntity = userAccountService.findAccountByUserName(username);
			if (userEntity == null) {
				responseDto.setData(null);
				responseDto.setMessage("Tài khoản không tồn tại");
				responseDto.setStatus(Constant.FAIL);
				
				return gson.toJson(responseDto);
			}
			
			//compare password
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
				responseDto.setData(null);
				responseDto.setMessage("Mật khẩu cũ không trùng khớp");
				responseDto.setStatus(Constant.FAIL);
				
				return gson.toJson(responseDto);
			}
			
			if (!newPassword.equals("confirmNewPassword")) {
				responseDto.setData(null);
				responseDto.setMessage("Mật khẩu mới không trùng với xác nhận mật khẩu");
				responseDto.setStatus(Constant.FAIL);
				
				return gson.toJson(responseDto);
			}
			
			userEntity.setPassword(passwordEncoder.encode(newPassword));
			userEntity.setUpdateAt(new Timestamp(System.currentTimeMillis()));
	    
	    	responseDto.setData(null);
	    	responseDto.setMessage("Thay đổi mật khẩu thành công");
			responseDto.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			responseDto.setData(null);
			responseDto.setMessage("Hệ thống có lỗi!");
			responseDto.setStatus(Constant.FAIL);
		} finally {
			return gson.toJson(responseDto);
		}	
		
		
	}
	
	
	
}


