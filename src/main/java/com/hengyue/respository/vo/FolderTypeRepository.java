package com.hengyue.respository.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.FolderType;

/**
 * 文件夹类别持久层接口
 * @author 章家宝
 *
 */
public interface FolderTypeRepository extends JpaRepository<FolderType, Integer> {
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	@Query(value = "select * from t_folder_type where p_id = ?1", nativeQuery = true)
	public List<FolderType> findByParentId(int parentId);

	/**
	 * 通过名称查找实体
	 * @param string
	 * @return
	 */
	@Query(value = "select * from t_folder_type where name = ?1 and url = ?2", nativeQuery = true)
	public List<FolderType> findByName(String string, String url);

	/**
	 * 查找根id为userId并且pid为1的节点
	 * @param userId
	 * @return
	 */
	@Query(value = "select * from t_folder_type where user_id = ?1 and p_id = 1", nativeQuery = true)
	public FolderType findGenId(Integer userId);
}
