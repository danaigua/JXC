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
import com.hengyue.entity.CustomerReturnList;
import com.hengyue.entity.CustomerReturnListGoods;
import com.hengyue.respository.GoodsRepository;
import com.hengyue.respository.GoodsTypeRepository;
import com.hengyue.respository.CustomerReturnListGoodsRepository;
import com.hengyue.respository.CustomerReturnListRepository;
import com.hengyue.service.CustomerReturnListService;
import com.hengyue.utils.StringUtils;
/**
 * 客户退货业务层实现类
 * @author 章家宝
 *
 */
@Service("customerReturnListService")
public class CustomerReturnListServiceImpl implements CustomerReturnListService {

	@Resource
	private CustomerReturnListRepository customerReturnListRepository;
	
	@Resource
	private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;

	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	
	@Override
	public String getTodayMaxCustomerReturnNumber() {
		return customerReturnListRepository.getTodayMaxCustomerReturnNumber();
	}

	
	@Override
	@Transactional			//加事务
	public void save(CustomerReturnList customerReturnList, List<CustomerReturnListGoods> customerReturnListGoodsList) {
		for(CustomerReturnListGoods customerReturnListGoods : customerReturnListGoodsList) {
			customerReturnListGoods.setType(goodsTypeRepository.getOne(customerReturnListGoods.getTypeId()));//设置类别
			customerReturnListGoods.setCustomerReturnList(customerReturnList);//设置客户退货
			customerReturnListGoodsRepository.save(customerReturnListGoods);
			//修改商品库存	成本均价以及	上次均价
			Goods goods = goodsRepository.getOne(customerReturnListGoods.getGoodsId());
			
			//均价 = （库存里面单价*数量 + 上次库存*上次价格）/总数量
			goods.setInventoryQuantity(goods.getInventoryQuantity() + customerReturnListGoods.getNum());
			goodsRepository.save(goods);
		}
		customerReturnListRepository.save(customerReturnList);
	}


	@Override
	public List<CustomerReturnList> list(CustomerReturnList customerReturnList, Direction direction,String... properties) {
		return customerReturnListRepository.findAll(new Specification<CustomerReturnList>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CustomerReturnList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(customerReturnList!=null){
					if(StringUtils.isNotEmpty(customerReturnList.getCustomerReturnNumber())){
						predicate.getExpressions().add(cb.like(root.get("customerReturnNumber"), "%"+customerReturnList.getCustomerReturnNumber().trim()+"%"));
					}
					if(customerReturnList.getCustomer()!=null && customerReturnList.getCustomer().getId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("id"), customerReturnList.getCustomer().getId()));
					}
					if(customerReturnList.getState()!=null){
						predicate.getExpressions().add(cb.equal(root.get("state"), customerReturnList.getState()));
					}
					if(customerReturnList.getbCustomerReturnDate()!=null){
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("customerReturnDate"), customerReturnList.getbCustomerReturnDate()));
					}
					if(customerReturnList.geteCustomerReturnDate()!=null){
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("customerReturnDate"), customerReturnList.geteCustomerReturnDate()));
					}
				}
				return predicate;
			}
		},new Sort(direction, properties));
	}


	@Override
	public CustomerReturnList findById(Integer id) {
		// TODO Auto-generated method stub
		return customerReturnListRepository.getOne(id);
	}


	@Override
	@Transactional
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		customerReturnListGoodsRepository.deleteByCustomerReturnListId(id);
		customerReturnListRepository.deleteById(id);
	}


	@Override
	public void update(CustomerReturnList customerReturnList) {
		// TODO Auto-generated method stub
		customerReturnListRepository.save(customerReturnList);
	}
}
