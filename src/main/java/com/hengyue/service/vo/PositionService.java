package com.hengyue.service.vo;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.Position;
/**
 * 职位service接口
 * @author 章家宝
 *
 */
public interface PositionService {

	
	/**
	 * 根据条件分页查询职位信息
	 * @param position
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Position> list(Position position, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param position
	 * @return
	 */
	public Long getCount(Position position);
	
	/**
	 * 添加或者修改职位信息
	 * @param position
	 */
	public void save(Position position);
	/**
	 * 根据id删除职位
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找职位
	 * @param id
	 * @return
	 */
	public Position findById(Integer id);

	/**
	 * combobox用
	 * 查询全部
	 * @return
	 */
	public List<Position> findAll();

	/**
	 * 通过职位名称查找职位实体
	 * @param position
	 * @return
	 */
	public Position findByName(String position);
}
