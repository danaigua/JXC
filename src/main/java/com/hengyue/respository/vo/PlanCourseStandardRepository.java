package com.hengyue.respository.vo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.PlanCourseStandard;

public interface PlanCourseStandardRepository extends JpaRepository<PlanCourseStandard, Integer>, JpaSpecificationExecutor<PlanCourseStandard> {

	/**
	 * 通过查找任务标准名称进而查找任务标准实体
	 * @param planCourseName
	 * @return
	 */
	@Query(value = "select * from t_plan_course_standard where name = ?1", nativeQuery = true)
	public PlanCourseStandard findByName(String planCourseName);

}