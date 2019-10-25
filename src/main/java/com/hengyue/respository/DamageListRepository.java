package com.hengyue.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.DamageList;

/**
 * 商品报损单持久层接口
 * @author 章家宝
 *
 */
public interface DamageListRepository extends JpaRepository<DamageList, Integer>, JpaSpecificationExecutor<DamageList> {

	/**
	 * 获取当天商品报损单号
	 * @return
	 */
	@Query(value = "SELECT MAX(damage_number) FROM t_damage_list WHERE TO_DAYS (damage_date)", nativeQuery = true)
	public String getTodayMaxDamageNumber();
}
