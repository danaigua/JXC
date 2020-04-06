package com.hengyue.respository.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.IsoFileUpload;

/**
 * 文件上传持久层接口
 * @author 章家宝
 *
 */
public interface IsoFileUploadRepository extends JpaRepository<IsoFileUpload, Integer>, JpaSpecificationExecutor<IsoFileUpload> {

	@Query(value = "select * from t_iso_file_upload where folder_type_id = ?1", nativeQuery = true)
	public List<IsoFileUpload> findByTypeId(Integer id);
}
