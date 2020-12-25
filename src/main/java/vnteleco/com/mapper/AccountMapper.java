package vnteleco.com.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import vnteleco.com.entity.RoleEntity;
import vnteleco.com.entity.UserEntity;
import vnteleco.com.entity.dto.AccountDto;
import vnteleco.com.enumjava.AccountStatus;


@Component
public class AccountMapper implements EntityDtoMapper<UserEntity, AccountDto>{
	
	@Override
	public AccountDto transform(UserEntity userEntity) {
				
		AccountDto accountDto = new AccountDto();
		accountDto.setId(userEntity.getId());
		accountDto.setUserName(userEntity.getUserName());
		
		Set<RoleEntity> roles = userEntity.getRoles();
		StringBuilder builder = new StringBuilder();
		for (RoleEntity roleEntity : roles) {
			builder.append(roleEntity.getRoleName()+" ");
		}
		accountDto.setRoleName(builder.toString());
		accountDto.setStatus(AccountStatus.ACCOUNT_STATUS_MAP.get(userEntity.getEnabled()));
		
		return accountDto;
	        
	}

	@Override
	public UserEntity transformReverse(AccountDto model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<AccountDto> transform(Collection<UserEntity> entities) {
		
		List<AccountDto> listOfAccountDto = new ArrayList<>();
		for (UserEntity userEntity : entities) {
			AccountDto accountDto = transform(userEntity);
			
			listOfAccountDto.add(accountDto);
		}
		
		return listOfAccountDto;
	}

	@Override
	public Collection<UserEntity> transformReverse(Collection<AccountDto> models) {
		// TODO Auto-generated method stub
		return null;
	}
}
