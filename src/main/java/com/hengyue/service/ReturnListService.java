package com.hengyue.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.ReturnList;
import com.hengyue.entity.ReturnListGoods;

/**
 * 退货单业务层接口
 * @author 章家宝
 *
 */
public interface ReturnListService {

	/**
	 * 获取当天退货单号
	 * @return
	 */
	public String getTodayMaxReturnNumber();
	/**
	 * 添加退货单以及所有退货单商品以及修改库存
	 * @param ReturnList
	 * @param ReturnListGoods
	 */
	public void save(ReturnList returnList, List<ReturnListGoods> returnListGoods);
	
	/**
	 * 根据条件查询退货单信息
	 * @param ReturnList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<ReturnList> list(ReturnList returnList, Direction direction, String...properties);
	/**
	 * 根据id删除退货单以及退货单实体
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public ReturnList findById(Integer id);
}
