package com.hengyue.service.vo;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.PlanCourseStandard;

public interface PlanCourseStandardService {
	
	/**
	 * 通过id查找planPlanCourseStandard实体
	 * @param planPlanCourseStandardId
	 * @return
	 */
	public PlanCourseStandard findById(Integer planPlanCourseStandardId);
	
	/**
	 * 通过名称查找planPlanCourseStandard实体
	 * @param planPlanCourseStandardName
	 * @return
	 */
	public PlanCourseStandard findByName(String planPlanCourseStandardName);
	/**
	 * 分页查询planPlanCourseStandard
	 * @param planPlanCourseStandard
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<PlanCourseStandard> list(PlanCourseStandard planPlanCourseStandard, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param planPlanCourseStandard
	 * @return
	 */
	public Long getCount(PlanCourseStandard planPlanCourseStandard);
	/**
	 * 通过id删除一个planPlanCourseStandard
	 * @param planPlanCourseStandardId
	 */
	public void delete(Integer planPlanCourseStandardId);
	/**
	 * 保存或者修改一个planPlanCourseStandard
	 * @param planPlanCourseStandard
	 */
	public void save(PlanCourseStandard planPlanCourseStandard);

	/**
	 * combobox用
	 */
	public List<PlanCourseStandard> findAll();
}
