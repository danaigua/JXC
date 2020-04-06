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

import com.hengyue.entity.User;
import com.hengyue.respository.UserRepository;
import com.hengyue.service.UserService;
import com.hengyue.utils.StringUtils;
@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	private UserRepository userRepository;

	@Override
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	@Override
	public List<User> list(User user, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<User> pageUser = userRepository.findAll( new Specification<User>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(user != null) {
					if(StringUtils.isNotEmpty(user.getUserName())) {
						predicate.getExpressions().add(cb.like(root.get("userName"), "%" + user.getUserName() + "%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageUser.getContent();
	}
	@Override
	public Long getCount(User user) {
		Long count = userRepository.count(new Specification<User>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(user != null) {
					if(StringUtils.isNotEmpty(user.getUserName())) {
						predicate.getExpressions().add(cb.like(root.get("userName"), "%" + user.getUserName() + "%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(User user) {
		userRepository.save(user);
	}
	@Override
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}
	@Override
	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
	@Override
	public User findByUserTrueName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserTrueName(userName);
	}

}
