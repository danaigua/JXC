package com.hengyue.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hengyue.entity.GoodsUnit;

/**
 * 商品单位持久层接口
 * @author 章家宝
 *
 */
public interface GoodsUnitRepository extends JpaRepository<GoodsUnit, Integer> {

}
