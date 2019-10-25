package com.hengyue.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.DamageListGoods;

/**
 * 商品报损单商品持久层接口
 * @author 章家宝
 *
 */
public interface DamageListGoodsRepository extends JpaRepository<DamageListGoods, Integer> {

	/**
	 * 通过商品报损单id查找商品报损单商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select * from t_damage_list_goods where damage_list_id = ?1", nativeQuery = true)
	public List<DamageListGoods> listByDamageListId(Integer id);
	
	/**
	 * 通过商品报损单id删除所有商品报损单商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "delete from t_damage_list_goods where damage_list_id = ?1", nativeQuery = true)
	@Modifying
	public void deleteByDamageListId(Integer damageListId);
}
