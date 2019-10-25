package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengyue.entity.SaleListGoods;
import com.hengyue.respository.SaleListGoodsRepository;
import com.hengyue.respository.SaleListRepository;
import com.hengyue.service.SaleListGoodsService;
import com.hengyue.utils.StringUtils;

/**
 * 销售单商品业务层实现类
 * 
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
		return saleListGoodsRepository.getTotalByGoodsId(goodsId) == null ? 0
				: saleListGoodsRepository.getTotalByGoodsId(goodsId);
	}

	@Override
	public List<SaleListGoods> list(SaleListGoods saleListGoods) {
		// TODO Auto-generated method stub
		return saleListGoodsRepository.findAll(new Specification<SaleListGoods>() {

			@Override
			public Predicate toPredicate(Root<SaleListGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (saleListGoods != null) {
					if (saleListGoods.getType() != null && saleListGoods.getType().getId() != null
							&& saleListGoods.getType().getId() != 1) {
						predicate.getExpressions()
								.add(cb.equal(root.get("type").get("id"), saleListGoods.getType().getId()));
					}
					if (StringUtils.isNotEmpty(saleListGoods.getCodeOrName())) {
						predicate.getExpressions()
								.add(cb.or(cb.like(root.get("code"), "%" + saleListGoods.getCodeOrName() + "%"),
										cb.like(root.get("name"), "%" + saleListGoods.getCodeOrName() + "%")));
					}
				}
				return predicate;
			}
		});
	}
}
