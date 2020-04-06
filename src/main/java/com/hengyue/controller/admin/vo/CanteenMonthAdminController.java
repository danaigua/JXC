package com.hengyue.controller.admin.vo;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hengyue.entity.Log;
import com.hengyue.entity.vo.CanteenMonth;
import com.hengyue.service.LogService;
import com.hengyue.service.vo.CanteenMonthService;
import com.hengyue.utils.StringUtils;
/**
 * 餐厅就餐controller
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/canteenMonth")
public class CanteenMonthAdminController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private CanteenMonthService canteenMonthService;
	
	@Resource
	private LogService logService;
	/**
	 * 分页查询餐厅就餐信息
	 * @param canteenMonth
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = "食堂管理")
	public Map<String, Object> list(
			CanteenMonth canteenMonth, 
			@RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<CanteenMonth> canteenMonthList = canteenMonthService.list(canteenMonth, page, rows, Direction.DESC, "times");
		for (CanteenMonth canteenMonth2 : canteenMonthList) {
			if(StringUtils.isNotEmpty(canteenMonth2.getAfternoon())) {
				canteenMonth2.setAfternoonNum(canteenMonth2.getAfternoon().split(",").length + "人");
			}
			if(StringUtils.isNotEmpty(canteenMonth2.getNight())) {
				canteenMonth2.setNightNum(canteenMonth2.getNight().split(",").length + "人");
			}
			if(StringUtils.isNotEmpty(canteenMonth2.getAfternoonAndNight())) {
				canteenMonth2.setAfternoonAndNightNum(canteenMonth2.getAfternoonAndNight().split(",").length + "人");
			}
			if(StringUtils.isNotEmpty(canteenMonth2.getSandun())) {
				canteenMonth2.setSandunNum(canteenMonth2.getSandun().split(",").length + "人");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String timeString = sdf.format(canteenMonth2.getTimes());
			canteenMonth2.setTimeString(timeString);
		}
		map.put("rows", canteenMonthList);
		map.put("total", canteenMonthList.size());
		logService.save(new Log(Log.SEARCH_ACTION, "查询餐厅就餐信息"));
		return map;
	}
	/**
	 * 添加或者修改餐厅就餐信息
	 * @param canteenMonth
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "食堂管理")
	public Map<String, Object> save(CanteenMonth canteenMonth) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(canteenMonth.getId() == null) {
			logService.save(new Log(Log.ADD_ACTION, "添加餐厅就餐信息" + canteenMonth));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新餐厅就餐信息" + canteenMonth));
		}
		canteenMonthService.save(canteenMonth);
		map.put("success", true);
		return map;
	}
}
