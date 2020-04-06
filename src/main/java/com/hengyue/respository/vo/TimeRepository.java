package com.hengyue.respository.vo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.vo.Time;

/**
 * 上班时间持久层接口
 * @author 章家宝
 *
 */
public interface TimeRepository extends JpaRepository<Time, Integer>, JpaSpecificationExecutor<Time> {

	/**
	 * 通过员工姓名以及日期查询时间上班对象
	 * @param employeeName
	 * @param date
	 * @return
	 */
	@Query(value = "select * from t_time where employee_name = ?1 and work_date = ?2", nativeQuery = true)
	public Time findByEmployeeNameAndDate(String employeeName, Date date);
}
