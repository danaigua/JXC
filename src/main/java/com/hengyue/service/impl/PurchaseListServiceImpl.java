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
import com.hengyue.entity.PurchaseList;
import com.hengyue.entity.PurchaseListGoods;
import com.hengyue.respository.GoodsRepository;
import com.hengyue.respository.GoodsTypeRepository;
import com.hengyue.respository.PurchaseListGoodsRepository;
import com.hengyue.respository.PurchaseListRepository;
import com.hengyue.service.PurchaseListService;
import com.hengyue.utils.MathUtils;
import com.hengyue.utils.StringUtils;
/**
 * 进货单业务层实现类
 * @author 章家宝
 *
 */
@Service("purchaseListService")
public class PurchaseListServiceImpl implements PurchaseListService {

	@Resource
	private PurchaseListRepository purchaseListRepository;
	
	@Resource
	private PurchaseListGoodsRepository purchaseListGoodsRepository;

	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	
	@Override
	public String getTodayMaxPurchaseNumber() {
		return purchaseListRepository.getTodayMaxPurchaseNumber();
	}

	
	@Override
	@Transactional			//加事务
	public void save(PurchaseList purchaseList, List<PurchaseListGoods> purchaseListGoodsList) {
		for(PurchaseListGoods purchaseListGoods : purchaseListGoodsList) {
			purchaseListGoods.setType(goodsTypeRepository.getOne(purchaseListGoods.getTypeId()));//设置类别
			purchaseListGoods.setPurchaseList(purchaseList);//设置进货单
			purchaseListGoodsRepository.save(purchaseListGoods);
			//修改商品库存	成本均价以及	上次均价
			Goods goods = goodsRepository.getOne(purchaseListGoods.getGoodsId());
			
			//均价 = （库存里面单价*数量 + 上次库存*上次价格）/总数量
			Float f = (goods.getPurchasingPrice() * goods.getInventoryQuantity() 
					+ purchaseListGoods.getPrice() * purchaseListGoods.getNum())
					/ (goods.getInventoryQuantity() + purchaseListGoods.getNum());
			goods.setPurchasingPrice(MathUtils.format2Bit(f));
			goods.setInventoryQuantity(goods.getInventoryQuantity() + purchaseListGoods.getNum());
			goods.setLastPurchasingPrice(purchaseListGoods.getPrice());
			goods.setState(2);
			goodsRepository.save(goods);
		}
		purchaseListRepository.save(purchaseList);
	}


	@Override
	public List<PurchaseList> list(PurchaseList purchaseList, Direction direction,String... properties) {
		return purchaseListRepository.findAll(new Specification<PurchaseList>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PurchaseList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(purchaseList!=null){
					if(StringUtils.isNotEmpty(purchaseList.getPurchaseNumber())){
						predicate.getExpressions().add(cb.like(root.get("purchaseNumber"), "%"+purchaseList.getPurchaseNumber().trim()+"%"));
					}
					if(purchaseList.getSupplier()!=null && purchaseList.getSupplier().getId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("id"), purchaseList.getSupplier().getId()));
					}
					if(purchaseList.getState()!=null){
						predicate.getExpressions().add(cb.equal(root.get("state"), purchaseList.getState()));
					}
					if(purchaseList.getbPurchaseDate()!=null){
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("purchaseDate"), purchaseList.getbPurchaseDate()));
					}
					if(purchaseList.getePurchaseDate()!=null){
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("purchaseDate"), purchaseList.getePurchaseDate()));
					}
				}
				return predicate;
			}
		},new Sort(direction, properties));
	}


	@Override
	public PurchaseList findById(Integer id) {
		// TODO Auto-generated method stub
		return purchaseListRepository.getOne(id);
	}


	@Override
	@Transactional
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		purchaseListGoodsRepository.deleteByPurchaseListId(id);
		purchaseListRepository.deleteById(id);
	}


	@Override
	public void update(PurchaseList purchaseList) {
		// TODO Auto-generated method stub
		purchaseListRepository.save(purchaseList);
	}
}
