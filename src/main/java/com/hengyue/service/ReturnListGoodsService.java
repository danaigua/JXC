package com.hengyue.service;

import java.util.List;

import com.hengyue.entity.ReturnListGoods;


public interface ReturnListGoodsService {

	/**
	 * 通过退货单id查找退货单商品
	 * 
	 * @param id
	 * @return
	 */
	public List<ReturnListGoods> listByReturnListId(Integer id);
	/**
	 * 根据条件查询退货单商品
	 * @param returnListGoods
	 * @return
	 */
	public List<ReturnListGoods> list(ReturnListGoods returnListGoods);
	
}
