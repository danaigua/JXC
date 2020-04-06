package com.hengyue.respository.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.Canteen;

public interface CanteenRepository extends JpaRepository<Canteen, Integer>, JpaSpecificationExecutor<Canteen> {

	/**
	 * 删除一个时间内的所有员工食堂就餐信息
	 * @param times
	 */
	@Query(value = "select * from t_canteen where times = ?1", nativeQuery = true)
	public List<Canteen> selectByTimes(String times);

}
