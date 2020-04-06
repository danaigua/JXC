package com.hengyue.service.vo;

import java.util.List;

import com.hengyue.entity.vo.CkCustomerReturnListGoods;



public interface CkCustomerReturnListGoodsService {

	/**
	 * 通过进货单id查找进货单商品
	 * 
	 * @param id
	 * @return
	 */
	public List<CkCustomerReturnListGoods> listByCkCustomerReturnListId(Integer id);
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
	public List<CkCustomerReturnListGoods> list(CkCustomerReturnListGoods customerReturnListGoods);
}
