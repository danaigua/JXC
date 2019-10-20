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
import com.hengyue.entity.SaleList;
import com.hengyue.entity.SaleListGoods;
import com.hengyue.respository.GoodsRepository;
import com.hengyue.respository.GoodsTypeRepository;
import com.hengyue.respository.SaleListGoodsRepository;
import com.hengyue.respository.SaleListRepository;
import com.hengyue.service.SaleListService;
import com.hengyue.utils.StringUtils;
/**
 * 销售单业务层实现类
 * @author 章家宝
 *
 */
@Service("saleListService")
public class SaleListServiceImpl implements SaleListService {

	@Resource
	private SaleListRepository saleListRepository;
	
	@Resource
	private SaleListGoodsRepository saleListGoodsRepository;

	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	
	@Override
	public String getTodayMaxSaleNumber() {
		return saleListRepository.getTodayMaxSaleNumber();
	}

	
	@Override
	@Transactional			//加事务
	public void save(SaleList saleList, List<SaleListGoods> saleListGoodsList) {
		for(SaleListGoods saleListGoods : saleListGoodsList) {
			saleListGoods.setType(goodsTypeRepository.getOne(saleListGoods.getTypeId()));//设置类别
			saleListGoods.setSaleList(saleList);//设置销售单
			saleListGoodsRepository.save(saleListGoods);
			//修改商品库存	成本均价以及	上次均价
			Goods goods = goodsRepository.getOne(saleListGoods.getGoodsId());
			
			//均价 = （库存里面单价*数量 + 上次库存*上次价格）/总数量
			goods.setInventoryQuantity(goods.getInventoryQuantity() - saleListGoods.getNum());
			goodsRepository.save(goods);
		}
		saleListRepository.save(saleList);
	}


	@Override
	public List<SaleList> list(SaleList saleList, Direction direction,String... properties) {
		return saleListRepository.findAll(new Specification<SaleList>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<SaleList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(saleList!=null){
					if(StringUtils.isNotEmpty(saleList.getSaleNumber())){
						predicate.getExpressions().add(cb.like(root.get("saleNumber"), "%"+saleList.getSaleNumber().trim()+"%"));
					}
					if(saleList.getCustomer()!=null && saleList.getCustomer().getId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("id"), saleList.getCustomer().getId()));
					}
					if(saleList.getState()!=null){
						predicate.getExpressions().add(cb.equal(root.get("state"), saleList.getState()));
					}
					if(saleList.getbSaleDate()!=null){
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("saleDate"), saleList.getbSaleDate()));
					}
					if(saleList.geteSaleDate()!=null){
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("saleDate"), saleList.geteSaleDate()));
					}
				}
				return predicate;
			}
		},new Sort(direction, properties));
	}


	@Override
	public SaleList findById(Integer id) {
		// TODO Auto-generated method stub
		return saleListRepository.getOne(id);
	}


	@Override
	@Transactional
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		saleListGoodsRepository.deleteBySaleListId(id);
		saleListRepository.deleteById(id);
	}
}
