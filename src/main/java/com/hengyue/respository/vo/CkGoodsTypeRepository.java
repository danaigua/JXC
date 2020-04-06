package com.hengyue.respository.vo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.CkGoodsType;

/**
 * 商品类别持久层接口
 * @author 章家宝
 *
 */
public interface CkGoodsTypeRepository extends JpaRepository<CkGoodsType, Integer> {
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	@Query(value = "select * from t_ck_goodstype where p_id = ?1", nativeQuery = true)
	public List<CkGoodsType> findByParentId(int parentId);

	/**
	 * 通过型号查找实体
	 * @param substring
	 * @return
	 */
	@Query(value = "select * from t_ck_goodstype where model = ?1", nativeQuery = true)
	public CkGoodsType findByModelName(String substring);

	/**
	 * 通过型号查找实体id
	 * @param substring
	 * @return
	 */
	@Query(value = "select t.id from t_ck_goodstype t where model = ?1", nativeQuery = true)
	public Integer findByModel(CharSequence subSequence);
}
