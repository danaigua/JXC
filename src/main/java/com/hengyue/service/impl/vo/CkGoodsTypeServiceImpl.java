package com.hengyue.service.impl.vo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
/**
 * 商品类别接口实现类
 * @author 章家宝
 *
 */

import com.hengyue.entity.vo.CkGoodsType;
import com.hengyue.respository.vo.CkGoodsTypeRepository;
import com.hengyue.service.vo.CkGoodsTypeService;
@Service("ckGoodsTypeService")
public class CkGoodsTypeServiceImpl implements CkGoodsTypeService {

	@Resource
	private CkGoodsTypeRepository ckGoodsTypeRepository;

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		ckGoodsTypeRepository.deleteById(id);
	}

	@Override
	public void save(CkGoodsType ckGoodsType) {
		// TODO Auto-generated method stub
		ckGoodsTypeRepository.save(ckGoodsType);
	}

	@Override
	public List<CkGoodsType> findByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		return ckGoodsTypeRepository.findByParentId(parentId);
	}

	@Override
	public CkGoodsType findById(Integer id) {
		// TODO Auto-generated method stub
		return ckGoodsTypeRepository.getOne(id);
	}

	@Override
	public CkGoodsType findByModelName(String substring) {
		// TODO Auto-generated method stub
		return ckGoodsTypeRepository.findByModelName(substring);
	}

	@Override
	public Integer findByModel(CharSequence subSequence) {
		// TODO Auto-generated method stub
		return ckGoodsTypeRepository.findByModel(subSequence);
	}
}
