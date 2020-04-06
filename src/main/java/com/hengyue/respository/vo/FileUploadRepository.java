package com.hengyue.respository.vo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengyue.entity.vo.FileUpload;

/**
 * 文件上传持久层接口
 * @author 章家宝
 *
 */
public interface FileUploadRepository extends JpaRepository<FileUpload, Integer>, JpaSpecificationExecutor<FileUpload> {
}
