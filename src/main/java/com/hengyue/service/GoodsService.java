package com.hengyue.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.Goods;

/**
 * 商品业务层接口
 * @author 章家宝
 *
 */
public interface GoodsService {

	/**
	 * 查询某个类别下得所有商品
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Goods> findByTypeId(int typeId);
	/**
	 * 分页查询商品
	 * @param goods
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Goods> list(Goods goods, Integer page, Integer pageSize, Direction direction, String...properties);
	/**
	 * 获取分页总记录数
	 * @param goods
	 * @return
	 */
	public Long getCount(Goods goods);
	/**
	 * 保存或者修改操作
	 * @param goods
	 */
	public void save(Goods goods);
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
	public Goods findById(Integer id);
	
	/**
	 * 获取最大的商品编码
	 * @return
	 */
	public String getMaxGoodsCode();
	
	/**
	 * 根据商品编码或者商品名称分页查询没有库存的商品
	 * @param goods
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Goods> listNoInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize, Direction direction, String...properties);
	/**
	 * 根据商品编码或者商品名称分页查询没有库存的商品
	 * 获取分页总记录数
	 * @param goods
	 * @return
	 */
	public Long getCountNoInventoryQuantityByCodeOrName(String codeOrName);
	/**
	 * 根据商品编码或者商品名称分页查询有库存的商品
	 * @param goods
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Goods> listHasInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize, Direction direction, String...properties);
	/**
	 * 根据商品编码或者商品名称分页查询有库存的商品
	 * 获取分页总记录数
	 * @param goods
	 * @return
	 */
	public Long getCountHasInventoryQuantityByCodeOrName(String codeOrName);
}
