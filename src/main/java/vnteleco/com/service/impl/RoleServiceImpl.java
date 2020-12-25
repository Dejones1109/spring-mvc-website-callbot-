package vnteleco.com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vnteleco.com.entity.RoleEntity;
import vnteleco.com.responsity.RoleRepository;
import vnteleco.com.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<RoleEntity> findAllRoleEntity() {
		// TODO Auto-generated method stub
		return roleRepository.findAllRole();
	}

	@Override
	public RoleEntity findRoleById(int roleId) {
		// TODO Auto-generated method stub
		return roleRepository.findRoleById(roleId);
	}

}
