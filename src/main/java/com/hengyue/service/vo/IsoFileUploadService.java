package com.hengyue.service.vo;


import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.vo.IsoFileUpload;
/**
 * 文件上传service接口
 * @author 章家宝
 *
 */
public interface IsoFileUploadService {

	
	/**
	 * 根据条件分页查询文件上传信息
	 * @param fileUpload
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<IsoFileUpload> list(IsoFileUpload fileUpload, Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 获取总记录数
	 * @param fileUpload
	 * @return
	 */
	public Long getCount(IsoFileUpload fileUpload);
	
	/**
	 * 添加或者修改文件上传信息
	 * @param fileUpload
	 */
	public void save(IsoFileUpload fileUpload);
	/**
	 * 根据id删除文件上传
	 * @param id
	 */
	public void delete(Integer id);
	/**
	 * 通过id查找文件上传
	 * @param id
	 * @return
	 */
	public IsoFileUpload findById(Integer id);

	/**
	 * 通过TypeId查找文件上传
	 * @param id
	 * @return
	 */
	public List<IsoFileUpload> findByTypeId(Integer id);
}
