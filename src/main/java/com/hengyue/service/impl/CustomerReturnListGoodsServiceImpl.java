package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hengyue.entity.CustomerReturnListGoods;
import com.hengyue.respository.CustomerReturnListGoodsRepository;
import com.hengyue.respository.CustomerReturnListRepository;
import com.hengyue.service.CustomerReturnListGoodsService;
/**
 * 客户退货商品业务层实现类
 * @author 章家宝
 *
 */
@Service("customerReturnListGoodsService")
public class CustomerReturnListGoodsServiceImpl implements CustomerReturnListGoodsService {

	@Resource
	private CustomerReturnListRepository customerReturnListRepository;
	
	@Resource
	private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;

	@Override
	public List<CustomerReturnListGoods> listByCustomerReturnListId(Integer id) {
		// TODO Auto-generated method stub
		return customerReturnListGoodsRepository.listByCustomerReturnListId(id);
	}

	@Override
	public Integer getTotalByGoodsId(Integer goodsId) {
		// TODO Auto-generated method stub
		return customerReturnListGoodsRepository.getTotalByGoodsId(goodsId) == null ? 0 : customerReturnListGoodsRepository.getTotalByGoodsId(goodsId);
	}
}
