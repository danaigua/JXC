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

import com.hengyue.entity.Supplier;
import com.hengyue.respository.SupplierRepository;
import com.hengyue.service.SupplierService;
import com.hengyue.utils.StringUtils;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Resource
	private SupplierRepository supplierRepository;
	
	
	@Override
	public List<Supplier> list(Supplier supplier, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Supplier> supplierPage = supplierRepository.findAll(new Specification<Supplier>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(supplier != null) {
					if(StringUtils.isNotEmpty(supplier.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + supplier.getName() + "%"));
					}
				}
				return predicate;
			}
		}, pageable);
		return supplierPage.getContent();
	}

	@Override
	public Long getCount(Supplier supplier) {
		Long count = supplierRepository.count(new Specification<Supplier>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(supplier != null) {
					if(StringUtils.isNotEmpty(supplier.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + supplier.getName() + "%"));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void save(Supplier supplier) {
		// TODO Auto-generated method stub
		supplierRepository.save(supplier);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		supplierRepository.deleteById(id);
	}

	@Override
	public Supplier findById(Integer id) {
		// TODO Auto-generated method stub
		return supplierRepository.getOne(id);
	}

	@Override
	public Supplier findBySupplierName(String supplierName) {
		// TODO Auto-generated method stub
		return supplierRepository.findBySupplierName(supplierName);
	}

	@Override
	public List<Supplier> findByName(String name) {
		// TODO Auto-generated method stub
		return supplierRepository.findByName(name);
	}

}
