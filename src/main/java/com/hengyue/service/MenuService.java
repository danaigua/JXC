package com.hengyue.service;

import java.util.List;

import com.hengyue.entity.Menu;

/**
 * 菜单业务层接口
 * @author 章家宝
 *
 */
public interface MenuService {
	/**
	 * 根据父节点以及角色id查询用户子节点
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public List<Menu> findByParentIdAndRoleId(Integer parentId, Integer roleId);
	/**
	 * 通过角色id查找菜单
	 * @param roleId
	 * @return
	 */
	public List<Menu> findByRoleId(Integer roleId);
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	public List<Menu> findByParentId(Integer parentId);
	/**
	 * 通过id查找实体
	 * @param menuId
	 * @return
	 */
	public Menu findById(Integer menuId);

}
