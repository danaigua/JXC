package com.hengyue.service.vo;
/**
 * 测试标准类别业务层接口
 * @author 章家宝
 *
 */

import java.util.List;

import com.hengyue.entity.vo.PlanCourseStandardType;

public interface PlanCourseStandardTypeService {
	/**
	 * 通过id删除商品类别
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 添加一个商品类别
	 * @param planCourseStandardType
	 */
	public void save(PlanCourseStandardType planCourseStandardType);
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	public List<PlanCourseStandardType> findByParentId(Integer parentId);
	/**
	 * 通过id查找实体
	 * @param id
	 * @return
	 */
	public PlanCourseStandardType findById(Integer id);
	/**
	 * 通过名称查找实体
	 * @param string
	 * @return
	 */
	public PlanCourseStandardType findByName(String string, String url);
}
