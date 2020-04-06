package com.hengyue.respository.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.PlanCourseStandardType;

/**
 * 任务测试类型持久层接口
 * @author 章家宝
 *
 */
public interface PlanCourseStandardTypeRepository extends JpaRepository<PlanCourseStandardType, Integer> {
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	@Query(value = "select * from t_plan_course_standard_type where p_id = ?1", nativeQuery = true)
	public List<PlanCourseStandardType> findByParentId(int parentId);

	/**
	 * 通过名称查找实体
	 * @param string
	 * @return
	 */
	@Query(value = "select * from t_plan_course_standard_type where name = ?1 and url = ?2", nativeQuery = true)
	public List<PlanCourseStandardType> findByName(String string, String url);
}
