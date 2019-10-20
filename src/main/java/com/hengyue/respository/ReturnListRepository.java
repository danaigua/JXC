package com.hengyue.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.ReturnList;

/**
 *退货单持久层接口
 * @author 章家宝
 *
 */
public interface ReturnListRepository extends JpaRepository<ReturnList, Integer>, JpaSpecificationExecutor<ReturnList> {

	/**
	 * 获取当天进货单号
	 * @return
	 */
	@Query(value = "SELECT MAX(return_number) FROM t_return_list WHERE TO_DAYS (return_date)", nativeQuery = true)
	public String getTodayMaxRuturnNumber();
}
