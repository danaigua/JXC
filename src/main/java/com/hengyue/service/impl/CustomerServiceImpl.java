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

import com.hengyue.entity.Customer;
import com.hengyue.respository.CustomerRepository;
import com.hengyue.service.CustomerService;
import com.hengyue.utils.StringUtils;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerRepository customerRepository;
	
	
	@Override
	public List<Customer> list(Customer customer, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Customer> customerPage = customerRepository.findAll(new Specification<Customer>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(customer != null) {
					if(StringUtils.isNotEmpty(customer.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + customer.getName() + "%"));
					}
				}
				return predicate;
			}
		}, pageable);
		return customerPage.getContent();
	}

	@Override
	public Long getCount(Customer Customer) {
		Long count = customerRepository.count(new Specification<Customer>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(Customer != null) {
					if(StringUtils.isNotEmpty(Customer.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + Customer.getName() + "%"));
					}
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	public void save(Customer Customer) {
		// TODO Auto-generated method stub
		customerRepository.save(Customer);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		customerRepository.deleteById(id);
	}

	@Override
	public Customer findById(Integer id) {
		// TODO Auto-generated method stub
		return customerRepository.getOne(id);
	}

	@Override
	public Customer findByCustomerName(String customerName) {
		// TODO Auto-generated method stub
		return customerRepository.findByCustomerName(customerName);
	}

	@Override
	public List<Customer> findByName(String name) {
		// TODO Auto-generated method stub
		return customerRepository.findByName(name);
	}

	@Override
	public Customer findCode(String string) {
		// TODO Auto-generated method stub
		return customerRepository.findCode(string);
	}

}
