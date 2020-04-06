package com.hengyue.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.Customer;

/**
 * 客户持久层接口
 * @author 章家宝
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

	/**
	 * 通过供应商姓名查找供应商
	 * @param CustomerName
	 * @return
	 */
	@Query(value = "select * from t_customer where name = ?1", nativeQuery = true)
	public Customer findByCustomerName(String CustomerName);
	/**
	 * 通过名称模糊查询查找客户
	 * @param name
	 * @return
	 */
	@Query(value = "select * from t_customer where name like ?1", nativeQuery = true)
	public List<Customer> findByName(String name);
	/**
	 * 通过代码查询客户
	 * @param string
	 * @return
	 */
	@Query(value = "select * from t_customer where code = ?1", nativeQuery = true)
	public Customer findCode(String string);
}
