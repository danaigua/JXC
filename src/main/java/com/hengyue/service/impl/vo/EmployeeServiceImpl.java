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

import com.hengyue.entity.vo.Employee;
import com.hengyue.respository.vo.EmployeeRepository;
import com.hengyue.service.vo.EmployeeService;
import com.hengyue.utils.StringUtils;
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

	@Resource
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> list(Employee employee, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Employee> pageEmployee = employeeRepository.findAll( new Specification<Employee>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(employee != null) {
					if(employee != null) {
						if(StringUtils.isNotEmpty(employee.getName())) {
							predicate.getExpressions().add(cb.like(root.get("name"), "%" + employee.getName() + "%"));
						}
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageEmployee.getContent();
	}
	@Override
	public Long getCount(Employee employee) {
		Long count = employeeRepository.count(new Specification<Employee>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(employee != null) {
					if(StringUtils.isNotEmpty(employee.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + employee.getName() + "%"));
					}
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(Employee employee) {
		employeeRepository.save(employee);
	}
	@Override
	public void delete(Integer id) {
		employeeRepository.deleteById(id);
	}
	@Override
	public Employee findById(Integer id) {
		// TODO Auto-generated method stub
		return employeeRepository.findById(id).get();
	}
	@Override
	public Employee findByName(String employeeName) {
		return employeeRepository.findByName(employeeName);
	}
	@Override
	public List<Employee> listAll() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}
	@Override
	public List<Employee> comboList(String q) {
		// TODO Auto-generated method stub
		return employeeRepository.comboList(q);
	}
	@Override
	public Employee findByNameAndNum(String employeeName, String employeeNum) {
		// TODO Auto-generated method stub
		return employeeRepository.findByNameAndNum(employeeName, employeeNum);
	}

}
