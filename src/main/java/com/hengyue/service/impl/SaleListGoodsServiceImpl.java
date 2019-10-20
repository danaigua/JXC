package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hengyue.entity.SaleListGoods;
import com.hengyue.respository.SaleListGoodsRepository;
import com.hengyue.respository.SaleListRepository;
import com.hengyue.service.SaleListGoodsService;
/**
 * 销售单商品业务层实现类
 * @author 章家宝
 *
 */
@Service("saleListGoodsService")
public class SaleListGoodsServiceImpl implements SaleListGoodsService {

	@Resource
	private SaleListRepository saleListRepository;
	
	@Resource
	private SaleListGoodsRepository saleListGoodsRepository;

	@Override
	public List<SaleListGoods> listBySaleListId(Integer id) {
		// TODO Auto-generated method stub
		return saleListGoodsRepository.listBySaleListId(id);
	}

	@Override
	public Integer getTotalByGoodsId(Integer goodsId) {
		// TODO Auto-generated method stub
		return saleListGoodsRepository.getTotalByGoodsId(goodsId) == null ? 0 : saleListGoodsRepository.getTotalByGoodsId(goodsId);
	}
}
