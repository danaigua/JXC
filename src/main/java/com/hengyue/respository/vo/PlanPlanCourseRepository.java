package com.hengyue.respository.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.PlanPlanCourse;

public interface PlanPlanCourseRepository extends JpaRepository<PlanPlanCourse, Integer>, JpaSpecificationExecutor<PlanPlanCourse> {

	/**
	 * 
	 * @param planId
	 * @return
	 */
	@Query(value = "select * from t_plan_plan_course where plan_id = ?1", nativeQuery = true)
	public List<PlanPlanCourse> findByPlanId(Integer planId);


	/**
	 * 
	 * @param planId
	 * @return
	 */
	@Query(value = "select * from t_plan_plan_course where plan_course = ?1", nativeQuery = true)
	public PlanPlanCourse findByPlanCourseId(Integer id);

}
