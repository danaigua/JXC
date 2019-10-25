package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengyue.entity.CustomerReturnListGoods;
import com.hengyue.respository.CustomerReturnListGoodsRepository;
import com.hengyue.respository.CustomerReturnListRepository;
import com.hengyue.service.CustomerReturnListGoodsService;
import com.hengyue.utils.StringUtils;
/**
 * 客户退货商品业务层实现类
 * @author 章家宝
 *
 */
@Service("customerReturnListGoodsService")
public class CustomerReturnListGoodsServiceImpl implements CustomerReturnListGoodsService {

	@Resource
	private CustomerReturnListRepository customerReturnListRepository;
	
	@Resource
	private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;

	@Override
	public List<CustomerReturnListGoods> listByCustomerReturnListId(Integer id) {
		// TODO Auto-generated method stub
		return customerReturnListGoodsRepository.listByCustomerReturnListId(id);
	}

	@Override
	public Integer getTotalByGoodsId(Integer goodsId) {
		// TODO Auto-generated method stub
		return customerReturnListGoodsRepository.getTotalByGoodsId(goodsId) == null ? 0 : customerReturnListGoodsRepository.getTotalByGoodsId(goodsId);
	}

	@Override
	public List<CustomerReturnListGoods> list(CustomerReturnListGoods customerReturnListGoods) {
		// TODO Auto-generated method stub
return customerReturnListGoodsRepository.findAll(new Specification<CustomerReturnListGoods>() {
			
			@Override
			public Predicate toPredicate(Root<CustomerReturnListGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(customerReturnListGoods!=null){
					if(customerReturnListGoods.getType()!=null && customerReturnListGoods.getType().getId()!=null && customerReturnListGoods.getType().getId()!=1){
						predicate.getExpressions().add(cb.equal(root.get("type").get("id"), customerReturnListGoods.getType().getId()));
					}
					if(StringUtils.isNotEmpty(customerReturnListGoods.getCodeOrName())){
						predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%"+customerReturnListGoods.getCodeOrName()+"%"), cb.like(root.get("name"), "%"+customerReturnListGoods.getCodeOrName()+"%")));
					}
				}
				return predicate;
			}
		});
	}
}
