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
import com.hengyue.entity.vo.Position;
import com.hengyue.entity.vo.Salary;
import com.hengyue.service.LogService;
import com.hengyue.service.RoleService;
import com.hengyue.service.vo.PositionService;
import com.hengyue.service.vo.SalaryService;
import com.hengyue.utils.StringUtils;
/**
 * 职位管理controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/position")
public class PositionAdminController {

	@Resource
	private PositionService positionService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private LogService logService;
	
	@Resource
	private SalaryService salaryService;
	/**
	 * 分页查询职位管理信息
	 * @param position
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = "职位管理")
	public Map<String, Object> list(Position position, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Position> positionList = positionService.list(position, page, rows, Direction.ASC, "id");
		for (Position position2 : positionList) {
			if(position2.getSalary() != null) {
				position2.setSalaryName(position2.getSalary().getName());
			}
		}
		Long total = positionService.getCount(position);
		map.put("rows", positionList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询职位管理信息"));
		return map;
	}
	/**
	 * 添加或者修改职位管理信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = "职位管理")
	public Map<String, Object> save(Position position, @RequestParam(value = "salaryId", required = false) Integer salaryId) 
			throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(salaryId != null && salaryId != 0) {
			Salary salary = salaryService.findById(salaryId);
			position.setSalary(salary);
		}else {
			if(position.getId() != null && position.getId() != 0) {
				Position position2 = positionService.findById(position.getId());
				if(position2 != null) {
					position.setSalary(position2.getSalary());
				}else {
					position.setSalary(null);
				}
			}
		}
		if(position.getId() == null) {
			logService.save(new Log(Log.ADD_ACTION, "添加职位管理信息" + position));
		}else {
			Position position3 = positionService.findById(position.getId());
			if(StringUtils.isEmpty(position.getName())) {
				position.setName(position3.getName());
			}
			if(StringUtils.isEmpty(position.getRemarks())) {
				position.setRemarks(position3.getRemarks());
			}
			logService.save(new Log(Log.UPDATE_ACTION, "更新职位管理信息" + position));
		}
		positionService.save(position);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 删除职位管理id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value = "职位管理")
	public Map<String, Object> delete(Integer id) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除职位管理信息" + positionService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		positionService.delete(id);
		map.put("success", true);
		return map;
	}
	@ResponseBody
	@RequestMapping("/comboList")
	@RequiresPermissions(value = "员工资料")
	public List<Position> comboList() throws Exception{
		return positionService.findAll();
	}
}
