package com.hengyue.service.impl.vo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
/**
 * 测试标准类别接口实现类
 * @author 章家宝
 *
 */

import com.hengyue.entity.vo.PlanCourseStandardType;
import com.hengyue.respository.vo.PlanCourseStandardTypeRepository;
import com.hengyue.service.vo.PlanCourseStandardTypeService;
@Service("planCourseStandardTypeService")
public class PlanCourseStandardTypeServiceImpl implements PlanCourseStandardTypeService {

	@Resource
	private PlanCourseStandardTypeRepository planCourseStandardTypeRepository;

	@Override
	public void delete(Integer id) {
		planCourseStandardTypeRepository.deleteById(id);
	}

	@Override
	public void save(PlanCourseStandardType planCourseStandardType) {
		planCourseStandardTypeRepository.save(planCourseStandardType);
	}

	@Override
	public List<PlanCourseStandardType> findByParentId(Integer parentId) {
		return planCourseStandardTypeRepository.findByParentId(parentId);
	}

	@Override
	public PlanCourseStandardType findById(Integer id) {
		return planCourseStandardTypeRepository.getOne(id);
	}

	@Override
	public PlanCourseStandardType findByName(String string, String url) {
		if(planCourseStandardTypeRepository.findByName(string, url).size() >= 1) {
			return planCourseStandardTypeRepository.findByName(string, url).get(0);
		}else {
			return null;
		}
		
	}
	
	
}
