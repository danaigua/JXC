package com.hengyue.respository.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.CkGoods;

/**
 * 商品持久层接口
 *
 * @author 章家宝
 *
 */
public interface CkGoodsRepository extends JpaRepository<CkGoods, Integer>, JpaSpecificationExecutor<CkGoods> {
	/**
	 * 查询某个类别下得所有商品
	 * 
	 * @param parentId
	 * @return
	 */
	@Query(value = "select * from t_ck_goods where type_id = ?1", nativeQuery = true)
	public List<CkGoods > findByTypeId(int typeId);
	
	 
	/**
	 * 获取报警的商品
	 * @return
	 */
	@Query(value = "SELECT * FROM t_ck_goods where inventory_quantity < min_num", nativeQuery = true)
	public List<CkGoods> listAlarm();
	/**
	 * 获取最大的商品编码
	 * @return
	 */
	@Query(value = "SELECT MAX(CODE) FROM t_ck_goods", nativeQuery = true)
	public String getMaxCkGoodsCode();

	/**
	 * 通过模型名称查找实体
	 * @param string
	 * @return
	 */
	@Query(value = "SELECT * FROM t_ck_goods where model = ?1", nativeQuery = true)
	public CkGoods findBymodelName(String string);
}
