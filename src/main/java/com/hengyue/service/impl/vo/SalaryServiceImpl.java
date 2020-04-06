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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengyue.entity.vo.Salary;
import com.hengyue.respository.vo.SalaryRepository;
import com.hengyue.service.vo.SalaryService;
import com.hengyue.utils.StringUtils;
@Service("salaryService")
public class SalaryServiceImpl implements SalaryService{

	@Resource
	private SalaryRepository salaryRepository;

	@Override
	public List<Salary> list(Salary salary, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Salary> pageSalary = salaryRepository.findAll( new Specification<Salary>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Salary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(salary != null) {
					if(salary != null) {
						if(StringUtils.isNotEmpty(salary.getName())) {
							predicate.getExpressions().add(cb.like(root.get("name"), "%" + salary.getName() + "%"));
						}
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageSalary.getContent();
	}
	@Override
	public Long getCount(Salary salary) {
		Long count = salaryRepository.count(new Specification<Salary>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Salary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(salary != null) {
					if(StringUtils.isNotEmpty(salary.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + salary.getName() + "%"));
					}
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(Salary salary) {
		salaryRepository.save(salary);
	}
	@Override
	public void delete(Integer id) {
		salaryRepository.deleteById(id);
	}
	@Override
	public Salary findById(Integer id) {
		// TODO Auto-generated method stub
		return salaryRepository.findById(id).get();
	}
	@Override
	public List<Salary> listAll() {
		return salaryRepository.findAll();
	}
	

}
