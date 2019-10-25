package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengyue.entity.ReturnListGoods;
import com.hengyue.respository.ReturnListGoodsRepository;
import com.hengyue.respository.ReturnListRepository;
import com.hengyue.service.ReturnListGoodsService;
import com.hengyue.utils.StringUtils;
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

	@Override
	public List<ReturnListGoods> list(ReturnListGoods returnListGoods) {
		// TODO Auto-generated method stub
		return returnListGoodsRepository.findAll(new Specification<ReturnListGoods>() {

			@Override
			public Predicate toPredicate(Root<ReturnListGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (returnListGoods != null) {
					if (returnListGoods.getType() != null && returnListGoods.getType().getId() != null
							&& returnListGoods.getType().getId() != 1) {
						predicate.getExpressions()
								.add(cb.equal(root.get("type").get("id"), returnListGoods.getType().getId()));
					}
					if (StringUtils.isNotEmpty(returnListGoods.getCodeOrName())) {
						predicate.getExpressions()
								.add(cb.or(cb.like(root.get("code"), "%" + returnListGoods.getCodeOrName() + "%"),
										cb.like(root.get("name"), "%" + returnListGoods.getCodeOrName() + "%")));
					}
				}
				return predicate;
			}
		});
	}
}
