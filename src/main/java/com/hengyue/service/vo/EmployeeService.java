package com.hengyue.service.vo;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.Employee;
/**
 * 员工service接口
 * @author 章家宝
 *
 */
public interface EmployeeService {

	
	/**
	 * 根据条件分页查询员工信息
	 * @param employee
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Employee> list(Employee employee, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param employee
	 * @return
	 */
	public Long getCount(Employee employee);
	
	/**
	 * 添加或者修改员工信息
	 * @param employee
	 */
	public void save(Employee employee);
	/**
	 * 根据id删除员工
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找员工
	 * @param id
	 * @return
	 */
	public Employee findById(Integer id);
	/**
	 * 通过员工姓名查找员工实体
	 * @param employeeName
	 * @return
	 */
	public Employee findByName(String employeeName);

	/**
	 * 列出所有的员工名单
	 * @return
	 */
	public List<Employee> listAll();

	/**
	 * 根据输入查找名称
	 * 关联查询
	 * @param q
	 * @return
	 */
	public List<Employee> comboList(String q);
	/**
	 * 
	 * @param employeeName
	 * @param employeeNum
	 * @return
	 */
	public Employee findByNameAndNum(String employeeName, String employeeNum);
}
