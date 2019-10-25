package com.hengyue.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.OverflowList;
import com.hengyue.entity.OverflowListGoods;

/**
 * 商品报溢单业务层接口
 * @author 章家宝
 *
 */
public interface OverflowListService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public OverflowList findById(Integer id);
	/**
	 * 获取当天商品报溢单号
	 * @return
	 */
	public String getTodayMaxOverflowNumber();
	/**
	 * 添加商品报溢单以及所有商品报溢单商品以及修改商品价格，进价和库存
	 * @param overflowList
	 * @param overflowListGoods
	 */
	public void save(OverflowList overflowList, List<OverflowListGoods> overflowListGoods);
	
	/**
	 * 根据条件查询商品报溢单信息
	 * @param overflowList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<OverflowList> list(OverflowList overflowList, Direction direction, String...properties);
}
