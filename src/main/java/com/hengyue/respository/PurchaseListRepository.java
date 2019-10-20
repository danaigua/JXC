package com.hengyue.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.PurchaseList;

/**
 * 进货单持久层接口
 * @author 章家宝
 *
 */
public interface PurchaseListRepository extends JpaRepository<PurchaseList, Integer>, JpaSpecificationExecutor<PurchaseList> {

	/**
	 * 获取当天进货单号
	 * @return
	 */
	@Query(value = "SELECT MAX(purchase_number) FROM t_purchase_list WHERE TO_DAYS (purchase_date)", nativeQuery = true)
	public String getTodayMaxPurchaseNumber();
}
