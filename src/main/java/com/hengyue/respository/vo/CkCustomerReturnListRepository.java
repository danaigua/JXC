package com.hengyue.respository.vo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.CkCustomerReturnList;


/**
 * 客户退货单持久层接口
 * @author 章家宝
 *
 */
public interface CkCustomerReturnListRepository extends JpaRepository<CkCustomerReturnList, Integer>, JpaSpecificationExecutor<CkCustomerReturnList> {

	/**
	 * 获取当天进货单号
	 * @return
	 */
	@Query(value = "SELECT MAX(customer_return_number) FROM t_ck_customer_return_list WHERE TO_DAYS (customer_return_date)", nativeQuery = true)
	public String getTodayMaxCkCustomerReturnNumber();
}
