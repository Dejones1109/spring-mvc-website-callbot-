package vnteleco.com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vnteleco.com.entity.UserEntity;
import vnteleco.com.responsity.UserAccountRepository;
import vnteleco.com.service.UserAccountService;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	UserAccountRepository userRepository;

	@Override
	public UserEntity findAccountByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findAccountByUserName(userName);
	}

	@Override
	public UserEntity findAccountById(int userId) {
		// TODO Auto-generated method stub
		return userRepository.findAccountById(userId);
	}

	@Override
	public List<UserEntity> findAllUserAccount() {
		// TODO Auto-generated method stub
		return userRepository.findAllUserAccount();
	}

	@Override
	public boolean addUserEntity(UserEntity userEntity) {
		// TODO Auto-generated method stub
		return userRepository.addUserEntity(userEntity);
	}

}
