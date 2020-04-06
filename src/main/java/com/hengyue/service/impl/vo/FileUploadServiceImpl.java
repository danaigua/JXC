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

import com.hengyue.entity.vo.FileUpload;
import com.hengyue.respository.vo.FileUploadRepository;
import com.hengyue.service.vo.FileUploadService;
import com.hengyue.utils.StringUtils;
@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService{

	@Resource
	private FileUploadRepository fileUploadRepository;

	@Override
	public List<FileUpload> list(FileUpload fileUpload, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<FileUpload> pageFileUpload = fileUploadRepository.findAll( new Specification<FileUpload>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<FileUpload> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(fileUpload != null) {
					if(fileUpload != null) {
						if(StringUtils.isNotEmpty(fileUpload.getName())) {
							predicate.getExpressions().add(cb.like(root.get("name"), "%" + fileUpload.getName() + "%"));
						}
					}
				}
				return predicate;
			}
		},pageable);//加上分页就可以有分页了
		return pageFileUpload.getContent();
	}
	@Override
	public Long getCount(FileUpload fileUpload) {
		Long count = fileUploadRepository.count(new Specification<FileUpload>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<FileUpload> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
	public void save(FileUpload fileUpload) {
		fileUploadRepository.save(fileUpload);
	}
	@Override
	public void delete(Integer id) {
		fileUploadRepository.deleteById(id);
	}
	@Override
	public FileUpload findById(Integer id) {
		// TODO Auto-generated method stub
		return fileUploadRepository.findById(id).get();
	}

}
