package com.hengyue.service.vo;
/**
 * 文件夹类别业务层接口
 * @author 章家宝
 *
 */

import java.util.List;

import com.hengyue.entity.vo.FolderType;

public interface FolderTypeService {
	/**
	 * 通过id删除商品类别
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 添加一个商品类别
	 * @param folderType
	 */
	public void save(FolderType folderType);
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	public List<FolderType> findByParentId(Integer parentId);
	/**
	 * 通过id查找实体
	 * @param id
	 * @return
	 */
	public FolderType findById(Integer id);
	/**
	 * 通过名称查找实体
	 * @param string
	 * @return
	 */
	public FolderType findByName(String string, String url);
	/**
	 * 查找根id为userId并且pid为1的节点
	 * @param userId
	 * @return
	 */
	public FolderType findGenId(Integer userId);
}
