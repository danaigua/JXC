package com.hengyue.controller.admin.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyue.entity.Log;
import com.hengyue.entity.vo.Assess;
import com.hengyue.service.LogService;
import com.hengyue.service.RoleService;
import com.hengyue.service.vo.AssessService;
/**
 * 绩效考核controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/assess")
public class AssessAdminController {

	@Resource
	private AssessService assessService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private LogService logService;
	/**
	 * 分页查询绩效考核信息
	 * @param assess
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = "绩效考核")
	public Map<String, Object> list(Assess assess, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Assess> assessList = assessService.list(assess, page, rows, Direction.ASC, "id");
		Long total = assessService.getCount(assess);
		map.put("rows", assessList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询绩效考核信息"));
		return map;
	}
	/**
	 * 添加或者修改绩效考核信息
	 * @param assess
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = "绩效考核")
	public Map<String, Object> save(Assess assess) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(assess.getId() == null) {
			logService.save(new Log(Log.ADD_ACTION, "添加绩效考核信息" + assess));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新绩效考核信息" + assess));
		}
		assessService.save(assess);
		map.put("success", true);
		return map;
	}
	/**
	 * 删除绩效考核id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value = "绩效考核")
	public Map<String, Object> delete(Integer id) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除绩效考核信息" + assessService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		assessService.delete(id);
		map.put("success", true);
		return map;
	}
}
