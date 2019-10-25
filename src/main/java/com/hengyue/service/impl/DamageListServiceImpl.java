package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengyue.entity.Goods;
import com.hengyue.entity.DamageList;
import com.hengyue.entity.DamageListGoods;
import com.hengyue.respository.GoodsRepository;
import com.hengyue.respository.GoodsTypeRepository;
import com.hengyue.respository.DamageListGoodsRepository;
import com.hengyue.respository.DamageListRepository;
import com.hengyue.service.DamageListService;
import com.hengyue.utils.MathUtils;
import com.hengyue.utils.StringUtils;
/**
 * 商品报损单业务层实现类
 * @author 章家宝
 *
 */
@Service("damageListService")
public class DamageListServiceImpl implements DamageListService {

	@Resource
	private DamageListRepository damageListRepository;
	
	@Resource
	private DamageListGoodsRepository damageListGoodsRepository;

	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	
	@Override
	public String getTodayMaxDamageNumber() {
		return damageListRepository.getTodayMaxDamageNumber();
	}

	
	@Override
	@Transactional			//加事务
	public void save(DamageList damageList, List<DamageListGoods> damageListGoodsList) {
		for(DamageListGoods damageListGoods : damageListGoodsList) {
			damageListGoods.setType(goodsTypeRepository.getOne(damageListGoods.getTypeId()));//设置类别
			damageListGoods.setDamageList(damageList);//设置商品报损单
			damageListGoodsRepository.save(damageListGoods);
			//修改商品库存	成本均价以及	上次均价
			Goods goods = goodsRepository.getOne(damageListGoods.getGoodsId());
			goods.setInventoryQuantity(goods.getInventoryQuantity() - damageListGoods.getNum());
			goods.setState(2);
			goodsRepository.save(goods);
		}
		damageListRepository.save(damageList);
	}


	@Override
	public List<DamageList> list(DamageList damageList, Direction direction,String... properties) {
		return damageListRepository.findAll(new Specification<DamageList>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<DamageList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(damageList!=null){
					if(StringUtils.isNotEmpty(damageList.getDamageNumber())){
						predicate.getExpressions().add(cb.like(root.get("damageNumber"), "%"+damageList.getDamageNumber().trim()+"%"));
					}
					if(damageList.getSupplier()!=null && damageList.getSupplier().getId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("id"), damageList.getSupplier().getId()));
					}
					if(damageList.getbDamageDate()!=null){
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("damageDate"), damageList.getbDamageDate()));
					}
					if(damageList.geteDamageDate()!=null){
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("damageDate"), damageList.geteDamageDate()));
					}
				}
				return predicate;
			}
		},new Sort(direction, properties));
	}


	@Override
	public DamageList findById(Integer id) {
		return damageListRepository.getOne(id);
	}

}
