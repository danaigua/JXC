package com.hengyue.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.OverflowList;

/**
 * 商品报溢单持久层接口
 * @author 章家宝
 *
 */
public interface OverflowListRepository extends JpaRepository<OverflowList, Integer>, JpaSpecificationExecutor<OverflowList> {

	/**
	 * 获取当天商品报溢单号
	 * @return
	 */
	@Query(value = "SELECT MAX(overflow_number) FROM t_overflow_list WHERE TO_DAYS (overflow_date)", nativeQuery = true)
	public String getTodayMaxOverflowNumber();
}
