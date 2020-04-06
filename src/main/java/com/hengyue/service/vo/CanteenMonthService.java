package com.hengyue.service.vo;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.CanteenMonth;
/**
 * 餐厅就餐service接口
 * @author 章家宝
 *
 */
public interface CanteenMonthService {

	
	/**
	 * 根据条件分页查询餐厅就餐信息
	 * @param canteenMonth
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<CanteenMonth> list(CanteenMonth canteenMonth, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param canteenMonth
	 * @return
	 */
	public Long getCount(CanteenMonth canteenMonth);
	
	/**
	 * 添加或者修改餐厅就餐信息
	 * @param canteenMonth
	 */
	public void save(CanteenMonth canteenMonth);
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
	public CanteenMonth findById(Integer id);

	/**
	 *通过时间查找
	 * @param times
	 */
	public CanteenMonth findByTimes(Date times);
}
