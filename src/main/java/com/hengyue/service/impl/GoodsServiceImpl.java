package com.hengyue.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengyue.entity.Goods;
import com.hengyue.respository.GoodsRepository;
import com.hengyue.service.GoodsService;
import com.hengyue.utils.StringUtils;

/**
 * 商品业务层接口实现类
 * 
 * @author 章家宝
 *
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

	@Resource
	private GoodsRepository goodsRepository;

	@Override
	public List<Goods> findByTypeId(int typeId) {
		return goodsRepository.findByTypeId(typeId);
	}

	@Override
	public List<Goods> list(Goods goods, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
		Page<Goods> goodsPage = goodsRepository.findAll(new Specification<Goods>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (goods != null) {
					if (StringUtils.isNotEmpty(goods.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + goods.getName() + "%"));
					}
					if (goods.getType() != null && goods.getType().getId() != null && goods.getType().getId() != 1) {
						predicate.getExpressions().add(cb.equal(root.get("type").get("id"), goods.getType().getId()));
					}
					if (StringUtils.isNotEmpty(goods.getCodeOrName())) {
						predicate.getExpressions()
								.add(cb.or(cb.like(root.get("code"), "%" + goods.getCodeOrName() + "%"),
										cb.like(root.get("name"), "%" + goods.getCodeOrName() + "%")));
					}
				}
				return predicate;
			}
		}, pageable);
		return goodsPage.getContent();
	}

	@Override
	public Long getCount(Goods goods) {
		Long count = goodsRepository.count(new Specification<Goods>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (goods != null) {
					if (StringUtils.isNotEmpty(goods.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + goods.getName() + "%"));
					}
					if (goods.getType() != null && goods.getType().getId() != null && goods.getType().getId() != 1) {
						predicate.getExpressions().add(cb.equal(root.get("type").get("id"), goods.getType().getId()));
					}
					if (StringUtils.isNotEmpty(goods.getCodeOrName())) {
						predicate.getExpressions()
								.add(cb.or(cb.like(root.get("code"), "%" + goods.getCodeOrName() + "%"),
										cb.like(root.get("name"), "%" + goods.getCodeOrName() + "%")));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void save(Goods goods) {
		// TODO Auto-generated method stub
		goodsRepository.save(goods);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		goodsRepository.deleteById(id);
	}

	@Override
	public Goods findById(Integer id) {
		// TODO Auto-generated method stub
		return goodsRepository.getOne(id);
	}

	@Override
	public String getMaxGoodsCode() {
		// TODO Auto-generated method stub
		return goodsRepository.getMaxGoodsCode();
	}

	@Override
	public List<Goods> listNoInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize,
			Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
		Page<Goods> goodsPage = goodsRepository.findAll(new Specification<Goods>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotEmpty(codeOrName)) {
					predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%" + codeOrName + "%"),
							cb.like(root.get("name"), "%" + codeOrName + "%")));
				}
				predicate.getExpressions().add(cb.equal(root.get("inventoryQuantity"), 0));// 库存为0
				return predicate;
			}
		}, pageable);
		return goodsPage.getContent();
	}

	@Override
	public Long getCountNoInventoryQuantityByCodeOrName(String codeOrName) {
		Long count = goodsRepository.count(new Specification<Goods>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotEmpty(codeOrName)) {
					predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%" + codeOrName + "%"),
							cb.like(root.get("name"), "%" + codeOrName + "%")));
				}
				predicate.getExpressions().add(cb.equal(root.get("inventoryQuantity"), 0));// 库存为0
				return predicate;
			}
		});

		return count;
	}

	@Override
	public List<Goods> listHasInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize,
			Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
		Page<Goods> goodsPage = goodsRepository.findAll(new Specification<Goods>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotEmpty(codeOrName)) {
					predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%" + codeOrName + "%"),
							cb.like(root.get("name"), "%" + codeOrName + "%")));
				}
				predicate.getExpressions().add(cb.greaterThan(root.get("inventoryQuantity"), 0));// 库存为大于0
				return predicate;
			}
		}, pageable);
		return goodsPage.getContent();
	}

	@Override
	public Long getCountHasInventoryQuantityByCodeOrName(String codeOrName) {
		Long count = goodsRepository.count(new Specification<Goods>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotEmpty(codeOrName)) {
					predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%" + codeOrName + "%"),
							cb.like(root.get("name"), "%" + codeOrName + "%")));
				}
				predicate.getExpressions().add(cb.greaterThan(root.get("inventoryQuantity"), 0));// 库存为大于0
				return predicate;
			}
		});
		return count;
	}

	@Override
	public List<Goods> listAlarm() {
		// TODO Auto-generated method stub
		return goodsRepository.listAlarm();
	}

}
