package com.hengyue.controller.admin.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyue.entity.Log;
import com.hengyue.entity.vo.Time;
import com.hengyue.service.LogService;
import com.hengyue.service.RoleService;
import com.hengyue.service.vo.TimeService;
/**
 * 工作时间controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/time")
public class TimeAdminController {

	@Resource
	private TimeService timeService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private LogService logService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	/**
	 * 分页查询工作时间信息
	 * @param time
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = {"工作时间", "数据生成"}, logical = Logical.OR)
	public Map<String, Object> list(Time time, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Time> timeList = timeService.list(time, page, rows, Direction.ASC, "id");
		Long total = timeService.getCount(time);
		map.put("rows", timeList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询工作时间信息"));
		return map;
	}
	/**
	 * 添加或者修改工作时间信息
	 * @param time
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = {"工作时间", "数据生成"}, logical = Logical.OR)
	public Map<String, Object> save(Time time) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(time.getId() == null) {
			logService.save(new Log(Log.ADD_ACTION, "添加工作时间信息" + time));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新工作时间信息" + time));
		}
		timeService.save(time);
		map.put("success", true);
		return map;
	}
	/**
	 * 删除工作时间id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value = {"工作时间", "数据生成"}, logical = Logical.OR)
	public Map<String, Object> delete(Integer id) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除工作时间信息" + timeService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		timeService.delete(id);
		map.put("success", true);
		return map;
	}
}
