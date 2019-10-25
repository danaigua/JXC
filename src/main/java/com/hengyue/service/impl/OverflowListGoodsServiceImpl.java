package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hengyue.entity.DamageListGoods;
import com.hengyue.entity.OverflowListGoods;
import com.hengyue.respository.OverflowListGoodsRepository;
import com.hengyue.respository.OverflowListRepository;
import com.hengyue.service.OverflowListGoodsService;
/**
 * 商品报溢单商品业务层实现类
 * @author 章家宝
 *
 */
@Service("overflowListGoodsService")
public class OverflowListGoodsServiceImpl implements OverflowListGoodsService {

	@Resource
	private OverflowListRepository overflowListRepository;
	
	@Resource
	private OverflowListGoodsRepository overflowListGoodsRepository;

	@Override
	public List<OverflowListGoods> listByOverflowListId(Integer id) {
		// TODO Auto-generated method stub
		return overflowListGoodsRepository.listByOverflowListId(id);
	}
}
