package com.hengyue.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.CustomerReturnList;
import com.hengyue.entity.CustomerReturnListGoods;

/**
 *客户退单业务层接口
 * @author 章家宝
 *
 */
public interface CustomerReturnListService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public CustomerReturnList findById(Integer id);
	/**
	 * 获取当天客户退单号
	 * @return
	 */
	public String getTodayMaxCustomerReturnNumber();
	/**
	 * 添加客户退单以及所有客户退单商品以及修改商品价格，进价和库存
	 * @param saleListGoods
	 * @param saleList
	 */
	public void save(CustomerReturnList saleList, List<CustomerReturnListGoods> saleListGoods);
	
	/**
	 * 根据条件查询客户退单信息
	 * @param saleList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<CustomerReturnList> list(CustomerReturnList saleList, Direction direction, String...properties);
	/**
	 * 根据id删除客户退单以及客户退单实体
	 * @param id
	 */
	public void delete(Integer id);
}
