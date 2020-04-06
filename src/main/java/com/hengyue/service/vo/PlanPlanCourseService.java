package com.hengyue.service.vo;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.PlanPlanCourse;
/**
 * 计划-计划过程service接口
 * @author 章家宝
 *
 */
public interface PlanPlanCourseService {

	
	/**
	 * 根据条件分页查询计划-计划过程信息
	 * @param planPlanCourse
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<PlanPlanCourse> list(PlanPlanCourse planPlanCourse, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param planPlanCourse
	 * @return
	 */
	public Long getCount(PlanPlanCourse planPlanCourse);
	
	/**
	 * 添加或者修改计划-计划过程信息
	 * @param planPlanCourse
	 */
	public void save(PlanPlanCourse planPlanCourse);
	/**
	 * 根据id删除计划-计划过程
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找计划-计划过程
	 * @param id
	 * @return
	 */
	public PlanPlanCourse findById(Integer id);

	/**
	 * 通过计划id查找计划中的任务id
	 * @param id
	 * @return
	 */
	public List<PlanPlanCourse> findByPlanId(Integer planId);

	/**
	 * 通过计划中的任务id查找
	 * @param id
	 * @return
	 */
	public PlanPlanCourse findByPlanCourseId(Integer id);
}
