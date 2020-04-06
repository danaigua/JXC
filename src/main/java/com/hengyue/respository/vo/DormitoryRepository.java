package com.hengyue.respository.vo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.Dormitory;

/**
 * 文件夹类别持久层接口
 * @author 章家宝
 *
 */
public interface DormitoryRepository extends JpaRepository<Dormitory, Integer> , JpaSpecificationExecutor<Dormitory> {

	/**
	 * 通过名称查找实体
	 * @param string
	 * @return
	 */
	@Query(value = "select * from t_dormitory where name = ?1", nativeQuery = true)
	public Dormitory findByName(String string);
}
