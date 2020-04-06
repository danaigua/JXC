package com.hengyue.service.impl.vo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
/**
 * 商品类别接口实现类
 * @author 章家宝
 *
 */

import com.hengyue.entity.vo.FolderType;
import com.hengyue.respository.vo.FolderTypeRepository;
import com.hengyue.service.vo.FolderTypeService;
@Service("folderTypeService")
public class FolderTypeServiceImpl implements FolderTypeService {

	@Resource
	private FolderTypeRepository folderTypeRepository;

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		folderTypeRepository.deleteById(id);
	}

	@Override
	public void save(FolderType folderType) {
		// TODO Auto-generated method stub
		folderTypeRepository.save(folderType);
	}

	@Override
	public List<FolderType> findByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		return folderTypeRepository.findByParentId(parentId);
	}

	@Override
	public FolderType findById(Integer id) {
		// TODO Auto-generated method stub
		return folderTypeRepository.getOne(id);
	}

	@Override
	public FolderType findByName(String string, String url) {
		// TODO Auto-generated method stub
		if(folderTypeRepository.findByName(string, url).size() >= 1) {
			return folderTypeRepository.findByName(string, url).get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public FolderType findGenId(Integer userId) {
		// TODO Auto-generated method stub
		return folderTypeRepository.findGenId(userId);
	}
	
	
}
