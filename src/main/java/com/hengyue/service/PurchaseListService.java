package com.hengyue.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.PurchaseList;
import com.hengyue.entity.PurchaseListGoods;

/**
 * 进货单业务层接口
 * @author 章家宝
 *
 */
public interface PurchaseListService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public PurchaseList findById(Integer id);
	/**
	 * 获取当天进货单号
	 * @return
	 */
	public String getTodayMaxPurchaseNumber();
	/**
	 * 添加进货单以及所有进货单商品以及修改商品价格，进价和库存
	 * @param purchaseList
	 * @param purchaseListGoods
	 */
	public void save(PurchaseList purchaseList, List<PurchaseListGoods> purchaseListGoods);
	
	/**
	 * 根据条件查询进货单信息
	 * @param purchaseList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<PurchaseList> list(PurchaseList purchaseList, Direction direction, String...properties);
	/**
	 * 根据id删除进货单以及进货单实体
	 * @param id
	 */
	public void delete(Integer id);
}
