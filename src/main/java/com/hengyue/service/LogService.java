package com.hengyue.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.hengyue.entity.Log;
/**
 * 日志service接口
 * @author 章家宝
 *
 */
public interface LogService {
	/**
	 * 添加或者修改日志信息
	 * @param log
	 */
	public void save(Log log);
	/**
	 * 分页查询日志信息
	 * @param log
	 * @param page
	 * @param PageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Log> list(Log log, Integer page, Integer PageSize, Direction direction, String...properties);
	/**
	 * 获取总记录数
	 * @param log
	 * @return
	 */
	public Long getCount(Log log);
}
