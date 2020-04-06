package com.hengyue.service.vo;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.Salary;
/**
 * 薪资模板service接口
 * @author 章家宝
 *
 */
public interface SalaryService {

	
	/**
	 * 根据条件分页查询薪资模板信息
	 * @param salary
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Salary> list(Salary salary, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param salary
	 * @return
	 */
	public Long getCount(Salary salary);
	
	/**
	 * 添加或者修改薪资模板信息
	 * @param salary
	 */
	public void save(Salary salary);
	/**
	 * 根据id删除薪资模板
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找薪资模板
	 * @param id
	 * @return
	 */
	public Salary findById(Integer id);
	/**
	 * 列出所有的薪资模板
	 * @return
	 */
	public List<Salary> listAll();

	
}
