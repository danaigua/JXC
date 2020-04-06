package com.hengyue.service.vo;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.CkGoods;

/**
 * 商品业务层接口
 * @author 章家宝
 *
 */
public interface CkGoodsService {

	/**
	 * 查询某个类别下得所有商品
	 * 
	 * @param parentId
	 * @return
	 */
	public List<CkGoods> findByTypeId(int typeId);
	/**
	 * 分页查询商品
	 * @param ckGoods
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<CkGoods> list(CkGoods ckGoods, Integer page, Integer pageSize, Direction direction, String...properties);
	/**
	 * 获取分页总记录数
	 * @param ckGoods
	 * @return
	 */
	public Long getCount(CkGoods ckGoods);
	/**
	 * 保存或者修改操作
	 * @param ckGoods
	 */
	public void save(CkGoods ckGoods);
	/**
	 * 删除操作
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找实体
	 * @param id
	 * @return
	 */
	public CkGoods findById(Integer id);
	
	/**
	 * 获取最大的商品编码
	 * @return
	 */
	public String getMaxCkGoodsCode();
	
	/**
	 * 根据商品编码或者商品名称分页查询没有库存的商品
	 * @param ckGoods
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<CkGoods> listNoInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize, Direction direction, String...properties);
	/**
	 * 根据商品编码或者商品名称分页查询没有库存的商品
	 * 获取分页总记录数
	 * @param ckGoods
	 * @return
	 */
	public Long getCountNoInventoryQuantityByCodeOrName(String codeOrName);
	/**
	 * 根据商品编码或者商品名称分页查询有库存的商品
	 * @param ckGoods
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<CkGoods> listHasInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize, Direction direction, String...properties);
	/**
	 * 根据商品编码或者商品名称分页查询有库存的商品
	 * 获取分页总记录数
	 * @param ckGoods
	 * @return
	 */
	public Long getCountHasInventoryQuantityByCodeOrName(String codeOrName);
	
	/**
	 * 获取报警的商品
	 * @return
	 */
	public List<CkGoods> listAlarm();
	/**
	 * 通过模型名称查找实体
	 * @param string
	 * @return
	 */
	public CkGoods findBymodelName(String string);

}
