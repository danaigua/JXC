package com.hengyue.service.impl.vo;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengyue.entity.vo.CkCustomerReturnListGoods;
import com.hengyue.respository.vo.CkCustomerReturnListGoodsRepository;
import com.hengyue.respository.vo.CkCustomerReturnListRepository;
import com.hengyue.service.vo.CkCustomerReturnListGoodsService;
import com.hengyue.utils.StringUtils;

/**
 * 客户退货商品业务层实现类
 * 
 * @author 章家宝
 *
 */
@Service("ckCustomerReturnListGoodsService")
public class CkCustomerReturnListGoodsServiceImpl implements CkCustomerReturnListGoodsService {

	@Resource
	private CkCustomerReturnListRepository ckCustomerReturnListRepository;

	@Resource
	private CkCustomerReturnListGoodsRepository ckCustomerReturnListGoodsRepository;

	@Override
	public List<CkCustomerReturnListGoods> listByCkCustomerReturnListId(Integer id) {
		return ckCustomerReturnListGoodsRepository.listByCkCustomerReturnListId(id);
	}

	@Override
	public Integer getTotalByGoodsId(Integer goodsId) {
		return ckCustomerReturnListGoodsRepository.getTotalByGoodsId(goodsId) == null ? 0
				: ckCustomerReturnListGoodsRepository.getTotalByGoodsId(goodsId);
	}

	@Override
	public List<CkCustomerReturnListGoods> list(CkCustomerReturnListGoods ckCustomerReturnListGoods) {
		return ckCustomerReturnListGoodsRepository.findAll(new Specification<CkCustomerReturnListGoods>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CkCustomerReturnListGoods> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (ckCustomerReturnListGoods != null) {
					if (ckCustomerReturnListGoods.getType() != null
							&& ckCustomerReturnListGoods.getType().getId() != null
							&& ckCustomerReturnListGoods.getType().getId() != 1) {
						predicate.getExpressions()
								.add(cb.equal(root.get("type").get("id"), ckCustomerReturnListGoods.getType().getId()));
					}
					if (StringUtils.isNotEmpty(ckCustomerReturnListGoods.getCodeOrName())) {
						predicate.getExpressions().add(cb.or(
								cb.like(root.get("code"), "%" + ckCustomerReturnListGoods.getCodeOrName() + "%"),
								cb.like(root.get("name"), "%" + ckCustomerReturnListGoods.getCodeOrName() + "%")));
					}
				}
				return predicate;
			}
		});
	}
}
