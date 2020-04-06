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

import com.hengyue.entity.vo.IsoFileUpload;
import com.hengyue.respository.vo.IsoFileUploadRepository;
import com.hengyue.service.vo.IsoFileUploadService;
import com.hengyue.utils.StringUtils;
@Service("isoFileUploadService")
public class IsoFileUploadServiceImpl implements IsoFileUploadService{

	@Resource
	private IsoFileUploadRepository isoFileUploadRepository;

	@Override
	public List<IsoFileUpload> list(IsoFileUpload fileUpload, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<IsoFileUpload> pageIsoFileUpload = isoFileUploadRepository.findAll( new Specification<IsoFileUpload>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<IsoFileUpload> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(fileUpload != null) {
					if(fileUpload != null) {
						if(StringUtils.isNotEmpty(fileUpload.getName())) {
							predicate.getExpressions().add(cb.like(root.get("name"), "%" + fileUpload.getName() + "%"));
						}
						if(StringUtils.isNotEmpty(fileUpload.getContent())) {
							predicate.getExpressions().add(cb.like(root.get("content"), "%" + fileUpload.getContent() + "%"));
						}
						if (fileUpload.getFolderType() != null && fileUpload.getFolderType().getId() != null && fileUpload.getFolderType().getId() != 1) {
							predicate.getExpressions().add(cb.equal(root.get("folderType").get("id"), fileUpload.getFolderType().getId()));
						}
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageIsoFileUpload.getContent();
	}
	@Override
	public Long getCount(IsoFileUpload fileUpload) {
		Long count = isoFileUploadRepository.count(new Specification<IsoFileUpload>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<IsoFileUpload> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(fileUpload != null) {
					if(StringUtils.isNotEmpty(fileUpload.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%" + fileUpload.getName() + "%"));
					}
				}
				return predicate;
			}
			
		});
		return count;
	}
	@Override
	public void save(IsoFileUpload fileUpload) {
		isoFileUploadRepository.save(fileUpload);
	}
	@Override
	public void delete(Integer id) {
		isoFileUploadRepository.deleteById(id);
	}
	@Override
	public IsoFileUpload findById(Integer id) {
		return isoFileUploadRepository.findById(id).get();
	}
	@Override
	public List<IsoFileUpload> findByTypeId(Integer id) {
		return isoFileUploadRepository.findByTypeId(id);
	}

}
