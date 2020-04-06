package com.hengyue.controller.admin.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
import com.hengyue.entity.User;
import com.hengyue.entity.vo.PlanCourseStandard;
import com.hengyue.entity.vo.PlanCourseStandardType;
import com.hengyue.service.LogService;
import com.hengyue.service.RoleService;
import com.hengyue.service.UserService;
import com.hengyue.service.vo.PlanCourseStandardService;
import com.hengyue.service.vo.PlanCourseStandardTypeService;
/**
 * 计划controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/planCourseStandard")
public class PlanCourseStandardAdminController {

	@Resource
	private PlanCourseStandardService planCourseStandardService;
	
	@Resource
	private PlanCourseStandardTypeService planCourseStandardTypeService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private LogService logService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	/**
	 * 分页查询计划信息
	 * @param planCourseStandard
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = {"项目规划", "项目进度", "执行标准"}, logical = Logical.OR)
	public Map<String, Object> list(PlanCourseStandard planCourseStandard, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<PlanCourseStandard> planCourseStandardList = planCourseStandardService.list(planCourseStandard, page, rows, Direction.ASC, "id");
		Long total = planCourseStandardService.getCount(planCourseStandard);
		map.put("rows", planCourseStandardList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询计划信息"));
		return map;
	}
	/**
	 * 添加或者修改计划信息
	 * @param planCourseStandard
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = {"项目规划", "项目进度", "执行标准"}, logical = Logical.OR)
	public Map<String, Object> save(PlanCourseStandard planCourseStandard, 
			@RequestParam(value = "typeId", required = false) Integer typeId,
			HttpSession session ) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("currentUser");
		if(user.getId() > 5) {
			map.put("success", false);
			map.put("errorInfo", "添加失败，您没有权限添加！");
			return map;
		}
		if(typeId != null) {
			PlanCourseStandardType planCourseStandardType = planCourseStandardTypeService.findById(typeId);
			if(planCourseStandardType != null ) {
				planCourseStandard.setPlanCourseStandardType(planCourseStandardType);
			}
		}
		
		if(planCourseStandard.getId() == null) {
			planCourseStandard.setCreateTimes(new Date());
			logService.save(new Log(Log.ADD_ACTION, "添加计划信息" + planCourseStandard));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新计划信息" + planCourseStandard));
		}
		planCourseStandardService.save(planCourseStandard);
		map.put("success", true);
		return map;
	}
	/**
	 * 删除计划id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value = {"项目规划", "项目进度", "执行标准"}, logical = Logical.OR)
	public Map<String, Object> delete(Integer id, HttpSession session) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除计划信息" + planCourseStandardService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("currentUser");
		if(user.getId() > 5) {
			map.put("success", false);
			map.put("errorInfo", "抱歉！您没有权限删除，请联系管理员！");
			return map;
		}
		
		planCourseStandardService.delete(id);
		map.put("success", true);
		return map;
	}
	/**
	 *combobox
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/combobox")
	@RequiresPermissions(value = {"项目规划", "项目进度", "执行标准"}, logical = Logical.OR)
	public List<Map<String, Object>> combobox() throws Exception{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<PlanCourseStandard> planCourseStandardList = planCourseStandardService.findAll();
		for (PlanCourseStandard planCourseStandard : planCourseStandardList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", planCourseStandard.getName());
			map.put("id", planCourseStandard.getId());
			list.add(map);
		}
		return list;
	}
}
