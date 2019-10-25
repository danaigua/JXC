package com.hengyue.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.OverflowListGoods;

/**
 * 商品报溢单商品持久层接口
 * @author 章家宝
 *
 */
public interface OverflowListGoodsRepository extends JpaRepository<OverflowListGoods, Integer> {

	/**
	 * 通过商品报溢单id查找商品报溢单商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select * from t_overflow_list_goods where overflow_list_id = ?1", nativeQuery = true)
	public List<OverflowListGoods> listByOverflowListId(Integer id);
	
	/**
	 * 通过商品报溢单id删除所有商品报溢单商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "delete from t_overflow_list_goods where overflow_list_id = ?1", nativeQuery = true)
	@Modifying
	public void deleteByOverflowListId(Integer overflowListId);
}
