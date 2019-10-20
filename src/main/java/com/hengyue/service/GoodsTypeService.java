package com.hengyue.service;
/**
 * 商品类别业务层接口
 * @author 章家宝
 *
 */

import java.util.List;

import com.hengyue.entity.GoodsType;

public interface GoodsTypeService {
	/**
	 * 通过id删除商品类别
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 添加一个商品类别
	 * @param goodsType
	 */
	public void save(GoodsType goodsType);
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	public List<GoodsType> findByParentId(Integer parentId);
	/**
	 * 通过id查找实体
	 * @param id
	 * @return
	 */
	public GoodsType findById(Integer id);
}
