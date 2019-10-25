package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hengyue.entity.DamageListGoods;
import com.hengyue.respository.DamageListGoodsRepository;
import com.hengyue.respository.DamageListRepository;
import com.hengyue.service.DamageListGoodsService;
/**
 * 商品报损单商品业务层实现类
 * @author 章家宝
 *
 */
@Service("damageListGoodsService")
public class DamageListGoodsServiceImpl implements DamageListGoodsService {

	@Resource
	private DamageListRepository damageListRepository;
	
	@Resource
	private DamageListGoodsRepository damageListGoodsRepository;

	@Override
	public List<DamageListGoods> listByDamageListId(Integer id) {
		// TODO Auto-generated method stub
		return damageListGoodsRepository.listByDamageListId(id);
	}
}
