package com.hengyue.service.vo;
/**
 * 商品类别业务层接口
 * @author 章家宝
 *
 */

import java.util.List;

import com.hengyue.entity.vo.CkGoodsType;

public interface CkGoodsTypeService {
	/**
	 * 通过id删除商品类别
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 添加一个商品类别
	 * @param ckGoodsType
	 */
	public void save(CkGoodsType ckGoodsType);
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	public List<CkGoodsType> findByParentId(Integer parentId);
	/**
	 * 通过id查找实体
	 * @param id
	 * @return
	 */
	public CkGoodsType findById(Integer id);
	/**
	 * 通过型号查找实体
	 * @param substring
	 * @return
	 */
	public CkGoodsType findByModelName(String substring);
	/**
	 * 通过型号查找实体id
	 * @param substring
	 * @return
	 */
	public Integer findByModel(CharSequence subSequence);
}
