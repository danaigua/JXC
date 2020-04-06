package com.hengyue.service.vo;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.DepartmentPosition;
/**
 * 部门职位service接口
 * @author 章家宝
 *
 */
public interface DepartmentPositionService {

	
	/**
	 * 根据条件分页查询部门职位信息
	 * @param departmentPosition
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<DepartmentPosition> list(DepartmentPosition departmentPosition, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param departmentPosition
	 * @return
	 */
	public Long getCount(DepartmentPosition departmentPosition);
	
	/**
	 * 添加或者修改部门职位信息
	 * @param departmentPosition
	 */
	public void save(DepartmentPosition departmentPosition);
	/**
	 * 根据id删除部门职位
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找部门职位
	 * @param id
	 * @return
	 */
	public DepartmentPosition findById(Integer id);
}
