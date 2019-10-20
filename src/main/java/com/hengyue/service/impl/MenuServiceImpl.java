package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hengyue.entity.Menu;
import com.hengyue.respository.MenuRepository;
import com.hengyue.service.MenuService;
/**
 * 权限菜单接口实现类
 * @author 章家宝
 *
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

	@Resource
	private MenuRepository menuRepository;
	@Override
	public List<Menu> findByParentIdAndRoleId(Integer parentId, Integer roleId) {
		return menuRepository.findByParentIdAndRoleId(parentId, roleId);
	}
	@Override
	public List<Menu> findByRoleId(Integer roleId) {
		// TODO Auto-generated method stub
		return menuRepository.findByRoleId(roleId);
	}
	@Override
	public List<Menu> findByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		return menuRepository.findByParentId(parentId);
	}
	@Override
	public Menu findById(Integer menuId) {
		// TODO Auto-generated method stub
		return menuRepository.getOne(menuId);
	}

}
