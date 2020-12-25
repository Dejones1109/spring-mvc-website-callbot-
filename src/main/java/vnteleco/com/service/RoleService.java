package vnteleco.com.service;

import java.util.List;

import vnteleco.com.entity.RoleEntity;

public interface RoleService {
	
	public RoleEntity findRoleById(int roleId);
	public List<RoleEntity> findAllRoleEntity();
}
