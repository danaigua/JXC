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
import com.hengyue.entity.OverflowList;
import com.hengyue.entity.OverflowListGoods;
import com.hengyue.respository.GoodsRepository;
import com.hengyue.respository.GoodsTypeRepository;
import com.hengyue.respository.OverflowListGoodsRepository;
import com.hengyue.respository.OverflowListRepository;
import com.hengyue.service.OverflowListService;
import com.hengyue.utils.StringUtils;
/**
 * 商品报溢单业务层实现类
 * @author 章家宝
 *
 */
@Service("overflowListService")
public class OverflowListServiceImpl implements OverflowListService {

	@Resource
	private OverflowListRepository overflowListRepository;
	
	@Resource
	private OverflowListGoodsRepository overflowListGoodsRepository;

	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	
	@Override
	public String getTodayMaxOverflowNumber() {
		return overflowListRepository.getTodayMaxOverflowNumber();
	}

	
	@Override
	@Transactional			//加事务
	public void save(OverflowList overflowList, List<OverflowListGoods> overflowListGoodsList) {
		for(OverflowListGoods overflowListGoods : overflowListGoodsList) {
			overflowListGoods.setType(goodsTypeRepository.getOne(overflowListGoods.getTypeId()));//设置类别
			overflowListGoods.setOverflowList(overflowList);//设置商品报溢单
			overflowListGoodsRepository.save(overflowListGoods);
			//修改商品库存	成本均价以及	上次均价
			Goods goods = goodsRepository.getOne(overflowListGoods.getGoodsId());
			goods.setInventoryQuantity(goods.getInventoryQuantity() + overflowListGoods.getNum());
			goods.setState(2);
			goodsRepository.save(goods);
		}
		overflowListRepository.save(overflowList);
	}


	@Override
	public List<OverflowList> list(OverflowList overflowList, Direction direction,String... properties) {
		return overflowListRepository.findAll(new Specification<OverflowList>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<OverflowList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(overflowList!=null){
					if(StringUtils.isNotEmpty(overflowList.getOverflowNumber())){
						predicate.getExpressions().add(cb.like(root.get("overflowNumber"), "%"+overflowList.getOverflowNumber().trim()+"%"));
					}
					if(overflowList.getSupplier()!=null && overflowList.getSupplier().getId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("id"), overflowList.getSupplier().getId()));
					}
					if(overflowList.getbOverflowDate()!=null){
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("overflowDate"), overflowList.getbOverflowDate()));
					}
					if(overflowList.geteOverflowDate()!=null){
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("overflowDate"), overflowList.geteOverflowDate()));
					}
				}
				return predicate;
			}
		},new Sort(direction, properties));
	}


	@Override
	public OverflowList findById(Integer id) {
		return overflowListRepository.getOne(id);
	}

}
