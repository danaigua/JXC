package com.hengyue.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.Role;
/**
 * 角色service接口
 * @author 章家宝
 *
 */
public interface RoleService {

	/**
	 * 根据用户id查找角色实体
	 * @param id
	 * @return
	 */
	public List<Role> findByUserId(Integer id);
	/**
	 * 根据id查询实体
	 */
	public Role findById(Integer id);
	/**
	 * 查询所有角色信息
	 * @return
	 */
	public List<Role> listAll();
	/**
	 * 分页查询权限实体
	 * @return
	 */
	public List<Role> list(Role role, Integer page, Integer pageSize, Direction direction, String...properties);
	/**
	 * 获取总记录数
	 * @return
	 */
	public Long getCount(Role role);
	/**
	 * 删除权限
	 */
	public void delete(Integer id);
	/**
	 * 更新或者新建一个权限
	 * @param role
	 */
	public void save(Role role);
	/**
	 * 通过名称查询角色
	 * @param name
	 * @return
	 */
	public Role findByRoleName(String name);
	
}
