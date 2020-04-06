package com.hengyue.controller.admin.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyue.entity.Log;
import com.hengyue.entity.vo.Department;
import com.hengyue.service.LogService;
import com.hengyue.service.RoleService;
import com.hengyue.service.vo.DepartmentService;
/**
 * 部门管理controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/department")
public class DepartmentAdminController {

	@Resource
	private DepartmentService departmentService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private LogService logService;
	/**
	 * 分页查询部门管理信息
	 * @param department
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = { "部门管理", "员工资料" }, logical = Logical.OR)
	public Map<String, Object> list(Department department, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Department> departmentList = departmentService.list(department, page, rows, Direction.ASC, "id");
		Long total = departmentService.getCount(department);
		map.put("rows", departmentList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询部门管理信息"));
		return map;
	}
	/**
	 * 添加或者修改部门管理信息
	 * @param department
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = { "部门管理", "员工资料" }, logical = Logical.OR)
	public Map<String, Object> save(Department department) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(department.getId() == null) {
			logService.save(new Log(Log.ADD_ACTION, "添加部门管理信息" + department));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新部门管理信息" + department));
		}
		departmentService.save(department);
		map.put("success", true);
		return map;
	}
	/**
	 * 删除部门管理id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value = { "部门管理", "员工资料" }, logical = Logical.OR)
	public Map<String, Object> delete(Integer id) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除部门管理信息" + departmentService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		departmentService.delete(id);
		map.put("success", true);
		return map;
	}
	/**
	 * comboList
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/comboList")
	@RequiresPermissions(value = "员工资料")
	public List<Department> comboList() throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "查看或者修改部门(comboList)"));
		return departmentService.listAll();
	}
}
