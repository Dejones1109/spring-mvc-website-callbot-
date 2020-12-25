package vnteleco.com.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vnteleco.com.entity.RoleEntity;
import vnteleco.com.entity.UserEntity;
import vnteleco.com.service.UserAccountService;

@Service
public class MyDBAuthenticationService implements UserDetailsService {

	@Autowired
	private UserAccountService userService;

	List<String> roleName = new ArrayList<>();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userService.findAccountByUserName(username);

		if (userEntity == null) {
			throw new UsernameNotFoundException("User "+ username + " was not found in the database");
		}
		
		//set role
		Set<RoleEntity> listOfRole = userEntity.getRoles();
		
		List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
        if(listOfRole!= null && listOfRole.size() > 0)  {
        	for (RoleEntity role : listOfRole) {
        		GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                grantList.add(authority);
           }      
		        	
        }      
		
        boolean enabledCheck = true;
		if (userEntity.getEnabled() == 0) {
			enabledCheck= false;
		}
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		UserDetails userDetails = (UserDetails) new User(userEntity.getUserName(),
				userEntity.getPassword(), enabledCheck, accountNonExpired,
				credentialsNonExpired, accountNonLocked, grantList);

		return userDetails;
	}
	

}
