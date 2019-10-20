package com.hengyue.service;

import com.hengyue.entity.UserRole;

public interface UserRoleService {

	/**
	 * 通过用户id删除关联
	 * @param userId
	 */
	public void deleteByUserId(Integer userId);
	
	/**
	 * 添加或者修改用户角色关系
	 * @param userRole
	 */
	public void save(UserRole userRole);
	/**
	 * 通过roleid删除关联
	 * @param userId
	 */
	public void deleteByRoleId(Integer roleId);
}
