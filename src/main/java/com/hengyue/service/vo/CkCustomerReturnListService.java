package com.hengyue.service.vo;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.CkCustomerReturnList;
import com.hengyue.entity.vo.CkCustomerReturnListGoods;


/**
 *客户退单业务层接口
 * @author 章家宝
 *
 */
public interface CkCustomerReturnListService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public CkCustomerReturnList findById(Integer id);
	/**
	 * 获取当天客户退单号
	 * @return
	 */
	public String getTodayMaxCkCustomerReturnNumber();
	/**
	 * 添加客户退单以及所有客户退单商品以及修改商品价格，进价和库存
	 * @param saleListGoods
	 * @param saleList
	 */
	public void save(CkCustomerReturnList saleList, List<CkCustomerReturnListGoods> saleListGoods);
	
	/**
	 * 根据条件查询客户退单信息
	 * @param saleList
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<CkCustomerReturnList> list(CkCustomerReturnList saleList, Direction direction, String...properties);
	/**
	 * 根据id删除客户退单以及客户退单实体
	 * @param id
	 */
	public void delete(Integer id);
	
	public void update(CkCustomerReturnList saleList);
}
