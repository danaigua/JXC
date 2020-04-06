package com.hengyue.controller.admin.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyue.entity.Log;
import com.hengyue.entity.vo.Employee;
import com.hengyue.entity.vo.Position;
import com.hengyue.entity.vo.Salary;
import com.hengyue.entity.vo.Time;
import com.hengyue.service.LogService;
import com.hengyue.service.RoleService;
import com.hengyue.service.vo.EmployeeService;
import com.hengyue.service.vo.PositionService;
import com.hengyue.service.vo.SalaryService;
import com.hengyue.service.vo.TimeService;
import com.hengyue.utils.DateUtils;
import com.hengyue.utils.ExcelUtils;
import com.hengyue.utils.StringUtils;
/**
 * 薪资管理controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/salary")
public class SalaryAdminController {

	@Resource
	private SalaryService salaryService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private LogService logService;
	
	@Resource
	private TimeService timeService;
	
	@Resource
	private PositionService positionService;
	
	@Resource
	private EmployeeService employeeService;
	/**
	 * 分页查询薪资管理信息
	 * @param salary
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = "薪资管理")
	public Map<String, Object> list(Salary salary, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Salary> salaryList = salaryService.list(salary, page, rows, Direction.ASC, "id");
		Long total = salaryService.getCount(salary);
		map.put("rows", salaryList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询薪资管理信息"));
		return map;
	}
	/**
	 * 添加或者修改薪资管理信息
	 * @param salary
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = "薪资管理")
	public Map<String, Object> save(Salary salary) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(salary.getId() == null) {
			logService.save(new Log(Log.ADD_ACTION, "添加薪资管理信息" + salary));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新薪资管理信息" + salary));
		}
		salaryService.save(salary);
		map.put("success", true);
		return map;
	}
	/**
	 * 删除薪资管理id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value = "薪资管理")
	public Map<String, Object> delete(Integer id) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除薪资管理信息" + salaryService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		salaryService.delete(id);
		map.put("success", true);
		return map;
	}
	/**
	 * combobox
	 * 用于选择薪资模板
	 * <input type="checkbox" name="salary.name" class="easyui-validatebox" 
	 * style="width: 250px" checked onchange="$('#dg').datagrid({selectOnCheck:$(this).is(':checked')})" />
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/comboList")
	@RequiresPermissions(value = "职位管理")
	public List<Salary> combobox() throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "查询薪资管理信息 （combobox）"));
		List<Salary> salaryList = salaryService.listAll();
		return salaryList;
	}
	/**
	 * 获取到指定的薪资
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/salary")
	@RequiresPermissions(value = "薪资生成")
	public Map<String, Object> salary(
			@RequestParam(value = "name", required = false) String name, 
			@RequestParam(value = "beginTime", required = false) String beginTime, 
			@RequestParam(value = "endTime", required = false) String endTime) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Time time = new Time();
		time.setEmployeeName(name);
		time.setbTime(DateUtils.string2Date(beginTime, "yyyy-MM-dd"));
		time.seteTime(DateUtils.string2Date(endTime, "yyyy-MM-dd"));
		List<Time> timeList = timeService.listNoPage(time, Direction.ASC, "id");
		
		List<GetSalary> getSalaryList = new ArrayList<GetSalary>();
		Set<String> nameSet = new HashSet<String>();
		Map<String, GetSalary> getSalaryMap = new HashMap<String, GetSalary>();
		for (Time time2 : timeList) {
			
			String employeeName = time2.getEmployeeName();
			//获取到员工实体
			Employee employee = employeeService.findByName(employeeName);
			//获取到职位
			Position position = positionService.findByName(employee.getPosition());
			//获取到他的工资表
			Salary salary = position.getSalary();
			
			//扣费工资
			double 	feeDeduction = 0;
			int late = 0;
			int leave = 0;
			double lateSalary = 0;
			double leaveSalary = 0;
			StringBuffer sb = new StringBuffer();
			//早上迟到的
			if(time2.getMorningLateTime() > 0 && time2.getMorningLateTime() <= 5) {
				//迟到0~5分钟
				feeDeduction += salary.getLateFiveMin();
				late ++;
				lateSalary += salary.getLateFiveMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "上午迟到" + time2.getMorningLateTime() + "分钟;");
			}
			if(time2.getMorningLateTime() > 5 && time2.getMorningLateTime() <= 10) {
				//迟到0~5分钟
				feeDeduction += salary.getLateTenMin();
				late ++;
				lateSalary += salary.getLateTenMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "上午迟到" + time2.getMorningLateTime() + "分钟;");
			}
			if(time2.getMorningLateTime() > 10 && time2.getMorningLateTime() <= 30) {
				//迟到10分钟~30分钟
				feeDeduction += salary.getLateThanTenLessThirdTenMin();
				late ++;
				lateSalary += salary.getLateThanTenLessThirdTenMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "上午迟到" + time2.getMorningLateTime() + "分钟;");
			}
			
			
			/**
			 * 早上早退
			 */
			if(time2.getMorningLeaveTime() == 1) {
				leave += 1;
				leaveSalary += salary.getLeavetimeOneMin();
				feeDeduction += salary.getLeavetimeOneMin();
			}
			if(time2.getMorningLeaveTime() == 2) {
				leave += 2;
				leaveSalary += salary.getLeavetimeOneMin();
				feeDeduction += salary.getLeavetimeTwoMin();
			}
			
			
			//中午迟到的
			if(time2.getAfternoonLateTime() > 0 && time2.getAfternoonLateTime() <= 5) {
				//迟到0~5分钟
				feeDeduction += salary.getLateFiveMin();
				late ++;
				lateSalary += salary.getLateFiveMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "下午迟到" + time2.getAfternoonLateTime() + "分钟;");
			}
			if(time2.getAfternoonLateTime() > 5 && time2.getAfternoonLateTime() <= 10) {
				//迟到0~5分钟
				feeDeduction += salary.getLateTenMin();
				late ++;
				lateSalary += salary.getLateTenMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "下午迟到" + time2.getAfternoonLateTime() + "分钟;");
			}
			if(time2.getAfternoonLateTime() > 10 && time2.getAfternoonLateTime() <= 30) {
				//迟到10分钟~30分钟
				feeDeduction += salary.getLateThanTenLessThirdTenMin();
				late ++;
				lateSalary += salary.getLateThanTenLessThirdTenMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "下午迟到" + time2.getAfternoonLateTime() + "分钟;");
			}
			/**
			 * 下午早退
			 */
			if(time2.getAfternoonLeaveTime() == 1) {
				leave += 1;
				leaveSalary += salary.getLeavetimeOneMin();
				feeDeduction += salary.getLeavetimeOneMin();
			}
			if(time2.getAfternoonLeaveTime() == 2) {
				leave += 2;
				leaveSalary += salary.getLeavetimeTwoMin();
				feeDeduction += salary.getLeavetimeTwoMin();
			}
			//晚上迟到的
			if(time2.getNightLateTime() > 0 && time2.getNightLateTime() <= 5) {
				//迟到0~5分钟
				late ++;
				lateSalary += salary.getLateFiveMin();
				feeDeduction += salary.getLateFiveMin();
			}
			if(time2.getNightLateTime() > 5 && time2.getNightLateTime() <= 10) {
				//迟到0~5分钟
				late ++;
				lateSalary += salary.getLateTenMin();
				feeDeduction += salary.getLateTenMin();
			}
			if(time2.getNightLateTime() > 10 && time2.getNightLateTime() <= 30) {
				//迟到10分钟~30分钟
				late ++;
				lateSalary += salary.getLateThanTenLessThirdTenMin();
				feeDeduction += salary.getLateThanTenLessThirdTenMin();
			}
			
			if(StringUtils.isNotEmpty(employeeName) && getSalaryMap.get(employeeName) == null) {
				nameSet.add(employeeName);
				GetSalary getSalary = new GetSalary();
				getSalary.setCommonTime(time2.getWorkHours());
				getSalary.setDescription(sb.toString());
				getSalary.setLate(late);
				getSalary.setName(employeeName);
				getSalary.setLateSalary(lateSalary);
				getSalary.setLeaveEarly(leave);
				getSalary.setLeaveEarlySalary(leaveSalary);
				getSalary.setOverTime(time2.getOvertime());
				getSalary.setFeeDeduction(feeDeduction);
				getSalary.setSalary(time2.getSalary());
				getSalaryMap.put(employeeName, getSalary);
			}else {
//				已经存在map里面了
				getSalaryMap.get(employeeName).setCommonTime(getSalaryMap.get(employeeName).getCommonTime() + time2.getWorkHours());
				getSalaryMap.get(employeeName).setDescription(getSalaryMap.get(employeeName).getDescription() + sb.toString());
				getSalaryMap.get(employeeName).setLate(getSalaryMap.get(employeeName).getLate() + late);
				getSalaryMap.get(employeeName).setLateSalary(getSalaryMap.get(employeeName).getLateSalary() + lateSalary);
				getSalaryMap.get(employeeName).setLeaveEarly(getSalaryMap.get(employeeName).getLeaveEarly() + leave);
				getSalaryMap.get(employeeName).setLeaveEarlySalary(getSalaryMap.get(employeeName).getLeaveEarlySalary() + leaveSalary);
				getSalaryMap.get(employeeName).setOverTime(getSalaryMap.get(employeeName).getOverTime() + time2.getOvertime());
				getSalaryMap.get(employeeName).setFeeDeduction(getSalaryMap.get(employeeName).getFeeDeduction() + feeDeduction);
				getSalaryMap.get(employeeName).setSalary(getSalaryMap.get(employeeName).getSalary() + time2.getSalary());
			}
		}
		for (String string : nameSet) {
			getSalaryList.add(getSalaryMap.get(string));
		}
		map.put("rows", getSalaryList);
		map.put("total", getSalaryList.size());
		logService.save(new Log(Log.SEARCH_ACTION, "查询薪资"));
		return map;
	}
	
	
	/**
	 * 获取到指定的薪资
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/excel")
	@RequiresPermissions(value = "薪资生成")
	public void excel(
			@RequestParam(value = "name", required = false) String name, 
			@RequestParam(value = "beginTime", required = false) String beginTime, 
			@RequestParam(value = "endTime", required = false) String endTime,
			 HttpServletResponse response) throws Exception{
		Time time = new Time();
		time.setEmployeeName(name);
		time.setbTime(DateUtils.string2Date(beginTime, "yyyy-MM-dd"));
		time.seteTime(DateUtils.string2Date(endTime, "yyyy-MM-dd"));
		List<Time> timeList = timeService.listNoPage(time, Direction.ASC, "id");
		
		List<GetSalary> getSalaryList = new ArrayList<GetSalary>();
		Set<String> nameSet = new HashSet<String>();
		Map<String, GetSalary> getSalaryMap = new HashMap<String, GetSalary>();
		for (Time time2 : timeList) {
			
			String employeeName = time2.getEmployeeName();
			//获取到员工实体
			Employee employee = employeeService.findByName(employeeName);
			//获取到职位
			Position position = positionService.findByName(employee.getPosition());
			//获取到他的工资表
			Salary salary = position.getSalary();
			
			//扣费工资
			double 	feeDeduction = 0;
			int late = 0;
			int leave = 0;
			double lateSalary = 0;
			double leaveSalary = 0;
			StringBuffer sb = new StringBuffer();
			//早上迟到的
			if(time2.getMorningLateTime() > 0 && time2.getMorningLateTime() <= 5) {
				//迟到0~5分钟
				feeDeduction += salary.getLateFiveMin();
				late ++;
				lateSalary += salary.getLateFiveMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "上午迟到" + time2.getMorningLateTime() + "分钟;");
			}
			if(time2.getMorningLateTime() > 5 && time2.getMorningLateTime() <= 10) {
				//迟到0~5分钟
				feeDeduction += salary.getLateTenMin();
				late ++;
				lateSalary += salary.getLateTenMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "上午迟到" + time2.getMorningLateTime() + "分钟;");
			}
			if(time2.getMorningLateTime() > 10 && time2.getMorningLateTime() <= 30) {
				//迟到10分钟~30分钟
				feeDeduction += salary.getLateThanTenLessThirdTenMin();
				late ++;
				lateSalary += salary.getLateThanTenLessThirdTenMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "上午迟到" + time2.getMorningLateTime() + "分钟;");
			}
			
			
			/**
			 * 早上早退
			 */
			if(time2.getMorningLeaveTime() == 1) {
				leave += 1;
				leaveSalary += salary.getLeavetimeOneMin();
				feeDeduction += salary.getLeavetimeOneMin();
			}
			if(time2.getMorningLeaveTime() == 2) {
				leave += 2;
				leaveSalary += salary.getLeavetimeOneMin();
				feeDeduction += salary.getLeavetimeTwoMin();
			}
			
			
			//中午迟到的
			if(time2.getAfternoonLateTime() > 0 && time2.getAfternoonLateTime() <= 5) {
				//迟到0~5分钟
				feeDeduction += salary.getLateFiveMin();
				late ++;
				lateSalary += salary.getLateFiveMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "下午迟到" + time2.getAfternoonLateTime() + "分钟;");
			}
			if(time2.getAfternoonLateTime() > 5 && time2.getAfternoonLateTime() <= 10) {
				//迟到0~5分钟
				feeDeduction += salary.getLateTenMin();
				late ++;
				lateSalary += salary.getLateTenMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "下午迟到" + time2.getAfternoonLateTime() + "分钟;");
			}
			if(time2.getAfternoonLateTime() > 10 && time2.getAfternoonLateTime() <= 30) {
				//迟到10分钟~30分钟
				feeDeduction += salary.getLateThanTenLessThirdTenMin();
				late ++;
				lateSalary += salary.getLateThanTenLessThirdTenMin();
				sb.append(employeeName + "在日期为" + DateUtils.date2String(time2.getWorkDate(), "yyyy/MM/dd") + "下午迟到" + time2.getAfternoonLateTime() + "分钟;");
			}
			/**
			 * 下午早退
			 */
			if(time2.getAfternoonLeaveTime() == 1) {
				leave += 1;
				leaveSalary += salary.getLeavetimeOneMin();
				feeDeduction += salary.getLeavetimeOneMin();
			}
			if(time2.getAfternoonLeaveTime() == 2) {
				leave += 2;
				leaveSalary += salary.getLeavetimeTwoMin();
				feeDeduction += salary.getLeavetimeTwoMin();
			}
			//晚上迟到的
			if(time2.getNightLateTime() > 0 && time2.getNightLateTime() <= 5) {
				//迟到0~5分钟
				late ++;
				lateSalary += salary.getLateFiveMin();
				feeDeduction += salary.getLateFiveMin();
			}
			if(time2.getNightLateTime() > 5 && time2.getNightLateTime() <= 10) {
				//迟到0~5分钟
				late ++;
				lateSalary += salary.getLateTenMin();
				feeDeduction += salary.getLateTenMin();
			}
			if(time2.getNightLateTime() > 10 && time2.getNightLateTime() <= 30) {
				//迟到10分钟~30分钟
				late ++;
				lateSalary += salary.getLateThanTenLessThirdTenMin();
				feeDeduction += salary.getLateThanTenLessThirdTenMin();
			}
			
			if(StringUtils.isNotEmpty(employeeName) && getSalaryMap.get(employeeName) == null) {
				nameSet.add(employeeName);
				GetSalary getSalary = new GetSalary();
				getSalary.setCommonTime(time2.getWorkHours());
				getSalary.setDescription(sb.toString());
				getSalary.setLate(late);
				getSalary.setName(employeeName);
				getSalary.setLateSalary(lateSalary);
				getSalary.setLeaveEarly(leave);
				getSalary.setLeaveEarlySalary(leaveSalary);
				getSalary.setOverTime(time2.getOvertime());
				getSalary.setFeeDeduction(feeDeduction);
				getSalary.setSalary(time2.getSalary());
				getSalaryMap.put(employeeName, getSalary);
			}else {
//				已经存在map里面了
				getSalaryMap.get(employeeName).setCommonTime(getSalaryMap.get(employeeName).getCommonTime() + time2.getWorkHours());
				getSalaryMap.get(employeeName).setDescription(getSalaryMap.get(employeeName).getDescription() + sb.toString());
				getSalaryMap.get(employeeName).setLate(getSalaryMap.get(employeeName).getLate() + late);
				getSalaryMap.get(employeeName).setLateSalary(getSalaryMap.get(employeeName).getLateSalary() + lateSalary);
				getSalaryMap.get(employeeName).setLeaveEarly(getSalaryMap.get(employeeName).getLeaveEarly() + leave);
				getSalaryMap.get(employeeName).setLeaveEarlySalary(getSalaryMap.get(employeeName).getLeaveEarlySalary() + leaveSalary);
				getSalaryMap.get(employeeName).setOverTime(getSalaryMap.get(employeeName).getOverTime() + time2.getOvertime());
				getSalaryMap.get(employeeName).setFeeDeduction(getSalaryMap.get(employeeName).getFeeDeduction() + feeDeduction);
				getSalaryMap.get(employeeName).setSalary(getSalaryMap.get(employeeName).getSalary() + time2.getSalary());
			}
		}
		for (String string : nameSet) {
			getSalaryList.add(getSalaryMap.get(string));
		}
		String[] headers = {"员工姓名", "工号", "普通工时", "加班工时", "早退次数", "早退罚款", "迟到次数", "迟到罚款", "获得工资"};
		List<Object[]> datas = new ArrayList<Object[]>();
		for (GetSalary g : getSalaryList) {
			Object[] data = new Object[9];
			data[0] = g.getName();
			data[1] = g.getNum();
			data[2] = g.getCommonTime();
			data[3] = g.getOverTime();
			data[4] = g.getLeaveEarly();
			data[5] = g.getLeaveEarlySalary();
			data[6] = g.getLate();
			data[7] = g.getLateSalary();
//			data[8] = g.getFeeDeduction();
			data[8] = g.getSalary();
			datas.add(data);
		}
		ExcelUtils.excelExport("工资报表", headers, datas, response);
	}
}
