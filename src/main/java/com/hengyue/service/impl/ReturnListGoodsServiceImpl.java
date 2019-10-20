package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hengyue.entity.ReturnListGoods;
import com.hengyue.respository.ReturnListGoodsRepository;
import com.hengyue.respository.ReturnListRepository;
import com.hengyue.service.ReturnListGoodsService;
/**
 * 进货单商品业务层实现类
 * @author 章家宝
 *
 */
@Service("returnListGoodsService")
public class ReturnListGoodsServiceImpl implements ReturnListGoodsService {

	@Resource
	private ReturnListRepository returnListRepository;
	
	@Resource
	private ReturnListGoodsRepository returnListGoodsRepository;

	@Override
	public List<ReturnListGoods> listByReturnListId(Integer id) {
		// TODO Auto-generated method stub
		return returnListGoodsRepository.listByReturnListId(id);
	}
}
