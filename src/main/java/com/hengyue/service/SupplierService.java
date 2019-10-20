package com.hengyue.service;
/**
 * 供应商业务层接口
 * @author 章家宝
 *
 */

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.Supplier;

public interface SupplierService {

	/**
	 * 分页查询所有供应商
	 * @param supplier
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Supplier> list(Supplier supplier, Integer page, Integer pageSize, Direction direction, String...properties);
	/**
	 * 获取供应商总记录数
	 * @param supplier
	 * @return
	 */
	public Long getCount(Supplier supplier);
	/**
	 * 修改或者添加供应商
	 * @param supplier
	 */
	public void save(Supplier supplier);
	/**
	 * 删除供应商
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找实体
	 * @param id
	 * @return
	 */
	public Supplier findById(Integer id);
	/**
	 * 通过供应商姓名查找供应商
	 * @param supplierName
	 * @return
	 */
	public Supplier findBySupplierName(String supplierName);
	/**
	 * 通过供应商名称模糊查询
	 * @param name
	 * @return
	 */
	public List<Supplier> findByName(String name);
}
