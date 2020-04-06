package com.hengyue.service.impl.vo;


import java.util.Date;
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
import com.hengyue.entity.vo.Time;
import com.hengyue.respository.vo.TimeRepository;
import com.hengyue.service.vo.TimeService;
import com.hengyue.utils.StringUtils;
@Service("timeService")
public class TimeServiceImpl implements TimeService{

	@Resource
	private TimeRepository timeRepository;

	@Override
	public List<Time> list(Time time, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Time> pageTime = timeRepository.findAll( new Specification<Time>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Time> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(time != null) {
					if(time != null) {
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageTime.getContent();
	}
	@Override
	public Long getCount(Time time) {
		Long count = timeRepository.count(new Specification<Time>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Time> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(time != null) {
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(Time time) {
		timeRepository.save(time);
	}
	@Override
	public void delete(Integer id) {
		timeRepository.deleteById(id);
	}
	@Override
	public Time findById(Integer id) {
		// TODO Auto-generated method stub
		return timeRepository.findById(id).get();
	}
	@Override
	public List<Time> listNoPage(Time time, Direction asc, String string) {
		return timeRepository.findAll(new Specification<Time>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Time> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(time != null) {
					if(StringUtils.isNotEmpty(time.getEmployeeName())) {
						predicate.getExpressions().add(cb.like(root.get("employeeName"), "%" + time.getEmployeeName() + "%"));
					}
					if(time.getbTime() != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("workDate"), time.getbTime()));
					}
					if(time.geteTime() != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("workDate"), time.geteTime()));
					}
				}
				return predicate;
			}
		}, Sort.by(asc, string));
	}
	@Override
	public Time findByEmployeeNameAndDate(String employeeName, Date date) {
		return timeRepository.findByEmployeeNameAndDate(employeeName, date);
	}
}
