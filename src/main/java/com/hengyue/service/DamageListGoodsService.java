package com.hengyue.service;

import java.util.List;

import com.hengyue.entity.DamageListGoods;

public interface DamageListGoodsService {

	/**
	 * 通过商品报损单id查找商品报损单商品
	 * 
	 * @param id
	 * @return
	 */
	public List<DamageListGoods> listByDamageListId(Integer id);
}
