package com.hengyue.respository.vo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hengyue.entity.vo.CanteenMonth;

public interface CanteenMonthRepository extends JpaRepository<CanteenMonth, Integer>, JpaSpecificationExecutor<CanteenMonth> {

	/**
	 * 通过时间查找食堂就餐是否存在
	 * @param times
	 * @return
	 */
	public CanteenMonth findByTimes(Date times);
	
	
}
