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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengyue.entity.vo.CanteenMonth;
import com.hengyue.respository.vo.CanteenMonthRepository;
import com.hengyue.service.vo.CanteenMonthService;
@Service("canteenMonthService")
public class CanteenMonthServiceImpl implements CanteenMonthService{

	@Resource
	private CanteenMonthRepository canteenMonthRepository;

	@Override
	public List<CanteenMonth> list(CanteenMonth canteenMonth, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<CanteenMonth> pageCanteenMonth = canteenMonthRepository.findAll( new Specification<CanteenMonth>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CanteenMonth> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(canteenMonth != null) {
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageCanteenMonth.getContent();
	}
	@Override
	public Long getCount(CanteenMonth canteenMonth) {
		Long count = canteenMonthRepository.count(new Specification<CanteenMonth>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CanteenMonth> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(canteenMonth != null) {
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(CanteenMonth canteenMonth) {
		canteenMonthRepository.save(canteenMonth);
	}
	@Override
	public void delete(Integer id) {
		canteenMonthRepository.deleteById(id);
	}
	@Override
	public CanteenMonth findById(Integer id) {
		return canteenMonthRepository.findById(id).get();
	}
	@Override
	public CanteenMonth findByTimes(Date times) {
		// TODO Auto-generated method stub
		return canteenMonthRepository.findByTimes(times);
	}

}
