package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
/**
 * 商品类别接口实现类
 * @author 章家宝
 *
 */

import com.hengyue.entity.GoodsType;
import com.hengyue.respository.GoodsTypeRepository;
import com.hengyue.service.GoodsTypeService;
@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService {

	@Resource
	private GoodsTypeRepository goodsTypeRepository;

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		goodsTypeRepository.deleteById(id);
	}

	@Override
	public void save(GoodsType goodsType) {
		// TODO Auto-generated method stub
		goodsTypeRepository.save(goodsType);
	}

	@Override
	public List<GoodsType> findByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		return goodsTypeRepository.findByParentId(parentId);
	}

	@Override
	public GoodsType findById(Integer id) {
		// TODO Auto-generated method stub
		return goodsTypeRepository.getOne(id);
	}
	
	
}
