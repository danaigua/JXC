package com.hengyue.service.vo;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.Plan;

public interface PlanService {
	
	/**
	 * 通过id查找plan实体
	 * @param planId
	 * @return
	 */
	public Plan findById(Integer planId);
	
	/**
	 * 通过名称查找plan实体
	 * @param planName
	 * @return
	 */
	public Plan findByName(String planName);
	/**
	 * 分页查询plan
	 * @param plan
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Plan> list(Plan plan, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param plan
	 * @return
	 */
	public Long getCount(Plan plan);
	/**
	 * 通过id删除一个plan
	 * @param planId
	 */
	public void delete(Integer planId);
	/**
	 * 保存或者修改一个plan
	 * @param plan
	 */
	public void save(Plan plan);
	/**
	 * 修改一个plan为已经完成的状态
	 * @param planId
	 */
	public void updateAsFinished(Integer planId);

	/**
	 * 通过PlanCourseId查询出plan
	 * @param id
	 * @return
	 */
	public Plan findByPlanCourseId(Integer id);
}
