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

import com.hengyue.entity.vo.Assess;
import com.hengyue.respository.vo.AssessRepository;
import com.hengyue.service.vo.AssessService;
import com.hengyue.utils.StringUtils;
@Service("assessService")
public class AssessServiceImpl implements AssessService{

	@Resource
	private AssessRepository assessRepository;

	@Override
	public List<Assess> list(Assess assess, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Assess> pageAssess = assessRepository.findAll( new Specification<Assess>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Assess> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(assess != null) {
					if(assess != null) {
						if(StringUtils.isNotEmpty(assess.getName())) {
							predicate.getExpressions().add(cb.like(root.get("name"), "%" + assess.getName() + "%"));
						}
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageAssess.getContent();
	}
	@Override
	public Long getCount(Assess assess) {
		Long count = assessRepository.count(new Specification<Assess>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Assess> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(assess != null) {
					if(StringUtils.isNotEmpty(assess.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + assess.getName() + "%"));
					}
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(Assess assess) {
		assessRepository.save(assess);
	}
	@Override
	public void delete(Integer id) {
		assessRepository.deleteById(id);
	}
	@Override
	public Assess findById(Integer id) {
		// TODO Auto-generated method stub
		return assessRepository.findById(id).get();
	}

}
