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

import com.hengyue.entity.Role;
import com.hengyue.respository.RoleRepository;
import com.hengyue.service.RoleService;
import com.hengyue.utils.StringUtils;
@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleRepository roleRepository;
	@Override
	public List<Role> findByUserId(Integer id) {
		// TODO Auto-generated method stub
		return roleRepository.findByUserId(id);
	}
	@Override
	public Role findById(Integer id) {
		// TODO Auto-generated method stub
		return roleRepository.findById(id).get();
	}
	@Override
	public List<Role> listAll() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}
	@Override
	public List<Role> list(Role role, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Role> rolePage = roleRepository.findAll(new Specification<Role>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(role != null) {
					if(StringUtils.isNotEmpty(role.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + role.getName() + "%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
		}, pageable);
		return rolePage.getContent();
	}
	@Override
	public Long getCount(Role role) {
		Long count = roleRepository.count(new Specification<Role>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(role != null) {
					if(StringUtils.isNotEmpty(role.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + role.getName() + "%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
		});
		return count;
	}
	@Override
	public void delete(Integer id) {
		roleRepository.deleteById(id);
	}
	@Override
	public void save(Role role) {
		// TODO Auto-generated method stub
		roleRepository.save(role);
	}
	@Override
	public Role findByRoleName(String name) {
		// TODO Auto-generated method stub
		return roleRepository.findByRoleName(name);
	}

}
