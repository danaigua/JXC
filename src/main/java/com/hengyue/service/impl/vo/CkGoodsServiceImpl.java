package com.hengyue.service.impl.vo;

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

import com.hengyue.entity.vo.CkGoods;
import com.hengyue.respository.vo.CkGoodsRepository;
import com.hengyue.service.vo.CkGoodsService;
import com.hengyue.utils.StringUtils;

/**
 * 商品业务层接口实现类
 * 
 * @author 章家宝
 *
 */
@Service("ckGoodsService")
public class CkGoodsServiceImpl implements CkGoodsService {

	@Resource
	private CkGoodsRepository ckGoodsRepository;

	@Override
	public List<CkGoods> findByTypeId(int typeId) {
		return ckGoodsRepository.findByTypeId(typeId);
	}

	@Override
	public List<CkGoods> list(CkGoods ckCkGoods, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
		Page<CkGoods> ckCkGoodsPage = ckGoodsRepository.findAll(new Specification<CkGoods>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CkGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (ckCkGoods != null) {
					if (StringUtils.isNotEmpty(ckCkGoods.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + ckCkGoods.getName() + "%"));
					}
					if (ckCkGoods.getType() != null && ckCkGoods.getType().getId() != null && ckCkGoods.getType().getId() != 1) {
						predicate.getExpressions().add(cb.equal(root.get("type").get("id"), ckCkGoods.getType().getId()));
					}
					if (StringUtils.isNotEmpty(ckCkGoods.getCodeOrName())) {
						predicate.getExpressions()
								.add(cb.or(cb.like(root.get("code"), "%" + ckCkGoods.getCodeOrName() + "%"),
										cb.like(root.get("name"), "%" + ckCkGoods.getCodeOrName() + "%")));
					}
				}
				return predicate;
			}
		}, pageable);
		return ckCkGoodsPage.getContent();
	}

	@Override
	public Long getCount(CkGoods ckCkGoods) {
		Long count = ckGoodsRepository.count(new Specification<CkGoods>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CkGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (ckCkGoods != null) {
					if (StringUtils.isNotEmpty(ckCkGoods.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + ckCkGoods.getName() + "%"));
					}
					if (ckCkGoods.getType() != null && ckCkGoods.getType().getId() != null && ckCkGoods.getType().getId() != 1) {
						predicate.getExpressions().add(cb.equal(root.get("type").get("id"), ckCkGoods.getType().getId()));
					}
					if (StringUtils.isNotEmpty(ckCkGoods.getCodeOrName())) {
						predicate.getExpressions()
								.add(cb.or(cb.like(root.get("code"), "%" + ckCkGoods.getCodeOrName() + "%"),
										cb.like(root.get("name"), "%" + ckCkGoods.getCodeOrName() + "%")));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void save(CkGoods ckCkGoods) {
		// TODO Auto-generated method stub
		ckGoodsRepository.save(ckCkGoods);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		ckGoodsRepository.deleteById(id);
	}

	@Override
	public CkGoods findById(Integer id) {
		// TODO Auto-generated method stub
		return ckGoodsRepository.getOne(id);
	}

	@Override
	public String getMaxCkGoodsCode() {
		// TODO Auto-generated method stub
		return ckGoodsRepository.getMaxCkGoodsCode();
	}

	@Override
	public List<CkGoods> listNoInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize,
			Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
		Page<CkGoods> ckCkGoodsPage = ckGoodsRepository.findAll(new Specification<CkGoods>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CkGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotEmpty(codeOrName)) {
					predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%" + codeOrName + "%"),
							cb.like(root.get("name"), "%" + codeOrName + "%")));
				}
				predicate.getExpressions().add(cb.equal(root.get("inventoryQuantity"), 0));// 库存为0
				return predicate;
			}
		}, pageable);
		return ckCkGoodsPage.getContent();
	}

	@Override
	public Long getCountNoInventoryQuantityByCodeOrName(String codeOrName) {
		Long count = ckGoodsRepository.count(new Specification<CkGoods>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CkGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
	public List<CkGoods> listHasInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize,
			Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
		Page<CkGoods> ckCkGoodsPage = ckGoodsRepository.findAll(new Specification<CkGoods>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CkGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotEmpty(codeOrName)) {
					predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%" + codeOrName + "%"),
							cb.like(root.get("name"), "%" + codeOrName + "%")));
				}
				predicate.getExpressions().add(cb.greaterThan(root.get("inventoryQuantity"), 0));// 库存为大于0
				return predicate;
			}
		}, pageable);
		return ckCkGoodsPage.getContent();
	}

	@Override
	public Long getCountHasInventoryQuantityByCodeOrName(String codeOrName) {
		Long count = ckGoodsRepository.count(new Specification<CkGoods>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CkGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
	public List<CkGoods> listAlarm() {
		// TODO Auto-generated method stub
		return ckGoodsRepository.listAlarm();
	}

	@Override
	public CkGoods findBymodelName(String string) {
		// TODO Auto-generated method stub
		return ckGoodsRepository.findBymodelName(string);
	}

}
