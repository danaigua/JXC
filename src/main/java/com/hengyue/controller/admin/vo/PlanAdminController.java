package com.hengyue.controller.admin.vo;

import java.text.SimpleDateFormat;
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
import com.hengyue.entity.vo.Plan;
import com.hengyue.entity.vo.PlanPlanCourse;
import com.hengyue.service.LogService;
import com.hengyue.service.RoleService;
import com.hengyue.service.UserService;
import com.hengyue.service.vo.PlanCourseService;
import com.hengyue.service.vo.PlanCourseStandardService;
import com.hengyue.service.vo.PlanPlanCourseService;
import com.hengyue.service.vo.PlanService;
import com.hengyue.utils.StringUtils;
/**
 * 计划controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/plan")
public class PlanAdminController {

	@Resource
	private PlanService planService;
	
	@Resource
	private PlanPlanCourseService planPlanCourseService;
	
	@Resource
	private PlanCourseStandardService planCourseStandardService;
	
	@Resource
	private PlanCourseService planCourseService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private LogService logService;
	
	@Resource
	private UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	/**
	 * 分页查询计划信息
	 * @param plan
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = {"项目规划", "项目进度"}, logical = Logical.OR)
	public Map<String, Object> list(Plan plan, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Plan> planList = planService.list(plan, page, rows, Direction.ASC, "id");
		for (Plan plan2 : planList) {
			User user = userService.findById(plan2.getUserId());
			if(user != null) {
				plan2.setPrincipal(user.getTrueName());
			}	
		}
		Long total = planService.getCount(plan);
		map.put("rows", planList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询计划信息"));
		return map;
	}
	/**
	 * 添加或者修改计划信息
	 * @param plan
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = {"项目规划", "项目进度"}, logical = Logical.OR)
	public Map<String, Object> save(Plan plan, HttpSession session) throws Exception{
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(plan.getUserId()==null || userService.findById(plan.getUserId()) == null) {
			map.put("success", false);
			map.put("errorInfo", "您添加的项目总负责人不在！");
			return map;
		}
		
		User user = (User) session.getAttribute("currentUser");
		if(user == null || user.getId() >= 5) {
			map.put("success", false);
			map.put("errorInfo", "您没有权限添加项目");
			return map;
		}
		if(plan.getId() == null) {
			plan.setSchedule(0);
			plan.setTime(new Date());
			plan.setFinish(0);
			logService.save(new Log(Log.ADD_ACTION, "添加计划信息" + plan));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新计划信息" + plan));
		}
		planService.save(plan);
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
	@RequiresPermissions(value = {"项目规划", "项目进度"}, logical = Logical.OR)
	public Map<String, Object> delete(Integer id, HttpSession session) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除计划信息" + planService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("currentUser");
		if(user == null || user.getId() >= 5) {
			map.put("success", false);
			map.put("errorInfo", "您没有权限删除项目");
			return map;
		}
		//找出所有的任务
		List<PlanPlanCourse> planPlanCourseList = planPlanCourseService.findByPlanId(id);
		for (PlanPlanCourse planPlanCourse : planPlanCourseList) {
			int planCourseId = planPlanCourse.getPlanCourse().getId();
			//删除关联
			planPlanCourseService.delete(planPlanCourse.getId());
			//删除掉任务
			planCourseService.delete(planCourseId);
		}
		planService.delete(id);
		map.put("success", true);
		return map;
	}
}
