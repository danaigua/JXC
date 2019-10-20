package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hengyue.entity.GoodsUnit;
import com.hengyue.respository.GoodsUnitRepository;
import com.hengyue.service.GoodsUnitService;

/**
 * 商品单位业务层接口实现类
 * @author 章家宝
 *
 */
@Service("goodsUnitService")
public class GoodsUnitServiceImpl implements GoodsUnitService {

	@Resource
	private GoodsUnitRepository goodsUnitRepository;
	
	@Override
	public void save(GoodsUnit goodsUnit) {
		// TODO Auto-generated method stub
		goodsUnitRepository.save(goodsUnit);
	}

	@Override
	public List<GoodsUnit> listAll() {
		// TODO Auto-generated method stub
		return goodsUnitRepository.findAll();
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		goodsUnitRepository.deleteById(id);
	}

	@Override
	public GoodsUnit findById(Integer id) {
		// TODO Auto-generated method stub
		return goodsUnitRepository.getOne(id);
	}

}
