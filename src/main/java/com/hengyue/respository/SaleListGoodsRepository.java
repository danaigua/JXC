package com.hengyue.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.SaleListGoods;

/**
 * 销售商品持久层接口
 * @author 章家宝
 *
 */
public interface SaleListGoodsRepository extends JpaRepository<SaleListGoods, Integer> {

	/**
	 * 通过销售id查找销售商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select * from t_sale_list_goods where sale_list_id = ?1", nativeQuery = true)
	public List<SaleListGoods> listBySaleListId(Integer id);
	
	/**
	 * 通过销售id删除所有销售商品
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "delete from t_sale_list_goods where sale_list_id = ?1", nativeQuery = true)
	@Modifying
	public void deleteBySaleListId(Integer saleListId);
	/**
	 * 通过商品id查找销售总数
	 * @param goodsId
	 * @return
	 */
	@Query(value = "select sum(num) as total from t_sale_list_goods where goods_id = ?1", nativeQuery = true)
	public Integer getTotalByGoodsId(Integer goodsId);
}
