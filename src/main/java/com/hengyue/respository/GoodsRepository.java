package com.hengyue.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.Goods;

/**
 * 商品持久层接口
 *
 * @author 章家宝
 *
 */
public interface GoodsRepository extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods> {
	/**
	 * 查询某个类别下得所有商品
	 * 
	 * @param parentId
	 * @return
	 */
	@Query(value = "select * from t_goods where type_id = ?1", nativeQuery = true)
	public List<Goods > findByTypeId(int typeId);
	
	/**
	 * 获取最大的商品编码
	 * @return
	 */
	@Query(value = "SELECT MAX(CODE) FROM t_goods", nativeQuery = true)
	public String getMaxGoodsCode();
	 
	/**
	 * 获取报警的商品
	 * @return
	 */
	@Query(value = "SELECT * FROM t_goods where inventory_quantity < min_num", nativeQuery = true)
	public List<Goods> listAlarm();
}
