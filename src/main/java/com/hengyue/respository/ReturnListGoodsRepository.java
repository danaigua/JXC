package com.hengyue.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.ReturnListGoods;

/**
 * 退货单商品持久层接口
 * @author 章家宝
 *
 */
public interface ReturnListGoodsRepository extends JpaRepository<ReturnListGoods, Integer> {

	/**
	 * 通过退货单id查找退货单商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select * from t_return_list_goods where return_list_id = ?1", nativeQuery = true)
	public List<ReturnListGoods> listByReturnListId(Integer id);
	
	/**
	 * 通过退货单id删除所有退货单商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "delete from t_return_list_goods where return_list_id = ?1", nativeQuery = true)
	@Modifying
	public void deleteByReturnListId(Integer returnListId);
}
