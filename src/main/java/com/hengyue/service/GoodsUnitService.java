package com.hengyue.service;

import java.util.List;

import com.hengyue.entity.GoodsUnit;

/**
 * 商品单位业务层接口
 * @author 章家宝
 *
 */
public interface GoodsUnitService {

	/**
	 * 新建或者修改商品单位
	 * @param goodsUnit
	 */
	public void save(GoodsUnit goodsUnit);
	/**
	 * 查询所有的商品单位
	 * @return
	 */
	public List<GoodsUnit> listAll();
	/**
	 * 根据id删除实体
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 根据id查找实体
	 * @param id
	 * @return
	 */
	public GoodsUnit findById(Integer id);
}
