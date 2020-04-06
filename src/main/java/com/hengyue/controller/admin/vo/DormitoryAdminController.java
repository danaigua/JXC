package com.hengyue.controller.admin.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyue.entity.Log;
import com.hengyue.entity.vo.Dormitory;
import com.hengyue.entity.vo.Employee;
import com.hengyue.service.LogService;
import com.hengyue.service.vo.DormitoryService;
import com.hengyue.service.vo.EmployeeService;

@RestController
@RequestMapping("/admin/dormitory")
public class DormitoryAdminController {
	
	@Resource
	private DormitoryService dormitoryService;
	
	@Resource
	private EmployeeService employeeService;
	
	@Resource
	private LogService logService;

	/**
	 * 保存或者修改一个宿舍实体
	 * @param dormitory
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "宿舍管理")
	public Map<String, Object> save(Dormitory dormitory){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(dormitory.getId() != null) {
				logService.save(new Log(Log.UPDATE_ACTION, dormitory.toString()));
			}else {
				logService.save(new Log(Log.ADD_ACTION, dormitory.toString()));
			}
			dormitoryService.save(dormitory);
			map.put("success", true);
		}catch(Exception e) {
			map.put("success", false);
			map.put("errorInfo", "系统出现一点故障，请稍后重试！");
		}
		return map;
	}
	/**
	 * 删除一个宿舍实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "宿舍管理")
	public Map<String, Object> delete(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dormitoryService.delete(id);
			map.put("success", true);
		}catch(Exception e) {
			map.put("success", false);
			map.put("errorInfo", "系统出现一点故障，请稍后重试！故障地址：/admin/dormimtory");
		}
		return map;
	}
	/**
	 * 删除一个宿舍实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = "宿舍管理")
	public Map<String, Object> list(Dormitory dormitory) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Dormitory> dormitoryList = dormitoryService.list(dormitory);
		try {
			for (Dormitory dormitory2 : dormitoryList) {
				if(dormitory2.getEmployee1() != null) {
					Employee employee1 = employeeService.findById(dormitory2.getEmployee1());
					dormitory2.setEmployee1Name(employee1.getName());
				}
				if(dormitory2.getEmployee2() != null) {
					Employee employee2 = employeeService.findById(dormitory2.getEmployee2());
					dormitory2.setEmployee2Name(employee2.getName());
				}
				if(dormitory2.getEmployee3() != null) {
					Employee employee3 = employeeService.findById(dormitory2.getEmployee3());
					dormitory2.setEmployee3Name(employee3.getName());
				}
				if(dormitory2.getEmployee4() != null) {
					Employee employee4 = employeeService.findById(dormitory2.getEmployee4());
					dormitory2.setEmployee4Name(employee4.getName());
				}
				if(dormitory2.getEmployee5() != null) {
					Employee employee5 = employeeService.findById(dormitory2.getEmployee5());
					dormitory2.setEmployee5Name(employee5.getName());
				}
				if(dormitory2.getEmployee6() != null) {
					Employee employee6 = employeeService.findById(dormitory2.getEmployee6());
					dormitory2.setEmployee6Name(employee6.getName());
				}
				if(dormitory2.getEmployee7() != null) {
					Employee employee7 = employeeService.findById(dormitory2.getEmployee7());
					dormitory2.setEmployee7Name(employee7.getName());
				}
				if(dormitory2.getEmployee8() != null) {
					Employee employee8 = employeeService.findById(dormitory2.getEmployee8());
					dormitory2.setEmployee8Name(employee8.getName());
				}
			}
			map.put("rows", dormitoryList);
		}catch(Exception e) {
			map.put("success", false);
			map.put("errorInfo", "系统出现一点故障，请稍后重试！故障地址：/admin/dormimtory");
		}
		return map;
	}
}
