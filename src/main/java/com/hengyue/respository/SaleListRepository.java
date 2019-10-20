package com.hengyue.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.SaleList;

/**
 * 销售单持久层接口
 * @author 章家宝
 *
 */
public interface SaleListRepository extends JpaRepository<SaleList, Integer>, JpaSpecificationExecutor<SaleList> {

	/**
	 * 获取当天进货单号
	 * @return
	 */
	@Query(value = "SELECT MAX(sale_number) FROM t_sale_list WHERE TO_DAYS (sale_date)", nativeQuery = true)
	public String getTodayMaxSaleNumber();
}
