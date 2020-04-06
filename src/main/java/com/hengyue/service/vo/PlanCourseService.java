package com.hengyue.service.vo;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.PlanCourse;

public interface PlanCourseService {
	
	/**
	 * 通过id查找planCourse实体
	 * @param planCourseId
	 * @return
	 */
	public PlanCourse findById(Integer planCourseId);
	
	/**
	 * 通过名称查找planCourse实体
	 * @param planCourseName
	 * @return
	 */
	public PlanCourse findByName(String planCourseName);
	/**
	 * 分页查询planCourse
	 * @param planCourse
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<PlanCourse> list(PlanCourse planCourse, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param planCourse
	 * @return
	 */
	public Long getCount(PlanCourse planCourse);
	/**
	 * 通过id删除一个planCourse
	 * @param planCourseId
	 */
	public void delete(Integer planCourseId);
	/**
	 * 保存或者修改一个planCourse
	 * @param planCourse
	 */
	public void save(PlanCourse planCourse);
	/**
	 * 修改一个planCourse为已经完成的状态
	 * @param planCourseId
	 */
	public void updateAsFinished(Integer planCourseId);

	/**
	 * 通过uuid查询新添加过程任务实体
	 * @param string
	 * @return
	 */
	public PlanCourse findByUUID(String string);

	/**
	 * 通过用户id查找任务实体
	 * @param id
	 * @return
	 */
	public List<PlanCourse> findByUserId(Integer id);
}
