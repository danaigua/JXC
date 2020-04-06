package com.hengyue.service.vo;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.Canteen;
/**
 * 餐厅就餐service接口
 * @author 章家宝
 *
 */
public interface CanteenService {

	
	/**
	 * 根据条件分页查询餐厅就餐信息
	 * @param canteen
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Canteen> list(Canteen canteen, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param canteen
	 * @return
	 */
	public Long getCount(Canteen canteen);
	
	/**
	 * 添加或者修改餐厅就餐信息
	 * @param canteen
	 */
	public void save(Canteen canteen);
	/**
	 * 根据id删除餐厅就餐
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找餐厅就餐
	 * @param id
	 * @return
	 */
	public Canteen findById(Integer id);

	/**
	 * 删除一个时间内的所有员工食堂就餐信息
	 * @param times
	 */
	public List<Canteen> selectByTimes(String times);
}
