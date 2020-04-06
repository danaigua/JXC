package com.hengyue.respository.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.PlanCourse;

public interface PlanCourseRepository extends JpaRepository<PlanCourse, Integer>, JpaSpecificationExecutor<PlanCourse> {
	
	/**
	 * 通过名称查找planCourse实体
	 * @param planCourseName
	 * @return
	 */
	@Query(value = "select * from t_plan_course where name = ?1", nativeQuery = true)
	public PlanCourse findByName(String planCourseName);
	
	/**
	 * 修改一个planCourse为已经完成的状态
	 * @param planCourseId
	 */
	@Query(value = "update t_plan_course set finish=1 where id = ?1", nativeQuery = true)
	public void updateAsFinished(Integer planCourseId);

	/**
	 * 通过uuid查询新添加过程任务实体
	 * @param string
	 * @return
	 */
	@Query(value = "select * from t_plan_course where keysname = ?1", nativeQuery = true)
	public PlanCourse findByUUID(String string);

	/**
	 * 通过用户id查找任务实体
	 * @param id
	 * @return
	 */
	@Query(value = "select * from t_plan_course where user_id = ?1", nativeQuery = true)
	public List<PlanCourse> findByUserId(Integer id);

}
