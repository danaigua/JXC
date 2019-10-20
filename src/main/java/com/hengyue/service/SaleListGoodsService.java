package com.hengyue.service;

import java.util.List;


import com.hengyue.entity.SaleListGoods;

public interface SaleListGoodsService {

	/**
	 * 通过进货单id查找进货单商品
	 * 
	 * @param id
	 * @return
	 */
	public List<SaleListGoods> listBySaleListId(Integer id);
	/**
	 * 通过商品id查找销售总数
	 * @param goodsId
	 * @return
	 */
	public Integer getTotalByGoodsId(Integer goodsId);
}