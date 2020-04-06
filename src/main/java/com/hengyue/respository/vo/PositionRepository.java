package com.hengyue.respository.vo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.Position;

/**
 *  职位持久层接口
 * @author 章家宝
 *
 */
public interface PositionRepository extends JpaRepository<Position, Integer>, JpaSpecificationExecutor<Position> {
	/**
	 * 通过职位名称查找职位实体
	 * @param position
	 * @return
	 */
	@Query(value = "select * from t_position where name = ?1", nativeQuery = true)
	Position findByName(String position);
}
