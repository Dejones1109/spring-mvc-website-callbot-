package vnteleco.com.controller;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import vnteleco.com.entity.RoleEntity;
import vnteleco.com.entity.UserEntity;
import vnteleco.com.entity.dto.AccountDto;
import vnteleco.com.entity.response.ResponseDto;
import vnteleco.com.mapper.AccountMapper;
import vnteleco.com.service.RoleService;
import vnteleco.com.service.UserAccountService;
import vnteleco.com.util.Constant;

@Controller
@Transactional
@EnableWebMvc
@PropertySource("classpath:config.properties")
public class AccountController {
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private RoleService roleService;
	
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

	@RequestMapping(value = { "/user" }, method = RequestMethod.GET)
	public String getAllUser(Model model, HttpServletRequest request) {
				
		List<AccountDto> listOfAccountDto = (List<AccountDto>) accountMapper.transform(userAccountService.findAllUserAccount());
		List<RoleEntity> listOfRoleEntity = roleService.findAllRoleEntity();
		
		model.addAttribute("listOfAccountDto", listOfAccountDto);
		model.addAttribute("listOfRoleEntity", listOfRoleEntity);
		return "account";
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/create_account")
	public @ResponseBody String createAccount(HttpServletRequest request) {
					
		ResponseDto<AccountDto> responseDto = new ResponseDto<AccountDto>();
		
		//set default
		Gson gson = new Gson();
		
		try {
			
			String userName = request.getParameter("user_name");
			String password = request.getParameter("password");
			int roleId = Integer.valueOf(request.getParameter("role"));
			int status = Integer.valueOf(request.getParameter("status"));
					
			UserEntity userEntity = userAccountService.findAccountByUserName(userName);
			if (userEntity != null) {
				responseDto.setData(null);
				responseDto.setMessage("Tên đăng nhập đã tồn tại");
				responseDto.setStatus(Constant.FAIL);
				
				return gson.toJson(responseDto);
			}
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			userEntity = new UserEntity();
			userEntity.setUserName(userName);
			userEntity.setPassword(passwordEncoder.encode(password));
			userEntity.setCreateAt(new Timestamp(System.currentTimeMillis()));
			userEntity.setUpdateAt(new Timestamp(System.currentTimeMillis()));
			userEntity.setEnabled(status);
	    	
			//set roles
			RoleEntity roleEntity = roleService.findRoleById(roleId);
			Set<RoleEntity> listOfRoleEntity =  new HashSet<>();
			listOfRoleEntity.add(roleEntity);
						
			userEntity.setRoles(listOfRoleEntity);
	    				    	
	    	AccountDto accountDto = new AccountDto();
	    	if (userAccountService.addUserEntity(userEntity)) {
	    		accountDto = accountMapper.transform(userEntity);
			}
	    	
	    	responseDto.setData(accountDto);
	    	responseDto.setMessage("Tạo tài khoản thành công");
			responseDto.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			responseDto.setData(null);
			responseDto.setMessage("Hệ thống có lỗi!");
			responseDto.setStatus(Constant.FAIL);
		} finally {
			return gson.toJson(responseDto);
		}	
		
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/edit_account")
	public @ResponseBody String editAccount(HttpServletRequest request) {
					
		ResponseDto<AccountDto> responseDto = new ResponseDto<AccountDto>();
		
		//set default
		Gson gson = new Gson();
		try {
			
			int userId = Integer.valueOf(request.getParameter("edit_id"));
			String userName = request.getParameter("edit_user_name");
			int roleId = Integer.valueOf(request.getParameter("edit_role"));
			int status = Integer.valueOf(request.getParameter("edit_status"));
					
			UserEntity userEntity = userAccountService.findAccountByUserName(userName);
			if (userEntity != null && userEntity.getId() != userId) {
				responseDto.setData(null);
				responseDto.setMessage("Tên đăng nhập đã tồn tại");
				responseDto.setStatus(Constant.FAIL);
				
				return gson.toJson(responseDto);
			}
			
			userEntity.setUserName(userName);
			userEntity.setUpdateAt(new Timestamp(System.currentTimeMillis()));
			userEntity.setEnabled(status);
	    	
			//set roles
			RoleEntity roleEntity = roleService.findRoleById(roleId);
			Set<RoleEntity> listOfRoleEntity =  new HashSet<>();
			listOfRoleEntity.add(roleEntity);
						
			userEntity.setRoles(listOfRoleEntity);
	    				    	
	    	AccountDto accountDto = new AccountDto();
	    	if (userAccountService.addUserEntity(userEntity)) {
	    		accountDto = accountMapper.transform(userEntity);
			}
	    	
	    	responseDto.setData(accountDto);
	    	responseDto.setMessage("Sửa tài khoản thành công");
			responseDto.setStatus(Constant.SUCCESS);
		} catch (Exception e) {
			responseDto.setData(null);
			responseDto.setMessage("Hệ thống có lỗi!");
			responseDto.setStatus(Constant.FAIL);
		} finally {
			return gson.toJson(responseDto);
		}	
		
	}
	
	@SuppressWarnings("finally")
	@RequestMapping(value = "/reset_password")
	public @ResponseBody String resetPassword(HttpServletRequest request) {
					
		ResponseDto<AccountDto> responseDto = new ResponseDto<AccountDto>();
		
		//set default
		Gson gson = new Gson();
		try {
			
			int userId = Integer.valueOf(request.getParameter("reset_id"));
					
			UserEntity userEntity = userAccountService.findAccountById(userId);
			if (userEntity == null) {
				responseDto.setData(null);
				responseDto.setMessage("Tài khoản không tồn tại");
				responseDto.setStatus(Constant.FAIL);
				
				return gson.toJson(responseDto);
			}
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			userEntity.setPassword(passwordEncoder.encode(resetPassord));
			userEntity.setUpdateAt(new Timestamp(System.currentTimeMillis()));
	    
	    	responseDto.setData(null);
	    	responseDto.setMessage("Cấp lại mật khẩu thành công");
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


