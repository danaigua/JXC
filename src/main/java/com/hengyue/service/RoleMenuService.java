package com.hengyue.service;

import com.hengyue.entity.RoleMenu;

public interface RoleMenuService {
	/**
	 * 通过roleid删除关联
	 * @param userId
	 */
	public void deleteByRoleId(Integer roleId);
	/**
	 * 保存实体
	 * @param roleMenu
	 */
	public void save(RoleMenu roleMenu);
}
