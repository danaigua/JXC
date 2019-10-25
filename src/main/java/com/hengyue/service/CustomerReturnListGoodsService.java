package com.hengyue.service;

import java.util.List;


import com.hengyue.entity.CustomerReturnListGoods;

public interface CustomerReturnListGoodsService {

	/**
	 * 通过进货单id查找进货单商品
	 * 
	 * @param id
	 * @return
	 */
	public List<CustomerReturnListGoods> listByCustomerReturnListId(Integer id);
	/**
	 * 通过商品id查找退货总数
	 * @param goodsId
	 * @return
	 */
	public Integer getTotalByGoodsId(Integer goodsId);
	
	/**
	 * 根据条件查询客户退货单商品
	 * @param customerReturnListGoods
	 * @return
	 */
	public List<CustomerReturnListGoods> list(CustomerReturnListGoods customerReturnListGoods);
}
