package com.hengyue.respository.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.Employee;

/**
 * 员工考核持久层接口
 * @author 章家宝
 *
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

	/**
	 * 通过姓名查找员工实体
	 * @param employeeName
	 * @return
	 */
	@Query(value = "select * from t_employee where name = ?1", nativeQuery = true)
	public Employee findByName(String employeeName);
	
	/**
	 * 通过关键词模糊查询
	 * @param q
	 * @return
	 */
	@Query(value = "select * from t_employee where name like ?1", nativeQuery = true)
	public List<Employee> comboList(String q);

	/**
	 * 
	 * @param employeeName
	 * @param employeeNum
	 * @return
	 */
	@Query(value = "select * from t_employee where name = ?1 and employee_num = ?2", nativeQuery = true)
	public Employee findByNameAndNum(String employeeName, String employeeNum);
}
