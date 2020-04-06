package com.hengyue.service.vo;
/**
 * 宿舍业务层接口
 * @author 章家宝
 *
 */


import java.util.List;

import com.hengyue.entity.vo.Dormitory;

public interface DormitoryService {
	/**
	 * 通过id删除商品类别
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 添加一个宿舍
	 * @param dormitory
	 */
	public void save(Dormitory dormitory);
	/**
	 * 通过id查找实体
	 * @param id
	 * @return
	 */
	public Dormitory findById(Integer id);
	/**
	 * 通过名称查找实体
	 * @param string
	 * @return
	 */
	public Dormitory findByName(String string);
	/**
	 * 列出所有的宿舍
	 * @param dormitory
	 * @return
	 */
	public List<Dormitory> list(Dormitory dormitory);
}
