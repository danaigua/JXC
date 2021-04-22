package com.hengyue.respository.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.CkCustomerReturnListGoods;


/**
 * 销售商品持久层接口
 * @author 章家宝
 *
 */
public interface CkCustomerReturnListGoodsRepository extends JpaRepository<CkCustomerReturnListGoods, Integer>, JpaSpecificationExecutor<CkCustomerReturnListGoods> {

	/**
	 * 通过销售id查找销售商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select * from t_ck_customer_return_list_goods where customer_return_list_id = ?1", nativeQuery = true)
	public List<CkCustomerReturnListGoods> listByCkCustomerReturnListId(Integer id);
	
	/**
	 * 通过销售id删除所有销售商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "delete from t_ck_customer_return_list_goods where customer_return_list_id = ?1", nativeQuery = true)
	@Modifying
	public void deleteByCkCustomerReturnListId(Integer saleListId);
	/**
	 * 通过商品id查找退货总数
	 * @param goodsId
	 * @return
	 */
	@Query(value = "select sum(num) as total from t_ck_customer_return_list_goods where goods_id = ?1", nativeQuery = true)
	public Integer getTotalByGoodsId(Integer goodsId);
}