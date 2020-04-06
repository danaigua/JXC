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

import com.hengyue.entity.vo.Department;
import com.hengyue.respository.vo.DepartmentRepository;
import com.hengyue.service.vo.DepartmentService;
import com.hengyue.utils.StringUtils;
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService{

	@Resource
	private DepartmentRepository departmentRepository;

	@Override
	public List<Department> list(Department department, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Department> pageDepartment = departmentRepository.findAll( new Specification<Department>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(department != null) {
					if(department != null) {
						if(StringUtils.isNotEmpty(department.getName())) {
							predicate.getExpressions().add(cb.like(root.get("name"), "%" + department.getName() + "%"));
						}
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageDepartment.getContent();
	}
	@Override
	public Long getCount(Department department) {
		Long count = departmentRepository.count(new Specification<Department>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(department != null) {
					if(StringUtils.isNotEmpty(department.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + department.getName() + "%"));
					}
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(Department department) {
		departmentRepository.save(department);
	}
	@Override
	public void delete(Integer id) {
		departmentRepository.deleteById(id);
	}
	@Override
	public Department findById(Integer id) {
		// TODO Auto-generated method stub
		return departmentRepository.findById(id).get();
	}
	@Override
	public List<Department> listAll() {
		return departmentRepository.findAll();
	}

}
