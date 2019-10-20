package com.hengyue.service.impl;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengyue.entity.UserRole;
import com.hengyue.respository.UserRoleRepository;
import com.hengyue.service.UserRoleService;

/**
 * 用户角色关联接口实现类
 * @author 章家宝
 *
 */
@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService{

	@Resource
	private UserRoleRepository userRoleRepository;
	@Override
	public void deleteByUserId(Integer userId) {
		userRoleRepository.deleteByUserId(userId);
	}
	@Override
	@RequiresPermissions(value = "用户管理")
	public void save(UserRole userRole) {
		// TODO Auto-generated method stub
		userRoleRepository.save(userRole);
	}
	@Override
	public void deleteByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		userRoleRepository.deleteByRoleId(roleId);
	}

}
