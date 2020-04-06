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

import com.hengyue.entity.vo.DepartmentPosition;
import com.hengyue.respository.vo.DepartmentPositionRepository;
import com.hengyue.service.vo.DepartmentPositionService;
@Service("departmentPositionService")
public class DepartmentPositionServiceImpl implements DepartmentPositionService{

	@Resource
	private DepartmentPositionRepository departmentPositionRepository;

	@Override
	public List<DepartmentPosition> list(DepartmentPosition departmentPosition, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<DepartmentPosition> pageDepartmentPosition = departmentPositionRepository.findAll( new Specification<DepartmentPosition>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<DepartmentPosition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(departmentPosition != null) {
					if(departmentPosition != null) {
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageDepartmentPosition.getContent();
	}
	@Override
	public Long getCount(DepartmentPosition departmentPosition) {
		Long count = departmentPositionRepository.count(new Specification<DepartmentPosition>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<DepartmentPosition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(departmentPosition != null) {
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(DepartmentPosition departmentPosition) {
		departmentPositionRepository.save(departmentPosition);
	}
	@Override
	public void delete(Integer id) {
		departmentPositionRepository.deleteById(id);
	}
	@Override
	public DepartmentPosition findById(Integer id) {
		// TODO Auto-generated method stub
		return departmentPositionRepository.findById(id).get();
	}

}
