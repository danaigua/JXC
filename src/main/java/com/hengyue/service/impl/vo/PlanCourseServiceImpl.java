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

import com.hengyue.entity.vo.PlanCourse;
import com.hengyue.respository.vo.PlanCourseRepository;
import com.hengyue.service.vo.PlanCourseService;
import com.hengyue.utils.StringUtils;

@Service
public class PlanCourseServiceImpl implements PlanCourseService {

	@Resource
	private PlanCourseRepository planCourseRepository;

	@Override
	public PlanCourse findById(Integer planCourseId) {
		return planCourseRepository.getOne(planCourseId);
	}

	@Override
	public PlanCourse findByName(String planCourseName) {
		return planCourseRepository.findByName(planCourseName);
	}

	@Override
	public List<PlanCourse> list(PlanCourse planCourse, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<PlanCourse> planCoursePage = planCourseRepository.findAll(new Specification<PlanCourse>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PlanCourse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (planCourse != null) {
					if (StringUtils.isNotEmpty(planCourse.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + planCourse.getName() + "%"));
					}
					if (StringUtils.isNotEmpty(planCourse.getContent())) {
						predicate.getExpressions().add(cb.like(root.get("content"), "%" + planCourse.getContent() + "%"));
					}
					if (StringUtils.isNotEmpty(planCourse.getRemarks())) {
						predicate.getExpressions().add(cb.like(root.get("remarks"), "%" + planCourse.getRemarks() + "%"));
					}
					predicate.getExpressions().add(cb.equal(root.get("finish"), planCourse.getFinish()));
				}
				return predicate;
			}
		}, pageable);
		return planCoursePage.getContent();
	}

	@Override
	public Long getCount(PlanCourse planCourse) {
		return planCourseRepository.count(new Specification<PlanCourse>() {

			@Override
			public Predicate toPredicate(Root<PlanCourse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (planCourse != null) {
					if (StringUtils.isNotEmpty(planCourse.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + planCourse.getName() + "%"));
					}
					if (StringUtils.isNotEmpty(planCourse.getContent())) {
						predicate.getExpressions().add(cb.like(root.get("content"), "%" + planCourse.getContent() + "%"));
					}
					if (StringUtils.isNotEmpty(planCourse.getRemarks())) {
						predicate.getExpressions().add(cb.like(root.get("remarks"), "%" + planCourse.getRemarks() + "%"));
					}
					predicate.getExpressions().add(cb.equal(root.get("finish"), planCourse.getFinish()));
				}
				return predicate;
			}
		});
	}

	@Override
	public void delete(Integer planCourseId) {
		planCourseRepository.deleteById(planCourseId);
	}

	@Override
	public void save(PlanCourse planCourse) {
		planCourseRepository.save(planCourse);
	}

	@Override
	public void updateAsFinished(Integer planCourseId) {
		planCourseRepository.updateAsFinished(planCourseId);
	}

	@Override
	public PlanCourse findByUUID(String string) {
		// TODO Auto-generated method stub
		return planCourseRepository.findByUUID(string);
	}

	@Override
	public List<PlanCourse> findByUserId(Integer id) {
		// TODO Auto-generated method stub
		return planCourseRepository.findByUserId(id);
	}

}
