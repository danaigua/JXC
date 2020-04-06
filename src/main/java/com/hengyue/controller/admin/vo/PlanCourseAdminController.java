package com.hengyue.controller.admin.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.hengyue.entity.vo.PlanCourse;
import com.hengyue.entity.vo.PlanCourseStandard;
import com.hengyue.entity.vo.PlanPlanCourse;
import com.hengyue.service.LogService;
import com.hengyue.service.RoleService;
import com.hengyue.service.UserService;
import com.hengyue.service.vo.PlanCourseService;
import com.hengyue.service.vo.PlanCourseStandardService;
import com.hengyue.service.vo.PlanPlanCourseService;
import com.hengyue.service.vo.PlanService;
/**
 * 计划controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/planCourse")
public class PlanCourseAdminController {

	@Resource
	private PlanCourseService planCourseService;
	
	@Resource
	private PlanCourseStandardService planCourseStandardService;
	
	@Resource
	private PlanPlanCourseService planPlanCourseService;
	
	@Resource
	private PlanService planService;
	
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
	 * @param planCourse
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = {"项目规划"}, logical = Logical.OR)
	public Map<String, Object> list(PlanCourse planCourse, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<PlanCourse> planCourseList = planCourseService.list(planCourse, page, rows, Direction.ASC, "id");
		for (PlanCourse plan2 : planCourseList) {
			User user = userService.findById(plan2.getUserId());
			if(user != null) {
				plan2.setPrincipal(user.getTrueName());
			}
		}
		Long total = planCourseService.getCount(planCourse);
		map.put("rows", planCourseList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询计划信息"));
		return map;
	}
	/**
	 * 不分页查询计划信息
	 * @param planCourse
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/listByPlanId")
	@RequiresPermissions(value = {"项目规划"}, logical = Logical.OR)
	public Map<String, Object> list(@RequestParam(value = "planId", required = false)Integer planId, 
			@RequestParam(value = "finish", required = false) Integer finish) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(planId == null || planId == 0) {
			map.put("rows", null);
			return map;
		}
		if(finish == null ) {
			finish = 0;
		}
		List<PlanPlanCourse> planPlanCourseList = planPlanCourseService.findByPlanId(planId);
		List<PlanCourse> planCourseList = new ArrayList<PlanCourse>();
		for (PlanPlanCourse planPlanCourse : planPlanCourseList) {
			if(planPlanCourse.getPlanCourse().getFinish() == finish) {
				User user = userService.findById(planPlanCourse.getPlanCourse().getUserId());
				if(user != null) {
					planPlanCourse.getPlanCourse().setPrincipal(user.getTrueName());
				}
				if(planPlanCourse.getPlanCourse().getAffirm() == 0) {
					planPlanCourse.getPlanCourse().setQuestionState("未确认");
				}else {
					planPlanCourse.getPlanCourse().setQuestionState("确认");
				}
				planCourseList.add(planPlanCourse.getPlanCourse());
			}
		}
		map.put("rows", planCourseList);
		logService.save(new Log(Log.SEARCH_ACTION, "查询计划中的任务信息"));
		return map;
	}
	/**
	 * 添加或者修改计划信息
	 * @param planCourse
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = {"项目规划"}, logical = Logical.OR)
	public Map<String, Object> save(PlanCourse planCourse, HttpSession session,
			@RequestParam(value = "planId", required = false) Integer planId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		PlanCourseStandard planCourseStandard;
		if(planCourse.getPlanCourseStandard() != null && planCourse.getPlanCourseStandard().getId() != null) {
			planCourseStandard = planCourseStandardService.findById(planCourse.getPlanCourseStandard().getId());
			if(planCourseStandard != null) {
				planCourse.setPlanCourseStandard(planCourseStandard);
			}else {
				map.put("success", false);
				map.put("errorInfo", "对不起，您所填写的标准不存在！请联系管理员添加标准之后再选中！");
				return map;
			}
		}
		if(planCourse.getId() == null) {
			planCourse.setTime(new Date());
			Plan plan = planService.findById(planId);
			UUID uuid = UUID.randomUUID();
			planCourse.setKeysname(uuid.toString());
			planCourseService.save(planCourse);
			PlanPlanCourse planPlanCourse = new PlanPlanCourse();
			planPlanCourse.setPlan(plan);
			PlanCourse newPlanCourse = planCourseService.findByUUID(uuid.toString());
			planPlanCourse.setPlanCourse(newPlanCourse);
			planPlanCourseService.save(planPlanCourse);
			logService.save(new Log(Log.ADD_ACTION, "添加计划信息" + planCourse));
		}else {
			/*
			 * PlanCourse planCourseCheck = planCourseService.findById(planCourse.getId());
			 * if(planCourseCheck.getPlanCourseStandard().getId() !=
			 * planCourse.getPlanCourseStandard().getId()) { User user = (User)
			 * session.getAttribute("currentUser"); if(user.getId() > 5) {
			 * map.put("success", false); map.put("errorInfo", "对不起，您无法更改标准参数选项，请联系管理员修改");
			 * return map; } }
			 * 在保存之前不能查找该实体
			 * 要不然jpa会不进行保存
			 */
			logService.save(new Log(Log.UPDATE_ACTION, "更新计划信息" + planCourse));
			planCourseService.save(planCourse);
		}
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
	@RequiresPermissions(value = {"项目规划"}, logical = Logical.OR)
	public Map<String, Object> delete(Integer id) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除计划信息" + planCourseService.findById(id)));
		
		//先删除关联，在删除实体
