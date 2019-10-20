package com.hengyue.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.Supplier;

/**
 * 供应商持久层接口
 * @author 章家宝
 *
 */
public interface SupplierRepository extends JpaRepository<Supplier, Integer>, JpaSpecificationExecutor<Supplier> {

	/**
	 * 通过供应商姓名查找供应商
	 * @param supplierName
	 * @return
	 */
	@Query(value = "select * from t_supplier where name = ?1", nativeQuery = true)
	public Supplier findBySupplierName(String supplierName);
	
	/**
	 * 通过供应商名称模糊查询
	 * @param name
	 * @return
	 */
	@Query(value = "select * from t_supplier where name like ?1", nativeQuery = true)
	public List<Supplier> findByName(String name);
}
