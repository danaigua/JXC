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

import com.hengyue.entity.vo.Position;
import com.hengyue.respository.vo.PositionRepository;
import com.hengyue.service.vo.PositionService;
import com.hengyue.utils.StringUtils;
@Service("positionService")
public class PositionServiceImpl implements PositionService{

	@Resource
	private PositionRepository positionRepository;

	@Override
	public List<Position> list(Position position, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<Position> pagePosition = positionRepository.findAll( new Specification<Position>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Position> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(position != null) {
					if(position != null) {
						if(StringUtils.isNotEmpty(position.getName())) {
							predicate.getExpressions().add(cb.like(root.get("name"), "%" + position.getName() + "%"));
						}
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pagePosition.getContent();
	}
	@Override
	public Long getCount(Position position) {
		Long count = positionRepository.count(new Specification<Position>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Position> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(position != null) {
					if(StringUtils.isNotEmpty(position.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + position.getName() + "%"));
					}
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(Position position) {
		positionRepository.save(position);
	}
	@Override
	public void delete(Integer id) {
		positionRepository.deleteById(id);
	}
	@Override
	public Position findById(Integer id) {
		return positionRepository.findById(id).get();
	}
	@Override
	public List<Position> findAll() {
		return positionRepository.findAll();
	}
	@Override
	public Position findByName(String position) {
		// TODO Auto-generated method stub
		return positionRepository.findByName(position);
	}

}
