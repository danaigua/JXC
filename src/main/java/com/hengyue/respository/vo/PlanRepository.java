package com.hengyue.respository.vo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.Plan;

/**
 * 
 * @author 章家宝
 *
 */
public interface PlanRepository extends JpaRepository<Plan, Integer>, JpaSpecificationExecutor<Plan> {

	/**
	 * 通过名称查找plan实体
	 * @param planName
	 * @return
	 */
	@Query(value = "select * from t_plan where name = ?1", nativeQuery = true)
	public Plan findByName(String planName);
	
	/**
	 * 修改一个plan为已经完成的状态
	 * @param planId
	 */
	@Query(value = "update t_plan set finish=1 where id = ?1", nativeQuery = true)
	public void updateAsFinished(Integer planId);

	/**
	 * 通过PlanCourseId查询出plan
	 * @param id
	 * @return
	 */
	@Query(value = "SELECT t2.* FROM t_plan_plan_course t1, t_plan t2 WHERE t1.`plan_course` = ?1 AND t1.`plan_id` = t2.`id`", nativeQuery = true)
	public Plan findByPlanCourseId(Integer id);
}
