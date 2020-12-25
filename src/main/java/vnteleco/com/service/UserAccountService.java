package vnteleco.com.service;

import java.util.List;

import vnteleco.com.entity.UserEntity;

public interface UserAccountService {
	public boolean addUserEntity(UserEntity userEntity);
	public UserEntity findAccountByUserName(String userName);
	public UserEntity findAccountById(int userId);
	public List<UserEntity> findAllUserAccount();
}
