package com.hengyue.respository.vo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengyue.entity.vo.DepartmentPosition;

/**
 * 部门职位持久层接口
 * @author 章家宝
 *
 */
public interface DepartmentPositionRepository extends JpaRepository<DepartmentPosition, Integer>, JpaSpecificationExecutor<DepartmentPosition> {
}
