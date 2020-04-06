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

import com.hengyue.entity.vo.Plan;
import com.hengyue.respository.vo.PlanRepository;
import com.hengyue.service.vo.PlanService;
import com.hengyue.utils.StringUtils;

@Service
public class PlanServiceImpl implements PlanService {

	@Resource
	private PlanRepository planRepository;

	@Override
	public Plan findById(Integer planId) {
		return planRepository.getOne(planId);
	}

	@Override
	public Plan findByName(String planName) {
		return planRepository.findByName(planName);
	}

	@Override
	public List<Plan> list(Plan plan, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Plan> planPage = planRepository.findAll(new Specification<Plan>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Plan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (plan != null) {
					if (StringUtils.isNotEmpty(plan.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + plan.getName() + "%"));
					}
					if (StringUtils.isNotEmpty(plan.getContent())) {
						predicate.getExpressions().add(cb.like(root.get("content"), "%" + plan.getContent() + "%"));
					}
					if (StringUtils.isNotEmpty(plan.getRemarks())) {
						predicate.getExpressions().add(cb.like(root.get("remarks"), "%" + plan.getRemarks() + "%"));
					}
					predicate.getExpressions().add(cb.equal(root.get("finish"), plan.getFinish()));
				}
				return predicate;
			}
		}, pageable);
		return planPage.getContent();
	}

	@Override
	public Long getCount(Plan plan) {
		return planRepository.count(new Specification<Plan>() {

			@Override
			public Predicate toPredicate(Root<Plan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (plan != null) {
					if (StringUtils.isNotEmpty(plan.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + plan.getName() + "%"));
					}
					if (StringUtils.isNotEmpty(plan.getContent())) {
						predicate.getExpressions().add(cb.like(root.get("content"), "%" + plan.getContent() + "%"));
					}
					if (StringUtils.isNotEmpty(plan.getRemarks())) {
						predicate.getExpressions().add(cb.like(root.get("remarks"), "%" + plan.getRemarks() + "%"));
					}
					predicate.getExpressions().add(cb.equal(root.get("finish"), plan.getFinish()));
				}
				return predicate;
			}
		});
	}

	@Override
	public void delete(Integer planId) {
		planRepository.deleteById(planId);
	}

	@Override
	public void save(Plan plan) {
		planRepository.save(plan);
	}

	@Override
	public void updateAsFinished(Integer planId) {
		planRepository.updateAsFinished(planId);
	}

	@Override
	public Plan findByPlanCourseId(Integer id) {
		// TODO Auto-generated method stub
		return planRepository.findByPlanCourseId(id);
	}

}
