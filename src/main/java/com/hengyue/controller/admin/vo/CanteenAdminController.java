package com.hengyue.controller.admin.vo;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hengyue.entity.Log;
import com.hengyue.entity.vo.Canteen;
import com.hengyue.entity.vo.CanteenMonth;
import com.hengyue.entity.vo.Employee;
import com.hengyue.service.LogService;
import com.hengyue.service.vo.CanteenMonthService;
import com.hengyue.service.vo.CanteenService;
import com.hengyue.service.vo.EmployeeService;
import com.hengyue.utils.StringUtils;
/**
 * 餐厅就餐controller
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/canteen")
public class CanteenAdminController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private CanteenService canteenService;
	
	@Resource
	private CanteenMonthService canteenMonthService;
	
	@Resource
	private EmployeeService employeeService;
	
	
	
	@Resource
	private LogService logService;
	/**
	 * 分页查询餐厅就餐信息
	 * @param canteen
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = "食堂管理")
	public Map<String, Object> list(Canteen canteen,Integer canteenMonthId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(canteenMonthId != null) {
			CanteenMonth canteenMonth = new CanteenMonth();
			canteenMonth.setId(canteenMonthId);
			canteen.setCanteenMonth(canteenMonth);
		}
		List<Canteen> canteenList = canteenService.list(canteen, 1, 10000, Direction.ASC, "id");
		map.put("rows", canteenList);
		map.put("total", canteenList.size());
		logService.save(new Log(Log.SEARCH_ACTION, "查询餐厅就餐信息"));
		return map;
	}
	/**
	 * 添加或者修改餐厅就餐信息
	 * @param canteen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "食堂管理")
	public Map<String, Object> save(Canteen canteen) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(canteen.getId() == null) {
			logService.save(new Log(Log.ADD_ACTION, "添加餐厅就餐信息" + canteen));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新餐厅就餐信息" + canteen));
		}
		canteenService.save(canteen);
		map.put("success", true);
		return map;
	}
	/**
	 * 删除餐厅就餐id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "食堂管理")
	public Map<String, Object> delete(Integer id) throws Exception{
		CanteenMonth canteenMonth = canteenMonthService.findById(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String times = sdf.format(canteenMonth.getTimes());
		List<Canteen> canteenList = canteenService.selectByTimes(times);
		System.out.println(canteenList);
		for (Canteen canteen : canteenList) {
			canteenService.delete(canteen.getId());
		}
		logService.save(new Log(Log.DELETE_ACTION, "删除餐厅就餐信息" + canteenMonth));
		Map<String, Object> map = new HashMap<String, Object>();
		canteenMonthService.delete(id);
		map.put("success", true);
		return map;
	}
	/**
	 * 保存临时就餐
	 * @param id
	 * @param name
	 * @param money
	 * @return
	 */
	@RequestMapping("/saveTempCanteen")
	@RequiresPermissions(value = "食堂管理")
	public Map<String, Object> saveTempCanteen(Integer id, Integer times, String name, Double money){
		Map<String, Object> map = new HashMap<String, Object>();
		CanteenMonth canteenMonth = canteenMonthService.findById(id);
		canteenMonth.setTotalMoney(canteenMonth.getTotalMoney() + money);
		canteenMonth.setRemarks(canteenMonth.getRemarks() + ";   " + name + "   就餐" + times + "费用：" + money + "元");
		canteenMonthService.save(canteenMonth);
		map.put("success", true);
		return map;
	}
	/**
	 * 添加就餐就餐
	 * @param id
	 * @param name
	 * @param money
	 * @return
	 */
	@RequestMapping("/addCanteen")
	@RequiresPermissions(value = "食堂管理")
	public Map<String, Object> addCanteen(
			String times,
			String afternoon, 
			Double afternoonMoney, 
			String night, 
			Double nightMoney,
			String afternoonAndNight, 
			Double afternoonAndNightMoney, 
			String sandun, 
			Double sandunMoney){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			CanteenMonth canteenMonth ;
			
			canteenMonth = canteenMonthService.findByTimes(sdf.parse(times));
			if(canteenMonth == null) {
				canteenMonth = new CanteenMonth();
				canteenMonth.setTimes(sdf.parse(times));
			}
			canteenMonth.setAfternoon(canteenMonth.getAfternoon() + "," + afternoon);
			canteenMonth.setNight(canteenMonth.getNight() + "," + night);
			canteenMonth.setAfternoonAndNight(canteenMonth.getAfternoonAndNight() + "," + afternoonAndNight);
			canteenMonth.setSandun(canteenMonth.getSandun() + "," + sandun);
			
			String[] afternoonStringArgs = afternoon.split(",");
			String[] nightStringArgs = afternoon.split(",");
			String[] afternoonAndNightStringArgs = afternoon.split(",");
			String[] sandunStringArgs = afternoon.split(",");
			canteenMonth.setAfternoon(afternoon );
			canteenMonth.setAfternoonMoney(afternoonMoney);
			canteenMonth.setNight(night);
			canteenMonth.setNightMoney(nightMoney);
			canteenMonth.setAfternoonAndNight(afternoonAndNight);
			canteenMonth.setAfternoonAndNightMoney(afternoonAndNightMoney);
			canteenMonth.setSandun(sandun);
			canteenMonth.setRemarks(" ");
			canteenMonth.setSandunMoney(sandunMoney);
			
			canteenMonth.setTotalMoney(canteenMonth.getTotalMoney() + afternoonStringArgs.length * afternoonMoney + nightStringArgs.length * nightMoney + 
					afternoonAndNightStringArgs.length * afternoonAndNightMoney + sandunStringArgs.length * sandunMoney);
			canteenMonthService.save(canteenMonth);
			
			CanteenMonth canteenMonth2 = canteenMonthService.findByTimes(sdf.parse(times));
			//分割字符串
			
			for (String string : afternoonStringArgs) {
				Employee employee = employeeService.findById(Integer.parseInt(string));
				Canteen canteen = new Canteen();
				canteen.setCanteenMonth(canteenMonth2);
				canteen.setEmployeeId(Integer.parseInt(string));
				canteen.setEmployeeName(employee.getName());
				canteen.setType("中午");
				canteen.setMoney(afternoonMoney);
				canteen.setTimes(times);
				canteenService.save(canteen);
			}
			
			for (String string : nightStringArgs) {
				Employee employee = employeeService.findById(Integer.parseInt(string));
				Canteen canteen = new Canteen();
				canteen.setCanteenMonth(canteenMonth2);
				canteen.setEmployeeId(Integer.parseInt(string));
				canteen.setEmployeeName(employee.getName());
				canteen.setType("晚上");
				canteen.setMoney(nightMoney);
				canteen.setTimes(times);
				canteenService.save(canteen);
			}
			
			for (String string : afternoonAndNightStringArgs) {
				Employee employee = employeeService.findById(Integer.parseInt(string));
				Canteen canteen = new Canteen();
				canteen.setCanteenMonth(canteenMonth2);
				canteen.setEmployeeId(Integer.parseInt(string));
				canteen.setEmployeeName(employee.getName());
				canteen.setType("中午、晚上");
				canteen.setMoney(afternoonAndNightMoney);
				canteen.setTimes(times);
				canteenService.save(canteen);
			}
			
			for (String string : sandunStringArgs) {
				Employee employee = employeeService.findById(Integer.parseInt(string));
				Canteen canteen = new Canteen();
				canteen.setCanteenMonth(canteenMonth2);
				canteen.setEmployeeId(Integer.parseInt(string));
				canteen.setEmployeeName(employee.getName());
				canteen.setType("早上、中午、晚上");
				canteen.setMoney(sandunMoney);
				canteen.setTimes(times);
				canteenService.save(canteen);
			}
			//保存总金额。
			
			map.put("success", true);
			return map;
		} catch (Exception e) {
			map.put("errorInfo", e.getMessage());
			map.put("success", false);
			return map;
		}
	}
	
	/**
	 *  保存食堂就餐信息
	 * @param times 次数
	 * @param afternoon  中午吃饭的人
	 * @param afternoonMoney 中午吃饭的人的钱
	 * @param night  晚上吃饭的人
	 * @param nightMoney 晚上吃饭的人的钱
	 * @param afternoonAndNight  吃中午和晚上的人
	 * @param afternoonAndNightMoney 吃中午和晚上的人的钱
	 * @param sandun  吃三顿的人
	 * @param sandunMoney  吃三顿的人的钱
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveCanteen")
	@RequiresPermissions(value = "食堂管理")
	public Map<String, Object> saveCanteen(
			String month,
			String year,
			String times,
			String afternoon, 
			Double afternoonMoney, 
			String night, 
			Double nightMoney,
			String afternoonAndNight, 
			Double afternoonAndNightMoney, 
			String sandun, 
			Double sandunMoney
			) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			times = year + month;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			CanteenMonth canteenMonth ;
			if(afternoonMoney == null) {
				afternoonMoney = 0.0;
			}
			if(nightMoney == null) {
				nightMoney = 0.0;
			}
			if(afternoonAndNightMoney == null) {
				afternoonAndNightMoney = 0.0;
			}
			if(sandunMoney == null) {
				sandunMoney = 0.0;
			}
			canteenMonth = canteenMonthService.findByTimes(sdf.parse(times));
			if(canteenMonth == null) {
				canteenMonth = new CanteenMonth();
				canteenMonth.setTimes(sdf.parse(times));
			}else {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				String times1 = sdf1.format(canteenMonth.getTimes());
				List<Canteen> canteenList = canteenService.selectByTimes(times1);
				System.out.println(canteenList);
				for (Canteen canteen : canteenList) {
					canteenService.delete(canteen.getId());
				}
			}
			canteenMonth.setAfternoon(afternoon );
			canteenMonth.setAfternoonMoney(afternoonMoney);
			canteenMonth.setNight(night);
			canteenMonth.setNightMoney(nightMoney);
			canteenMonth.setAfternoonAndNight(afternoonAndNight);
			canteenMonth.setAfternoonAndNightMoney(afternoonAndNightMoney);
			canteenMonth.setSandun(sandun);
			canteenMonth.setSandunMoney(sandunMoney);
			canteenMonth.setMonth(month);
			canteenMonth.setYear(year);
			String[] afternoonStringArgs = new String[0];
			String[] nightStringArgs = new String[0];
			String[] afternoonAndNightStringArgs = new String[0];
			String[] sandunStringArgs =  new String[0];
			
			if(StringUtils.isNotEmpty(afternoon)) {
				afternoonStringArgs = afternoon.split(",");
			}
			if(StringUtils.isNotEmpty(night)) {
				nightStringArgs = night.split(",");
			}
			if(StringUtils.isNotEmpty(afternoonAndNight)) {
				afternoonAndNightStringArgs = afternoonAndNight.split(",");
			}
			if(StringUtils.isNotEmpty(sandun)) {
				sandunStringArgs = sandun.split(",");
			}
			logger.info(afternoonStringArgs.length + ":afternoonStringArgs");
			logger.info(nightStringArgs.length + ":nightStringArgs");
			logger.info(afternoonAndNightStringArgs.length + ":afternoonAndNightStringArgs");
			logger.info(sandunStringArgs.length + ":sandunStringArgs");
			canteenMonth.setTotalMoney(
					  afternoonStringArgs.length * afternoonMoney 
					+ nightStringArgs.length * nightMoney 
					+ afternoonAndNightStringArgs.length * afternoonAndNightMoney 
					+ sandunStringArgs.length * sandunMoney);
			canteenMonthService.save(canteenMonth);
			
			CanteenMonth canteenMonth2 = canteenMonthService.findByTimes(sdf.parse(times));
			//分割字符串
			
			for (String string : afternoonStringArgs) {
				Employee employee = employeeService.findById(Integer.parseInt(string));
				Canteen canteen = new Canteen();
				canteen.setCanteenMonth(canteenMonth2);
				canteen.setEmployeeId(Integer.parseInt(string));
				canteen.setEmployeeName(employee.getName());
				canteen.setType("中午");
				canteen.setMoney(afternoonMoney);
				canteen.setTimes(times);
				canteenService.save(canteen);
			}
			
			for (String string : nightStringArgs) {
				Employee employee = employeeService.findById(Integer.parseInt(string));
				Canteen canteen = new Canteen();
				canteen.setCanteenMonth(canteenMonth2);
				canteen.setEmployeeId(Integer.parseInt(string));
				canteen.setEmployeeName(employee.getName());
				canteen.setType("晚上");
				canteen.setMoney(nightMoney);
				canteen.setTimes(times);
				canteenService.save(canteen);
			}
			
			for (String string : afternoonAndNightStringArgs) {
				Employee employee = employeeService.findById(Integer.parseInt(string));
				Canteen canteen = new Canteen();
				canteen.setCanteenMonth(canteenMonth2);
				canteen.setEmployeeId(Integer.parseInt(string));
				canteen.setEmployeeName(employee.getName());
				canteen.setType("中午、晚上");
				canteen.setMoney(afternoonAndNightMoney);
				canteen.setTimes(times);
				canteenService.save(canteen);
			}
			for (String string : sandunStringArgs) {
				Employee employee = employeeService.findById(Integer.parseInt(string));
				Canteen canteen = new Canteen();
				canteen.setCanteenMonth(canteenMonth2);
				canteen.setEmployeeId(Integer.parseInt(string));
				canteen.setEmployeeName(employee.getName());
				canteen.setType("早上、中午、晚上");
				canteen.setMoney(sandunMoney);
				canteen.setTimes(times);
				canteenService.save(canteen);
			}
			//保存总金额。
			map.put("success", true);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", e.getMessage());
			map.put("success", false);
			return map;
		}
		
		
	}
}
