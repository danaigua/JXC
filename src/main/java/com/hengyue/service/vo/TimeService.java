package com.hengyue.service.vo;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.Salary;
import com.hengyue.entity.vo.Time;
/**
 * 工作时间service接口
 * @author 章家宝
 *
 */
public interface TimeService {
	
	/**
	 * 根据条件分页查询工作时间信息
	 * @param time
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Time> list(Time time, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param time
	 * @return
	 */
	public Long getCount(Time time);
	
	/**
	 * 添加或者修改工作时间信息
	 * @param time
	 */
	public void save(Time time);
	/**
	 * 根据id删除工作时间
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找工作时间
	 * @param id
	 * @return
	 */
	public Time findById(Integer id);
	/**
	 * 不分页查询薪资表
	 * @param salary
	 * @param asc
	 * @param string
	 * @return
	 */
	public List<Time> listNoPage(Time time, Direction asc, String string);

	/**
	 * 通过员工姓名和员工名称查询员工工作时间表
	 * @param employeeName
	 * @return
	 */
	public Time findByEmployeeNameAndDate(String employeeName, Date date);
}
