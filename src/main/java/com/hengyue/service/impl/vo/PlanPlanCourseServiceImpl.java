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

import com.hengyue.entity.vo.PlanPlanCourse;
import com.hengyue.respository.vo.PlanPlanCourseRepository;
import com.hengyue.service.vo.PlanPlanCourseService;
@Service("planPlanCourseService")
public class PlanPlanCourseServiceImpl implements PlanPlanCourseService{

	@Resource
	private PlanPlanCourseRepository planPlanCourseRepository;

	@Override
	public List<PlanPlanCourse> list(PlanPlanCourse planPlanCourse, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<PlanPlanCourse> pagePlanPlanCourse = planPlanCourseRepository.findAll( new Specification<PlanPlanCourse>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PlanPlanCourse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(planPlanCourse != null) {
					if(planPlanCourse != null) {
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pagePlanPlanCourse.getContent();
	}
	@Override
	public Long getCount(PlanPlanCourse planPlanCourse) {
		Long count = planPlanCourseRepository.count(new Specification<PlanPlanCourse>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PlanPlanCourse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(planPlanCourse != null) {
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(PlanPlanCourse planPlanCourse) {
		planPlanCourseRepository.save(planPlanCourse);
	}
	@Override
	public void delete(Integer id) {
		planPlanCourseRepository.deleteById(id);
	}
	@Override
	public PlanPlanCourse findById(Integer id) {
		return planPlanCourseRepository.findById(id).get();
	}
	@Override
	public List<PlanPlanCourse> findByPlanId(Integer planId) {
		// TODO Auto-generated method stub
		return planPlanCourseRepository.findByPlanId(planId);
	}
	@Override
	public PlanPlanCourse findByPlanCourseId(Integer id) {
		// TODO Auto-generated method stub
		return planPlanCourseRepository.findByPlanCourseId(id);
	}

}
