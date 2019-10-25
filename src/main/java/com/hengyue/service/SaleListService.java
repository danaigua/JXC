package com.hengyue.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.SaleList;
import com.hengyue.entity.SaleListGoods;

/**
 *销售单业务层接口
 * @author 章家宝
 *
 */
public interface SaleListService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public SaleList findById(Integer id);
	/**
	 * 获取当天销售单号
	 * @return
	 */
	public String getTodayMaxSaleNumber();
	/**
	 * 添加销售单以及所有销售单商品以及修改商品价格，进价和库存
	 * @param saleListGoods
	 * @param saleList
	 */
	public void save(SaleList saleList, List<SaleListGoods> saleListGoods);
	
	/**
	 * 根据条件查询销售单信息
	 * @param saleList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<SaleList> list(SaleList saleList, Direction direction, String...properties);
	/**
	 * 根据id删除销售单以及销售单实体
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 进货单支付状态修改
	 * @param saleList
	 */
	public void update(SaleList saleList);
	/**
	 * 按天统计某个日期范围内的销售信息
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Object> countSaleByDay(String begin,String end);
	
	/**
	 * 按月统计某个日期范围内的销售信息
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<Object> countSaleByMonth(String begin,String end);
}
