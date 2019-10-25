package com.hengyue.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.PurchaseListGoods;

/**
 * 进货单商品持久层接口
 * @author 章家宝
 *
 */
public interface PurchaseListGoodsRepository extends JpaRepository<PurchaseListGoods, Integer>, JpaSpecificationExecutor<PurchaseListGoods> {

	/**
	 * 通过进货单id查找进货单商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select * from t_purchase_list_goods where purchase_list_id = ?1", nativeQuery = true)
	public List<PurchaseListGoods> listByPurchaseListId(Integer id);
	
	/**
	 * 通过进货单id删除所有进货单商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "delete from t_purchase_list_goods where purchase_list_id = ?1", nativeQuery = true)
	@Modifying
	public void deleteByPurchaseListId(Integer purchaseListId);
}