//		planPlanCourseService.deleteByPlanCourseId(id);
		PlanPlanCourse planPlanCourse = planPlanCourseService.findByPlanCourseId(id);
		if(planPlanCourse != null) {
			planPlanCourseService.delete(planPlanCourse.getId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		planCourseService.delete(id);
		map.put("success", true);
		return map;
	}
	/**
	 * 
	 * @param session
	 * @param finish
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/listByUserId")
	@RequiresPermissions(value = {"当前任务"}, logical = Logical.OR)
	public Map<String, Object> listByUserId(HttpSession session, Integer finish) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("currentUser");
		if(finish == null) {
			finish = 0;
		}
		if(user != null) {
			List<PlanCourse> planCourseList = planCourseService.findByUserId(user.getId());
			List<PlanCourse> pList = new ArrayList<PlanCourse>();
			for (PlanCourse planCourse : planCourseList) {
				if(planCourse.getFinish() == finish) {
					Plan plan = planService.findByPlanCourseId(planCourse.getId());
					if(plan != null) {
						planCourse.setProjectName(plan.getName());
						User currentUser = userService.findById(plan.getUserId());
						if(currentUser != null) {
							planCourse.setProjectPrincipal(currentUser.getTrueName());
						}
					}
					User currentTaskUser = userService.findById(planCourse.getUserId());
					if(currentTaskUser != null) {
						planCourse.setPrincipal(currentTaskUser.getTrueName());
					}
					if(finish == 0) {
						planCourse.setIsFinish("未完成");
					}else {
						planCourse.setIsFinish("已完成");
					}
					pList.add(planCourse);
				}
			}
			map.put("rows", pList);
		}
		return map;
	}
	/**
	 * 保存文档
	 * @param id
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveDocument")
	@RequiresPermissions(value = {"当前任务"}, logical = Logical.OR)
	public Map<String, Object> saveDocument(Integer id, String content){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PlanCourse planCourse = planCourseService.findById(id);
			if(planCourse != null) {
				planCourse.setValuesname(content);
				planCourseService.save(planCourse);
			}
			map.put("success", true);
			return map;
		}catch(Exception e) {
			map.put("success", false);
			map.put("errorInfo", "系统发生了错误，错误信息为" + e.getMessage());
			return map;
		}
		
	}
	/**
	 * 修改任务为已经完成或者未完成
	 * @param id
	 * @param finish
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setFinish")
	@RequiresPermissions(value = {"当前任务"}, logical = Logical.OR)
	public Map<String, Object> setFinish(Integer id, Integer finish){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PlanCourse planCourse = planCourseService.findById(id);
			if(planCourse != null) {
				planCourse.setFinish(finish);
				planCourseService.save(planCourse);
			}
			map.put("success", true);
			return map;
		}catch(Exception e) {
			map.put("success", false);
			map.put("errorInfo", "系统发生了错误，错误信息为" + e.getMessage());
			return map;
		}
	}
	/**
	 * 修改任务为已经完成或者未完成
	 * @param id
	 * @param finish
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeToAffirm")
	@RequiresPermissions(value = {"项目规划"}, logical = Logical.OR)
	public Map<String, Object> changeToAffirm(Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PlanCourse planCourse = planCourseService.findById(id);
			if(planCourse != null) {
				planCourse.setAffirm(1);
				planCourseService.save(planCourse);
			}
			map.put("success", true);
			return map;
		}catch(Exception e) {
			map.put("success", false);
			map.put("errorInfo", "系统发生了错误，错误信息为" + e.getMessage());
			return map;
		}
	}
	/**
	 * 保存审批
	 * @param id
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/examine")
	@RequiresPermissions(value = {"当前任务"}, logical = Logical.OR)
	public Map<String, Object> examine(Integer examine, Integer id, String examineContent){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PlanCourse planCourse = planCourseService.findById(id);
			if(planCourse != null) {
				if(examine == 1) {
					planCourse.setExamine1(examineContent);
				}else if(examine == 2) {
					planCourse.setExamine2(examineContent);
				}else if(examine == 3) {
					planCourse.setExamine3(examineContent);
				}
				planCourseService.save(planCourse);
			}
			map.put("success", true);
			return map;
		}catch(Exception e) {
			map.put("success", false);
			map.put("errorInfo", "系统发生了错误，错误信息为" + e.getMessage());
			return map;
		}
	}
	/**
	 * 保存审核
	 * @param id
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveReply")
	@RequiresPermissions(value = {"当前任务"}, logical = Logical.OR)
	public Map<String, Object> saveReply(Integer id, String replyContent, Integer reply){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PlanCourse planCourse = planCourseService.findById(id);
			if(planCourse != null) {
				if(reply == 1) {
					planCourse.setReply1(replyContent);
				}else if(reply == 2) {
					planCourse.setReply2(replyContent);
				}else if(reply == 3) {
					planCourse.setReply3(replyContent);
				}
				planCourseService.save(planCourse);
			}
			map.put("success", true);
			return map;
		}catch(Exception e) {
			map.put("success", false);
			map.put("errorInfo", "系统发生了错误，错误信息为" + e.getMessage());
			return map;
		}
		
	}
}
