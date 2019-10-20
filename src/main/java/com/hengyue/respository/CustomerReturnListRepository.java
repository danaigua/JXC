package com.hengyue.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.CustomerReturnList;

/**
 * 客户退货单持久层接口
 * @author 章家宝
 *
 */
public interface CustomerReturnListRepository extends JpaRepository<CustomerReturnList, Integer>, JpaSpecificationExecutor<CustomerReturnList> {

	/**
	 * 获取当天进货单号
	 * @return
	 */
	@Query(value = "SELECT MAX(customer_return_number) FROM t_customer_return_list WHERE TO_DAYS (customer_return_date)", nativeQuery = true)
	public String getTodayMaxCustomerReturnNumber();
}
