package com.hengyue.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.DamageList;
import com.hengyue.entity.DamageListGoods;

/**
 * 商品报损单业务层接口
 * @author 章家宝
 *
 */
public interface DamageListService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public DamageList findById(Integer id);
	/**
	 * 获取当天商品报损单号
	 * @return
	 */
	public String getTodayMaxDamageNumber();
	/**
	 * 添加商品报损单以及所有商品报损单商品以及修改商品价格，进价和库存
	 * @param damageList
	 * @param damageListGoods
	 */
	public void save(DamageList damageList, List<DamageListGoods> damageListGoods);
	
	/**
	 * 根据条件查询商品报损单信息
	 * @param damageList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<DamageList> list(DamageList damageList, Direction direction, String...properties);
}
