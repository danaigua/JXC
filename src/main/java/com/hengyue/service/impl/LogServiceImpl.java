package com.hengyue.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengyue.entity.Log;
import com.hengyue.respository.LogRepository;
import com.hengyue.respository.UserRepository;
import com.hengyue.service.LogService;
import com.hengyue.utils.StringUtils;

@Service("logService")
public class LogServiceImpl implements LogService{

	@Resource
	private LogRepository logRepository;
	
	@Resource
	private UserRepository userRepository;

	@Override
	public void save(Log log) {
		// TODO Auto-generated method stub
		log.setTime(new Date());
		log.setUser(userRepository.findByUserName((String)SecurityUtils.getSubject().getPrincipal()));
		logRepository.save(log);
	}

	@Override
	public List<Log> list(Log log, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize, direction,properties);
		Page<Log> pageLog = logRepository.findAll(new Specification<Log>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(log != null) {
					if(StringUtils.isNotEmpty(log.getType())) {
						predicate.getExpressions().add(cb.equal(root.get("type"),log.getType()));
					}
					if(log.getUser() != null && StringUtils.isNotEmpty(log.getUser().getTrueName())) {
						predicate.getExpressions().add(cb.like(root.get("user").get("trueName"), "%" + log.getUser().getTrueName() + "%"));
					}
					if(log.getBtime() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("time"), log.getBtime()));
					}
					if(log.getEtime() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("time"), log.getEtime()));
					}
				}
				return predicate;
			}
		}, pageable);
		return pageLog.getContent();
	}

	@Override
	public Long getCount(Log log) {
		Long count = logRepository.count(new Specification<Log>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(log != null) {
					if(StringUtils.isNotEmpty(log.getType())) {
						predicate.getExpressions().add(cb.equal(root.get("type"),log.getType()));
					}
					if(log.getUser() != null && StringUtils.isNotEmpty(log.getUser().getTrueName())) {
						predicate.getExpressions().add(cb.like(root.get("user").get("trueName"), "%" + log.getUser().getTrueName() + "%"));
					}
					if(log.getBtime() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("time"), log.getBtime()));
					}
					if(log.getEtime() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("time"), log.getEtime()));
					}
				}
				return predicate;
			}
		});
		return count;
	}


}
