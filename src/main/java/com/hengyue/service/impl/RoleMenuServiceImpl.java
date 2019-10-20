package com.hengyue.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengyue.entity.RoleMenu;
import com.hengyue.respository.RoleMenuRepository;
import com.hengyue.service.RoleMenuService;

/**
 * 用户角色关联接口实现类
 * @author 章家宝
 *
 */
@Service("roleMenuService")
@Transactional
public class RoleMenuServiceImpl implements RoleMenuService{


	@Resource
	private RoleMenuRepository roleMenuRepository;

	@Override
	public void deleteByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		roleMenuRepository.deleteByRoleId(roleId);
	}

	@Override
	public void save(RoleMenu roleMenu) {
		// TODO Auto-generated method stub
		roleMenuRepository.save(roleMenu);
	}

}
