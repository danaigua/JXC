package com.hengyue.service.vo;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.Assess;
/**
 * 绩效考核service接口
 * @author 章家宝
 *
 */
public interface AssessService {

	
	/**
	 * 根据条件分页查询绩效考核信息
	 * @param assess
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Assess> list(Assess assess, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param assess
	 * @return
	 */
	public Long getCount(Assess assess);
	
	/**
	 * 添加或者修改绩效考核信息
	 * @param assess
	 */
	public void save(Assess assess);
	/**
	 * 根据id删除绩效考核
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找绩效考核
	 * @param id
	 * @return
	 */
	public Assess findById(Integer id);
}
