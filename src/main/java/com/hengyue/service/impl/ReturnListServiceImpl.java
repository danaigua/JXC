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
import com.hengyue.entity.ReturnList;
import com.hengyue.entity.ReturnListGoods;
import com.hengyue.respository.GoodsRepository;
import com.hengyue.respository.GoodsTypeRepository;
import com.hengyue.respository.ReturnListGoodsRepository;
import com.hengyue.respository.ReturnListRepository;
import com.hengyue.service.ReturnListService;
import com.hengyue.utils.StringUtils;
/**
 * 进货单业务层实现类
 * @author 章家宝
 *
 */
@Service("returnListService")
public class ReturnListServiceImpl implements ReturnListService {

	@Resource
	private ReturnListRepository returnListRepository;
	
	@Resource
	private ReturnListGoodsRepository returnListGoodsRepository;

	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	
	@Override
	public String getTodayMaxReturnNumber() {
		return returnListRepository.getTodayMaxRuturnNumber();
	}

	
	@Override
	@Transactional			//加事务
	public void save(ReturnList returnList, List<ReturnListGoods> returnListGoodsList) {
		for(ReturnListGoods returnListGoods : returnListGoodsList) {
			returnListGoods.setType(goodsTypeRepository.getOne(returnListGoods.getTypeId()));//设置类别
			returnListGoods.setReturnList(returnList);//设置退货单
			returnListGoodsRepository.save(returnListGoods);
			//修改商品库存	成本均价以及	上次均价
			Goods goods = goodsRepository.getOne(returnListGoods.getGoodsId());
			
			goods.setInventoryQuantity(goods.getInventoryQuantity() - returnListGoods.getNum());
			goods.setState(2);
			goodsRepository.save(goods);
		}
		returnListRepository.save(returnList);
	}


	@Override
	public List<ReturnList> list(ReturnList returnList, Direction direction, String... properties) {
		
		
		return returnListRepository.findAll(new Specification<ReturnList>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<ReturnList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(returnList!=null){
					if(StringUtils.isNotEmpty(returnList.getReturnNumber())){
						predicate.getExpressions().add(cb.like(root.get("returnNumber"), "%"+returnList.getReturnNumber().trim()+"%"));
					}
					if(returnList.getSupplier()!=null && returnList.getSupplier().getId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("id"), returnList.getSupplier().getId()));
					}
					if(returnList.getState()!=null){
						predicate.getExpressions().add(cb.equal(root.get("state"), returnList.getState()));
					}
					if(returnList.getbReturnDate()!=null){
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("returnDate"), returnList.getbReturnDate()));
					}
					if(returnList.geteReturnDate()!=null){
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("returnDate"), returnList.geteReturnDate()));
					}
				}
				return predicate;
			}
		},new Sort(direction, properties));
	}


	@Override
	@Transactional
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		returnListGoodsRepository.deleteByReturnListId(id);
		returnListRepository.deleteById(id);
	}


	@Override
	public ReturnList findById(Integer id) {
		// TODO Auto-generated method stub
		return returnListRepository.getOne(id);
	}
}
