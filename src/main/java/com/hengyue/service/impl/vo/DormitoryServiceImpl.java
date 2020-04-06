package com.hengyue.service.impl.vo;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
/**
 * @author 章家宝
 *
 */

import com.hengyue.entity.vo.Dormitory;
import com.hengyue.respository.vo.DormitoryRepository;
import com.hengyue.service.vo.DormitoryService;
import com.hengyue.utils.StringUtils;
@Service("dormitoryService")
public class DormitoryServiceImpl implements DormitoryService {

	@Resource
	private DormitoryRepository dormitoryRepository;

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		dormitoryRepository.deleteById(id);
	}

	@Override
	public void save(Dormitory dormitory) {
		// TODO Auto-generated method stub
		dormitoryRepository.save(dormitory);
	}


	@Override
	public Dormitory findById(Integer id) {
		// TODO Auto-generated method stub
		return dormitoryRepository.getOne(id);
	}

	@Override
	public Dormitory findByName(String string) {
		return dormitoryRepository.findByName(string);
	}

	@Override
	public List<Dormitory> list(Dormitory dormitory) {
		return dormitoryRepository.findAll(new Specification<Dormitory>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Dormitory> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(dormitory != null) {
					if(StringUtils.isNotEmpty(dormitory.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + dormitory.getName() + "%"));
					}
				}
				return predicate;
			}
		});
	}

	
}
