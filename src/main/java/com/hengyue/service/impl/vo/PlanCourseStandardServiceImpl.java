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

import com.hengyue.entity.vo.PlanCourseStandard;
import com.hengyue.respository.vo.PlanCourseStandardRepository;
import com.hengyue.service.vo.PlanCourseStandardService;
import com.hengyue.utils.StringUtils;

@Service
public class PlanCourseStandardServiceImpl implements PlanCourseStandardService {

	@Resource
	private PlanCourseStandardRepository planCourseStandardRepository;

	@Override
	public PlanCourseStandard findById(Integer planCourseStandardStandard) {
		return planCourseStandardRepository.getOne(planCourseStandardStandard);
	}

	@Override
	public PlanCourseStandard findByName(String planCourseStandardName) {
		return planCourseStandardRepository.findByName(planCourseStandardName);
	}

	@Override
	public List<PlanCourseStandard> list(PlanCourseStandard planCourseStandard, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<PlanCourseStandard> planCourseStandardPage = planCourseStandardRepository.findAll(new Specification<PlanCourseStandard>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PlanCourseStandard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (planCourseStandard != null) {
					if (StringUtils.isNotEmpty(planCourseStandard.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + planCourseStandard.getName() + "%"));
					}
					if (planCourseStandard.getPlanCourseStandardType() != null && planCourseStandard.getPlanCourseStandardType().getId() != 1) {
						predicate.getExpressions().add(cb.equal(root.get("planCourseStandardType"), planCourseStandard.getPlanCourseStandardType().getId()));
					}
				}
				return predicate;
			}
		}, pageable);
		return planCourseStandardPage.getContent();
	}

	@Override
	public Long getCount(PlanCourseStandard planCourseStandard) {
		return planCourseStandardRepository.count(new Specification<PlanCourseStandard>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<PlanCourseStandard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (planCourseStandard != null) {
					if (StringUtils.isNotEmpty(planCourseStandard.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + planCourseStandard.getName() + "%"));
					}
					if (planCourseStandard.getPlanCourseStandardType() != null && planCourseStandard.getPlanCourseStandardType().getId() != 1) {
						predicate.getExpressions().add(cb.equal(root.get("planCourseStandardType"), planCourseStandard.getPlanCourseStandardType().getId()));
					}
				}
				return predicate;
			}
		});
	}

	@Override
	public void delete(Integer planCourseStandard) {
		planCourseStandardRepository.deleteById(planCourseStandard);
	}

	@Override
	public void save(PlanCourseStandard planCourseStandard) {
		planCourseStandardRepository.save(planCourseStandard);
	}

	@Override
	public List<PlanCourseStandard> findAll() {
		// TODO Auto-generated method stub
		return planCourseStandardRepository.findAll();
	}


}
