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

import com.hengyue.entity.vo.Canteen;
import com.hengyue.respository.vo.CanteenRepository;
import com.hengyue.service.vo.CanteenService;
import com.hengyue.utils.StringUtils;
@Service("canteenService")
public class CanteenServiceImpl implements CanteenService{

	@Resource
	private CanteenRepository canteenRepository;

	@Override
	public List<Canteen> list(Canteen canteen, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Canteen> pageCanteen = canteenRepository.findAll( new Specification<Canteen>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Canteen> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(canteen != null) {
					if(canteen != null) {
						if(StringUtils.isNotEmpty(canteen.getEmployeeName())) {
							predicate.getExpressions().add(cb.like(root.get("employeeName"), "%" + canteen.getEmployeeName() + "%"));
						}
						if(canteen.getCanteenMonth().getId() != null) {
							predicate.getExpressions().add(cb.equal(root.get("canteenMonth"), canteen.getCanteenMonth().getId()));
						}
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageCanteen.getContent();
	}
	@Override
	public Long getCount(Canteen canteen) {
		Long count = canteenRepository.count(new Specification<Canteen>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Canteen> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(canteen != null) {
					if(canteen != null) {
						if(StringUtils.isNotEmpty(canteen.getEmployeeName())) {
							predicate.getExpressions().add(cb.like(root.get("employeeName"), "%" + canteen.getEmployeeName() + "%"));
						}
					}
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(Canteen canteen) {
		canteenRepository.save(canteen);
	}
	@Override
	public void delete(Integer id) {
		canteenRepository.deleteById(id);
	}
	@Override
	public Canteen findById(Integer id) {
		return canteenRepository.findById(id).get();
	}
	@Override
	public List<Canteen> selectByTimes(String times) {
		// TODO Auto-generated method stub
		return canteenRepository.selectByTimes(times);
	}

}
