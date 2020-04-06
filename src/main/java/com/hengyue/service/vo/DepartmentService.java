package com.hengyue.service.vo;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.Department;
/**
 * 部门service接口
 * @author 章家宝
 *
 */
public interface DepartmentService {

	
	/**
	 * 根据条件分页查询部门信息
	 * @param department
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Department> list(Department department, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param department
	 * @return
	 */
	public Long getCount(Department department);
	
	/**
	 * 添加或者修改部门信息
	 * @param department
	 */
	public void save(Department department);
	/**
	 * 根据id删除部门
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找部门
	 * @param id
	 * @return
	 */
	public Department findById(Integer id);

	/**
	 * 查看所有的部门
	 * @return
	 */
	public List<Department> listAll();
}
