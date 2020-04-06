package com.hengyue.respository.vo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengyue.entity.vo.Assess;

/**
 * 绩效考核持久层接口
 * @author 章家宝
 *
 */
public interface AssessRepository extends JpaRepository<Assess, Integer>, JpaSpecificationExecutor<Assess> {
}
