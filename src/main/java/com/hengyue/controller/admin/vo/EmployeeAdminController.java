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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyue.entity.Log;
import com.hengyue.entity.vo.Department;
import com.hengyue.entity.vo.Employee;
import com.hengyue.service.LogService;
import com.hengyue.service.RoleService;
import com.hengyue.service.vo.DepartmentService;
import com.hengyue.service.vo.EmployeeService;
/**
 * 员工管理controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/employee")
public class EmployeeAdminController {

	@Resource
	private EmployeeService employeeService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private LogService logService;
	
	@Resource
	private DepartmentService departmentService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/**
	 * 分页查询员工管理信息
	 * @param employee
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = "员工管理")
	public Map<String, Object> list(Employee employee, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Employee> employeeList = employeeService.list(employee, page, rows, Direction.ASC, "id");
		for (Employee employee2 : employeeList) {
			if(employee2.getDepartment() != null) {
				employee2.setDepartmentName(employee2.getDepartment().getName());
			}
		}
		Long total = employeeService.getCount(employee);
		map.put("rows", employeeList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询员工管理信息"));
		return map;
	}
	/**
	 * 添加或者修改员工管理信息
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = "员工管理")
	public Map<String, Object> save(Employee employee, @RequestParam(value = "departmentId", required = false) Integer departmentId)
			throws Exception{
		if(departmentId != null && departmentId != 0) {
			Department department2 = departmentService.findById(departmentId);
			employee.setDepartment(department2);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(employee.getId() == null) {
			logService.save(new Log(Log.ADD_ACTION, "添加员工管理信息" + employee));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新员工管理信息" + employee));
		}
		employeeService.save(employee);
		map.put("success", true);
		return map;
	}
	/**
	 * 删除员工管理id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value = "员工管理")
	public Map<String, Object> delete(Integer id) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除员工管理信息" + employeeService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		employeeService.delete(id);
		map.put("success", true);
		return map;
	}
	/**
	 * combobox用
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/comboList")
	@RequiresPermissions(value = {"薪资生成","食堂管理"}, logical = Logical.OR)
	public List<Employee> comboList(@RequestParam(value = "q", required = false)String q) throws Exception{
		if(StringUtils.isEmpty(q)) {
			q = "";
		}else {
			q = "%" + q + "%";
		}
		return employeeService.comboList(q);
	}
	/**
	 * combobox用
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/combobox")
	@RequiresPermissions(value = {"薪资生成","食堂管理"}, logical = Logical.OR)
	public List<Employee> combobox() throws Exception{
		return employeeService.listAll();
	}
	/**
	 * 查看姓名是否存在
	 * 如果存在那么就不能修改，本系统中姓名不能重复
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/isExist")
	@RequiresPermissions(value = "员工资料")
	public Map<String, Object> isExist(String name) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Employee employee = employeeService.findByName(name);
		if(employee != null) {
			map.put("success", false);
			map.put("errorInfo", "姓名为" + name + "已经被" + employee.getDepartment().getName() + "一位同事占用，请您重新命名，可以是" + name + "-部门");
			return map;
		}else {
			map.put("success", true);
			return map;
		}
	}
}
