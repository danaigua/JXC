package com.hengyue.service.impl.vo;

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
import com.hengyue.entity.vo.CkCustomerReturnList;
import com.hengyue.entity.vo.CkCustomerReturnListGoods;
import com.hengyue.entity.vo.CkGoods;
import com.hengyue.respository.GoodsRepository;
import com.hengyue.respository.vo.CkCustomerReturnListGoodsRepository;
import com.hengyue.respository.vo.CkCustomerReturnListRepository;
import com.hengyue.respository.vo.CkGoodsRepository;
import com.hengyue.respository.vo.CkGoodsTypeRepository;
import com.hengyue.service.vo.CkCustomerReturnListService;
import com.hengyue.utils.StringUtils;
/**
 * 客户退货业务层实现类
 * @author 章家宝
 *
 */
@Service("ckCustomerReturnListService")
public class CkCustomerReturnListServiceImpl implements CkCustomerReturnListService {

	@Resource
	private CkCustomerReturnListRepository ckCustomerReturnListRepository;
	
	@Resource
	private CkCustomerReturnListGoodsRepository ckCustomerReturnListGoodsRepository;

	@Resource
	private CkGoodsTypeRepository ckGoodsTypeRepository;
	
	@Resource
	private CkGoodsRepository ckGoodsRepository;
	
	
	@Override
	public String getTodayMaxCkCustomerReturnNumber() {
		return ckCustomerReturnListRepository.getTodayMaxCkCustomerReturnNumber();
	}

	
	@Override
	@Transactional			//加事务
	public void save(CkCustomerReturnList ckCustomerReturnList, List<CkCustomerReturnListGoods> ckCustomerReturnListGoodsList) {
		for(CkCustomerReturnListGoods ckCustomerReturnListGoods : ckCustomerReturnListGoodsList) {
			ckCustomerReturnListGoods.setType(ckGoodsTypeRepository.getOne(ckCustomerReturnListGoods.getTypeId()));//设置类别
			ckCustomerReturnListGoods.setCustomerReturnList(ckCustomerReturnList);//设置客户退货
			ckCustomerReturnListGoodsRepository.save(ckCustomerReturnListGoods);
			//修改商品库存	成本均价以及	上次均价
			CkGoods ckGoods = ckGoodsRepository.getOne(ckCustomerReturnListGoods.getGoodsId());
			
			//均价 = （库存里面单价*数量 + 上次库存*上次价格）/总数量
			ckGoods.setInventoryQuantity(ckGoods.getInventoryQuantity() + ckCustomerReturnListGoods.getNum());
			ckGoodsRepository.save(ckGoods);
		}
		ckCustomerReturnListRepository.save(ckCustomerReturnList);
	}


	@Override
	public List<CkCustomerReturnList> list(CkCustomerReturnList ckCustomerReturnList, Direction direction,String... properties) {
		return ckCustomerReturnListRepository.findAll(new Specification<CkCustomerReturnList>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CkCustomerReturnList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(ckCustomerReturnList!=null){
//					if(StringUtils.isNotEmpty(ckCustomerReturnList.getCustomerReturnNumber())){
//						predicate.getExpressions().add(cb.like(root.get("customerReturnNumber"), "%"+ckCustomerReturnList.getCustomerReturnNumber().trim()+"%"));
//					}
					if(ckCustomerReturnList.getState()!=null){
						predicate.getExpressions().add(cb.equal(root.get("state"), ckCustomerReturnList.getState()));
					}
					if(ckCustomerReturnList.getbCkCustomerReturnDate()!=null){
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("customerReturnDate"), ckCustomerReturnList.getbCkCustomerReturnDate()));
					}
					if(ckCustomerReturnList.geteCkCustomerReturnDate()!=null){
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("customerReturnDate"), ckCustomerReturnList.geteCkCustomerReturnDate()));
					}
				}
				return predicate;
			}
		},new Sort(direction, properties));
	}


	@Override
	public CkCustomerReturnList findById(Integer id) {
		return ckCustomerReturnListRepository.getOne(id);
	}


	@Override
	@Transactional
	public void delete(Integer id) {
		ckCustomerReturnListGoodsRepository.deleteByCkCustomerReturnListId(id);
		ckCustomerReturnListRepository.deleteById(id);
	}


	@Override
	public void update(CkCustomerReturnList ckCustomerReturnList) {
		ckCustomerReturnListRepository.save(ckCustomerReturnList);
	}
}
