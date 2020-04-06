package com.hengyue.service;
/**
 * 客户业务层接口
 * @author 章家宝
 *
 */

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.Customer;

public interface CustomerService {

	/**
	 * 分页查询所有客户
	 * @param Customer
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Customer> list(Customer customer, Integer page, Integer pageSize, Direction direction, String...properties);
	/**
	 * 获取客户总记录数
	 * @param Customer
	 * @return
	 */
	public Long getCount(Customer customer);
	/**
	 * 修改或者添加客户
	 * @param Customer
	 */
	public void save(Customer customer);
	/**
	 * 删除客户
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找实体
	 * @param id
	 * @return
	 */
	public Customer findById(Integer id);
	/**
	 * 通过客户姓名查找客户
	 * @param customerName
	 * @return
	 */
	public Customer findByCustomerName(String customerName);
	/**
	 * 通过名称查找客户
	 * @param name
	 * @return
	 */
	public List<Customer> findByName(String name);
	/**
	 * 通过代码查询客户
	 * @param string
	 * @return
	 */
	public Customer findCode(String string);
}
