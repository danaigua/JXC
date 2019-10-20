package com.hengyue.service;

import java.util.List;

import com.hengyue.entity.PurchaseListGoods;

public interface PurchaseListGoodsService {

	/**
	 * 通过进货单id查找进货单商品
	 * 
	 * @param id
	 * @return
	 */
	public List<PurchaseListGoods> listByPurchaseListId(Integer id);
}
