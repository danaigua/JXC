package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengyue.entity.PurchaseListGoods;
import com.hengyue.respository.PurchaseListGoodsRepository;
import com.hengyue.respository.PurchaseListRepository;
import com.hengyue.service.PurchaseListGoodsService;
import com.hengyue.utils.StringUtils;
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

	@Override
	public List<PurchaseListGoods> list(PurchaseListGoods purchaseListGoods) {
		return purchaseListGoodsRepository.findAll(new Specification<PurchaseListGoods>() {
			
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<PurchaseListGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(purchaseListGoods!=null){
					if(purchaseListGoods.getType()!=null && purchaseListGoods.getType().getId()!=null && purchaseListGoods.getType().getId()!=1){
						predicate.getExpressions().add(cb.equal(root.get("type").get("id"), purchaseListGoods.getType().getId()));
					}
					if(StringUtils.isNotEmpty(purchaseListGoods.getCodeOrName())){
						predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%"+purchaseListGoods.getCodeOrName()+"%"), cb.like(root.get("name"), "%"+purchaseListGoods.getCodeOrName()+"%")));
					}
				}
				return predicate;
			}
		});
	}
}
