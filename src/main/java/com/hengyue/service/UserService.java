package com.hengyue.service;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.User;
/**
 * 用户service接口
 * @author 章家宝
 *
 */
public interface UserService {

	/**
	 * 根据用户名查找实体
	 * @param userName
	 * @return
	 */
	public User findByUserName(String userName);
	
	/**
	 * 根据条件分页查询用户信息
	 * @param user
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<User> list(User user, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param user
	 * @return
	 */
	public Long getCount(User user);
	
	/**
	 * 添加或者修改用户信息
	 * @param user
	 */
	public void save(User user);
	/**
	 * 根据id删除用户
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找用户
	 * @param id
	 * @return
	 */
	public User findById(Integer id);

	/**
	 * 查找所有的用户
	 * @return
	 */
	public List<User> findAll();

	/**
	 * 通过查找用户真实姓名返回用户实体
	 * @param userName
	 * @return
	 */
	public User findByUserTrueName(String userName);
}
