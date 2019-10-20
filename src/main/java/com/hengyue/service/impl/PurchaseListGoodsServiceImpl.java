package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hengyue.entity.PurchaseListGoods;
import com.hengyue.respository.PurchaseListGoodsRepository;
import com.hengyue.respository.PurchaseListRepository;
import com.hengyue.service.PurchaseListGoodsService;
/**
 * 进货单商品业务层实现类
 * @author 章家宝
 *
 */
@Service("purchaseListGoodsService")
public class PurchaseListGoodsServiceImpl implements PurchaseListGoodsService {

	@Resource
	private PurchaseListRepository purchaseListRepository;
	
	@Resource
	private PurchaseListGoodsRepository purchaseListGoodsRepository;

	@Override
	public List<PurchaseListGoods> listByPurchaseListId(Integer id) {
		// TODO Auto-generated method stub
		return purchaseListGoodsRepository.listByPurchaseListId(id);
	}
}
