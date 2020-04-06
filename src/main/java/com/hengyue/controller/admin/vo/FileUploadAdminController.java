package com.hengyue.controller.admin.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hengyue.entity.Log;
import com.hengyue.entity.vo.Employee;
import com.hengyue.entity.vo.FileUpload;
import com.hengyue.entity.vo.Position;
import com.hengyue.entity.vo.Salary;
import com.hengyue.entity.vo.Time;
import com.hengyue.service.LogService;
import com.hengyue.service.vo.EmployeeService;
import com.hengyue.service.vo.FileUploadService;
import com.hengyue.service.vo.PositionService;
import com.hengyue.service.vo.SalaryService;
import com.hengyue.service.vo.TimeService;
import com.hengyue.utils.DateUtils;
/**
 * 文件管理controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/fileUpload")
public class FileUploadAdminController {

	@Resource
	private FileUploadService fileUploadService;
	
	@Resource
	private LogService logService;
	
	@Resource
	private EmployeeService employeeService;
	
	@Resource
	private TimeService timeService;
	
	@Resource
	private SalaryService salaryService;
	
	@Resource
	private PositionService positionService;
	
	private static String dateFormat1 = "yyyy/MM/dd hh:mm";
	private static String dateFormat2 = "yyyy-MM-dd hh:mm";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/**
	 * 上传文件
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/upload")
	@RequiresPermissions(value = "数据导入")
	public Map<String, Object> upload(
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		FileInputStream fis = null;
		Workbook workBook = null;
		try {
			if(file != null) {
				String formatname = null;
				String url = null;
				String type = null;
				int len = file.getOriginalFilename().split("\\.").length;
				System.out.println("getOriginalFilename:" + file.getOriginalFilename());
				if( len >= 2) {
					formatname = DateUtils.getCurrentTime()  + "." +  file.getOriginalFilename().split("\\.")[1];
					url = request.getServletContext().getRealPath("/") + "//file//" + DateUtils.getCurrentTime() + "." + file.getOriginalFilename().split("\\.")[1];
					type = file.getOriginalFilename().split("\\.")[1];
				}else {
					url = request.getServletContext().getRealPath("/") + "//file//" + DateUtils.getCurrentTime() + "." + file.getOriginalFilename().split("\\.")[0];
					formatname = DateUtils.getCurrentTime()  + "." +  file.getOriginalFilename().split("\\.")[0];
					type = file.getOriginalFilename().split("\\.")[0];
				}
				/**
				 * 在部署的时候需要加上
				 */
				url = "/opt/data/upload_tmp/" + formatname;
				String size = file.getSize() / 1024 + "kb";
				String name = file.getOriginalFilename();
				System.out.println("url:" + url);
				FileUpload uploadFile = new FileUpload();
				uploadFile.setTime(new Date());
				uploadFile.setFormatname(formatname);
				uploadFile.setName(name);
				uploadFile.setSize(size);
				uploadFile.setUrl(url);
				uploadFile.setType(type);
				file.transferTo(new File(url));
				fis = new FileInputStream(url);
				if ("xlsx".equals(type)) {
					workBook = new XSSFWorkbook(fis); // 使用XSSFWorkbook
				} else if ("xls".equals(type)) {
					workBook = new HSSFWorkbook(fis, true); // 使用HSSFWorkbook 构造函数略有不同 true表示转化成为Nodes
				}
//				dealWorkBook(workBook);
				Sheet sheet = workBook.getSheetAt(0); // 获取第一个sheet
				List<String> firstRow = new ArrayList<String>();
				int i = 0;
				for (Row row : sheet) {
					int j = 0;
					if (i == 0) {
						for (Cell cell : row) { // 遍历当前行的所有cell
							cell.setCellType(CellType.STRING);
							firstRow.add(cell.getRichStringCellValue().getString());
						}
					}
					if (i != 0) {
						String date = "";
						
						long morning_begin_begin = 0;						//早上上班打卡时间范围
						long morning_begin_end = 0;							//早上上班打卡时间范围
						long morning_end_begin = 0;							//早上下班打卡时间范围
						long morning_end_end = 0;							//早上下班打卡时间范围
						
						long afternoon_begin_begin = 0;						//中午上班打卡时间范围
						long afternoon_begin_end = 0;
						long afternoon_end_begin = 0;						//下午下班打卡时间范围
						long afternoon_end_end = 0;
						
						long night_begin_begin = 0;							//晚上上班打卡时间范围
						long night_begin_end = 0;
						long night_end_begin = 0;
						long night_end_end = 0;								//晚上下班打卡时间范围
						
						long night_work = 0;								//晚上晚退的上班时间
						
						long night_begin_1 = 0;								//晚上迟到早退
						long night_begin_2 = 0;								//晚上迟到早退
						
						boolean morningNormalWorkBegin = false;				//早上正常工作
						boolean morningNormalWorkEnd = false;				//早上正常工作
						boolean afternoonNormalWorkBegin = false;			//中午正常工作
						boolean afternoonNormalWorkEnd = false;				//中午正常工作
						boolean nightNormalWorkBegin = false;				//晚上正常工作
						boolean nightNormalWorkEnd = false;					//晚上正常工作
						
						boolean nightWorkMore = false;					    //晚上晚退
						
						String employeeName = "";
						String department = "";
						String employeeNum = "";
						for (int index = 0; index < firstRow.size(); index++) {
							//查找员工
							Position position = null;
							Salary salary = null;
							Employee employee = null;
							Time time1 = null;
							long morningLate = 0;
							long afternoonLate = 0;
							long nightLate = 0;
							
							Cell cell = row.getCell(index);
							if (cell == null) {
								j++;
							} else {
								switch (firstRow.get(j)) {
								case "考勤号码":
									employeeNum = cell.getRichStringCellValue().getString().trim();
									break;
								case "姓名":
									employeeName = cell.getRichStringCellValue().getString().trim();
									break;
								case "部门":
									department = cell.getRichStringCellValue().getString().trim();
									break;
								case "日期":
									try {
										morning_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 7:30", dateFormat1).getTime();
										morning_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 8:00", dateFormat1).getTime();
										
										morning_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 12:00", dateFormat1).getTime();
										morning_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 12:30", "yyyy/MM/dd hh:mm").getTime();
										
										afternoon_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 13:00", dateFormat1).getTime();
										afternoon_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 13:30", dateFormat1).getTime();
										
										afternoon_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 17:30", dateFormat1).getTime();
										afternoon_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 18:00", dateFormat1).getTime();
										
										night_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 18:00", dateFormat1).getTime();
										night_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 18:30", dateFormat1).getTime();
										
										night_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 21:30",dateFormat1).getTime();
										night_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 22:00", dateFormat1).getTime();
									}catch(Exception e) {
										morning_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 7:30", dateFormat2).getTime();
										morning_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 8:00", dateFormat2).getTime();
										
										morning_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 12:00", dateFormat2).getTime();
										morning_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 12:30", dateFormat2).getTime();
										
										afternoon_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 13:00", dateFormat2).getTime();
										afternoon_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 13:30", dateFormat2).getTime();
										
										afternoon_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 17:30", dateFormat2).getTime();
										afternoon_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 18:00", dateFormat2).getTime();
										
										night_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 18:00", dateFormat2).getTime();
										night_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 18:30", dateFormat2).getTime();
										
										night_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 21:30",dateFormat2).getTime();
										night_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString().trim() + " 22:00", dateFormat2).getTime();
									}
									date = cell.getRichStringCellValue().getString().trim();
									break;
								case "时间":
									/**
									 * 判断早上迟到的分钟数
									 */
									double morningLateToMin = 0;
									/**
									 * 判断下午迟到的分钟数
									 */
									double afternoonLateToMin = 0;
									/**
									 * 判断晚上迟到的分钟数
									 */
									double nightLateToMin = 0;
									Date date1 = null;
									try {
										date1 = DateUtils.string2Date(date, "yyyy-MM-dd");
									}catch(Exception e) {
										date1 = DateUtils.string2Date(date, "yyyy/MM/dd");
									}
//									employee = employeeService.findByName(employeeName);
									employee = employeeService.findByNameAndNum(employeeName, employeeNum);
									//查找所以在的这位，以及部门
									if(employee != null) {
										position = positionService.findByName(employee.getPosition());
									}else {
										employee = new Employee();
										employee.setName(employeeName);
										employee.setEmployeeNum(employeeNum);
										employeeService.save(employee);
									}
									//如果查询得到，那么就算，查询不到的话就无法计算
									if(position != null) {
										salary = position.getSalary();
										if(salary == null) {
											map.put("success", false);
											map.put("errorInfo", "职位：" + position.getName() + "        没有设置好职位工资模板，所以不能计算工资，请设置好之后再计算！");
											return map;
										}
									}else {
										map.put("success", false);
										map.put("errorInfo", "您导入的文档中有员工：" + employeeName + "没有设置好职位信息，请设置以后再导入计算工资");
										return map;
									}
									time1 = timeService.findByEmployeeNameAndDate(employeeName, date1);
									if(time1 == null) {
										time1 = new Time();
									}
									
									String[] times = cell.getRichStringCellValue().getString().split(" ");
//									boolean flag = true;
									if (times.length > 1) {
										// 正常上下班
										for (String string : times) {
											long time = 0;
											try {
												time = DateUtils.string2Date(date + " " + string,"yyyy/MM/dd hh:mm").getTime();
											}catch(Exception e) {
												time = DateUtils.string2Date(date + " " + string,"yyyy-MM-dd hh:mm").getTime();
											}
											if(time >= morning_begin_begin && time <= morning_begin_end) {
												//早上上班正常
												morningNormalWorkBegin = true;
											}
											if(time >= morning_end_begin && time <= morning_end_end) {
												//早上下班正常
												morningNormalWorkEnd = true;
											}
											if(time > morning_begin_end && time < (morning_end_begin + 46800000)) {
												//早上上班迟到或者早退
												morningLate = time - morning_begin_end;
											}
											//下午正常上班
											if(time >= afternoon_begin_begin && time <= afternoon_begin_end) {
												afternoonNormalWorkBegin = true;
											}
											//下午正常下班
											if(time >= afternoon_end_begin && time <= afternoon_end_end) {
												afternoonNormalWorkEnd = true;
											}
											//下午迟到或者早退
											if(time > afternoon_begin_end && time < afternoon_end_begin) {
												//早上上班迟到或者早退
												afternoonLate = time - afternoon_begin_end;
											}
											//晚上正常上班
											if(time >= night_begin_begin && time <= night_begin_end) {
												nightNormalWorkBegin = true;
											}
											//晚上正常下班
											if(time >= night_end_begin && time <= night_end_end) {
												nightNormalWorkEnd = true;
											}
											//晚上迟到或者早退
											if(time > night_begin_end && time < night_end_begin) {
												//早上上班迟到或者早退
												nightLate = time - night_begin_end;
											}
											//晚上晚退  (算出上班时间)
											if(time > night_end_end) {
												night_work = time - night_begin_end;
												nightWorkMore = true;
											}
											//晚上迟到早退
											if(time > night_begin_end && time < night_end_begin) {
												night_begin_1 = time;
											}
											if(time > night_begin_1 && night_begin_1 != 0 && time < night_end_begin) {
												night_begin_2 = time;
											}
										}
										StringBuffer sw = new StringBuffer();
										StringBuffer xw = new StringBuffer();
										StringBuffer ws = new StringBuffer();
										if (morningNormalWorkBegin && morningNormalWorkEnd) {
											sw.append("上午上班4小时");
											time1.setMorningWorkTime(4);
										}
										if(morningNormalWorkBegin && !morningNormalWorkEnd) {
											sw.append("上午早退，上班时间为：" + (double)morningLate/60000 + "分钟");
											int morningWorkTime = (int) morningLate/3600000;
											if((double)morningLate/60000 == 1.0) {
												time1.setMorningLeaveTime(1);
												time1.setMorningWorkTime(4);
											}
											if((double)morningLate/60000 == 2.0) {
												time1.setMorningLeaveTime(2);
												time1.setMorningWorkTime(4);
											}
											if((double)morningLate/60000 > 60.0) {
												time1.setMorningWorkTime(morningWorkTime);
											}
										}
										if(!morningNormalWorkBegin && morningNormalWorkEnd) {
											sw.append("上午迟到：" + (double)morningLate/60000 + "分钟");
											morningLateToMin = morningLate/60000;
											time1.setMorningWorkTime(4);
											time1.setMorningLateTime((int) ((double)morningLate/60000));
										}
										if (afternoonNormalWorkBegin && afternoonNormalWorkEnd) {
											xw.append("下午上班4小时");
											time1.setAfternoonWorkTime(4);
										}
										if (afternoonNormalWorkBegin && !afternoonNormalWorkEnd) {
											xw.append("下午早退，上班时间为：" + (double)afternoonLate/60000 + "分钟");
											int afternoonWorkTime = (int) afternoonLate/3600000;
											if((double)afternoonLate/60000 == 1.0) {
												time1.setAfternoonLeaveTime(1);
												time1.setAfternoonWorkTime(4);
											}
											if((double)afternoonLate/60000 == 2.0) {
												time1.setAfternoonLeaveTime(2);
												time1.setAfternoonWorkTime(4);
											}
											if((double)afternoonLate/60000 > 60.0) {
												time1.setAfternoonWorkTime(afternoonWorkTime);
											}
										}
										if (!afternoonNormalWorkBegin && afternoonNormalWorkEnd) {
											xw.append("下午迟到：" + (double)afternoonLate/60000 + "分钟");
											afternoonLateToMin = afternoonLate/60000;
											time1.setAfternoonLateTime((int) ((double)afternoonLate/60000));
											time1.setAfternoonWorkTime(4);
										}
										if (nightNormalWorkBegin && nightNormalWorkEnd) {
											ws.append("晚上加班三个小时");
											time1.setNightWorkTime(3);
										}
										if (nightNormalWorkBegin && !nightNormalWorkEnd) {
											ws.append("晚上早退，上班时间为：" + (double)nightLate/60000 + "分钟");
//											time1.setNightWorkTime((int) ((double)nightLate/3600000));
											/**
											 * 晚上早退时间
											 */
											double nightWorkTimeTmp = (double) nightLate/3600000;
											NumberFormat nf = NumberFormat.getNumberInstance();
											// 保留两位小数
											nf.setMaximumFractionDigits(1);
											// 如果不需要四舍五入，可以使用RoundingMode.DOWN
											nf.setRoundingMode(RoundingMode.DOWN);
											String tmpDate = nf.format(nightWorkTimeTmp);
											double nightWorkTime = (double) nf.parse(tmpDate);
											/**
											 * 晚上迟到两分钟或者一分钟
											 */
											if((double)nightLate/60000 == 1.0) {
												time1.setNightLeaveTime(1);
												time1.setNightWorkTime(3);
											}
											if((double)nightLate/60000 == 2.0) {
												time1.setNightLeaveTime(2);
												time1.setNightWorkTime(3);
											}
											if((double)nightLate/60000 > 60.0) {
												time1.setNightWorkTime(nightWorkTime);
											}
										}
										if (!nightNormalWorkBegin && nightNormalWorkEnd) {
											time1.setNightWorkTime(3);
											nightLate = nightLate/60000;
											ws.append("晚上迟到，迟到：" + (double)nightLate/60000 + "分钟");
											time1.setNightLateTime((int) ((double)nightLate/60000));
										}
										//晚上加班之后没有正常下班，继续加班
										if (nightWorkMore) {
											if(nightNormalWorkBegin) {
												//晚上正常上班
												ws.append("晚上加班之后再加班，加班时间：" + (double)night_work/3600000 + "分钟");
												NumberFormat nf = NumberFormat.getNumberInstance();
												// 保留两位小数
												nf.setMaximumFractionDigits(1);
												// 如果不需要四舍五入，可以使用RoundingMode.DOWN
												nf.setRoundingMode(RoundingMode.DOWN);
												String tmpDate = nf.format((double)night_work/3600000);
												time1.setNightWorkTime((double)nf.parse(tmpDate));
											}else {
												//晚上迟到上班
												
											}
											
										}
										if(DateUtils.isWeekend(date1)) {
											time1.setWorkTime(time1.getMorningWorkTime() + time1.getAfternoonWorkTime());
											time1.setWorkHours(time1.getMorningWorkTime() + time1.getAfternoonWorkTime());
											time1.setOvertime(time1.getNightWorkTime());
										}else {
											time1.setOvertime(time1.getMorningWorkTime() + time1.getAfternoonWorkTime() + time1.getNightWorkTime());
										}
										if(salary != null) {
											/**
											 * 说明有工作
											 * 可以算工资
											 */
											double todaySalary = time1.getWorkTime() * salary.getHourSalary();
											if(morningLateToMin > 0) {
												if(morningLateToMin <= 5) {
													todaySalary = todaySalary - salary.getLateFiveMin();
												}
												if(morningLateToMin > 5 && morningLateToMin <= 10) {
													todaySalary = todaySalary - salary.getLateTenMin();
												}
												if(morningLateToMin > 10 && morningLateToMin <= 30) {
													todaySalary = todaySalary - salary.getLateThanTenLessThirdTenMin();
												}
												if(morningLateToMin > 30) {
													todaySalary = todaySalary - salary.getLateThanThirdTenMin();
												}
											}
											if(afternoonLateToMin > 0) {
												if(afternoonLateToMin <= 5) {
													todaySalary = todaySalary - salary.getLateFiveMin();
												}
												if(afternoonLateToMin > 5 && afternoonLateToMin <= 10) {
													todaySalary = todaySalary - salary.getLateTenMin();
												}
												if(afternoonLateToMin > 10 && afternoonLateToMin <= 30) {
													todaySalary = todaySalary - salary.getLateThanTenLessThirdTenMin();
												}
												if(afternoonLateToMin > 30) {
													todaySalary = todaySalary - salary.getLateThanThirdTenMin();
												}
											}
											if(nightLateToMin > 0) {
												if(nightLateToMin <= 5) {
													todaySalary = todaySalary - salary.getLateFiveMin();
												}
												if(nightLateToMin > 5 && nightLateToMin <= 10) {
													todaySalary = todaySalary - salary.getLateTenMin();
												}
												if(nightLateToMin > 10 && nightLateToMin <= 30) {
													todaySalary = todaySalary - salary.getLateThanTenLessThirdTenMin();
												}
												if(nightLateToMin > 30) {
													todaySalary = todaySalary - salary.getLateThanThirdTenMin();
												}
											}
											
											if(time1.getMorningLeaveTime() == 1) {
												todaySalary = todaySalary - salary.getLeavetimeOneMin();
											}
											if(time1.getMorningLeaveTime() == 2) {
												todaySalary = todaySalary - salary.getLeavetimeTwoMin();
											}
											if(time1.getAfternoonLeaveTime() == 1) {
												todaySalary = todaySalary - salary.getLeavetimeOneMin();
											}
											if(time1.getAfternoonLeaveTime() == 2) {
												todaySalary = todaySalary - salary.getLeavetimeTwoMin();
											}
											if(time1.getNightLeaveTime() == 1) {
												todaySalary = todaySalary - salary.getLeavetimeOneMin();
											}
											if(time1.getNightLeaveTime() == 2) {
												todaySalary = todaySalary - salary.getLeavetimeTwoMin();
											}
											todaySalary += time1.getOvertime() * salary.getOvertime();
											time1.setSalary(todaySalary);
											time1.setWorkDate(date1);
											time1.setEmployeeName(employeeName);
											time1.setEmployeeId(employee.getId());
											timeService.save(time1);
										}
										System.out.println(employeeName + "----" + department + ":" + date + "----" + sw + "----" + xw  + "----" + ws);
									}
									break;
								}
								j++;
							}
						}
					}
					i++;
				}
				map.put("success", true);
				fileUploadService.save(uploadFile);
				logService.save(new Log(Log.ADD_ACTION, "上传文件" + uploadFile));
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("errorInfo", "上传失败，请稍后重试！");
			return map;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		map.put("success", true);
		return map;
	}
	/**
	 * 分页查询文件管理信息
	 * @param fileUpload
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = "数据导入")
	public Map<String, Object> list(FileUpload fileUpload, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileUpload> fileUploadList = fileUploadService.list(fileUpload, page, rows, Direction.ASC, "id");
		Long total = fileUploadService.getCount(fileUpload);
		map.put("rows", fileUploadList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询文件管理信息"));
		return map;
	}
	/**
	 * 添加或者修改文件管理信息
	 * @param fileUpload
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = "数据导入")
	public Map<String, Object> save(FileUpload fileUpload) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(fileUpload.getId() == null) {
			logService.save(new Log(Log.ADD_ACTION, "添加文件管理信息" + fileUpload));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新文件管理信息" + fileUpload));
		}
		fileUploadService.save(fileUpload);
		map.put("success", true);
		return map;
	}
	/**
	 * 删除文件管理id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value = "数据导入")
	public Map<String, Object> delete(Integer id) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除文件管理信息" + fileUploadService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		fileUploadService.delete(id);
		map.put("success", true);
		return map;
	}
	
	
	/**
	 * 
	 * @param workBook
	 */
	private static void dealWorkBook(Workbook workBook) {
		Sheet sheet = workBook.getSheetAt(0); // 获取第一个sheet
		try {
			List<String> firstRow = new ArrayList<String>();
			int i = 0;
			for (Row row : sheet) {
				int j = 0;
				if (i == 0) {
					for (Cell cell : row) { // 遍历当前行的所有cell
						cell.setCellType(CellType.STRING);
						firstRow.add(cell.getRichStringCellValue().getString());
					}
				}
				if (i != 0) {
					String date = "";
					
					long morning_begin_begin = 0;						//早上上班打卡时间范围
					long morning_begin_end = 0;							//早上上班打卡时间范围
					long morning_end_begin = 0;							//早上下班打卡时间范围
					long morning_end_end = 0;							//早上下班打卡时间范围
					
					long afternoon_begin_begin = 0;						//中午上班打卡时间范围
					long afternoon_begin_end = 0;
					long afternoon_end_begin = 0;						//下午下班打卡时间范围
					long afternoon_end_end = 0;
					
					long night_begin_begin = 0;							//晚上上班打卡时间范围
					long night_begin_end = 0;
					long night_end_begin = 0;
					long night_end_end = 0;								//晚上下班打卡时间范围
					
					boolean morningNormalWorkBegin = false;				//早上正常工作
					boolean morningNormalWorkEnd = false;				//早上正常工作
					boolean afternoonNormalWorkBegin = false;			//中午正常工作
					boolean afternoonNormalWorkEnd = false;				//中午正常工作
					boolean nightNormalWorkBegin = false;				//晚上正常工作
					boolean nightNormalWorkEnd = false;					//晚上正常工作
					
					String name = "";
					String department = "";
					
					for (int index = 0; index < firstRow.size(); index++) {
						//查找员工
//						Employee employee = employeeService.
						//查找所以在的这位，以及部门
						
						//如果查询得到，那么就算，查询不到的话就无法计算
						
						long morningLate = 0;
						long afternoonLate = 0;
						long nightLate = 0;
						
						Cell cell = row.getCell(index);
						if (cell == null) {
							j++;
						} else {
							switch (firstRow.get(j)) {
							case "姓名":
								name = cell.getRichStringCellValue().getString();
								
								break;
							case "部门":
								department = cell.getRichStringCellValue().getString();
								break;
							case "日期":
								try {
									morning_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 7:30", dateFormat1).getTime();
									morning_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 8:00", dateFormat1).getTime();
									
									morning_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 12:00", dateFormat1).getTime();
									morning_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 12:30", "yyyy/MM/dd hh:mm").getTime();
									
									afternoon_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 13:00", dateFormat1).getTime();
									afternoon_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 13:30", dateFormat1).getTime();
									
									afternoon_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 17:30", dateFormat1).getTime();
									afternoon_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 18:00", dateFormat1).getTime();
									
									night_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 18:00", dateFormat1).getTime();
									night_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 18:30", dateFormat1).getTime();
									
									night_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 21:30",dateFormat1).getTime();
									night_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 22:00", dateFormat1).getTime();
								}catch(Exception e) {
									morning_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 7:30", dateFormat2).getTime();
									morning_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 8:00", dateFormat2).getTime();
									
									morning_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 12:00", dateFormat2).getTime();
									morning_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 12:30", dateFormat2).getTime();
									
									afternoon_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 13:00", dateFormat2).getTime();
									afternoon_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 13:30", dateFormat2).getTime();
									
									afternoon_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 17:30", dateFormat2).getTime();
									afternoon_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 18:00", dateFormat2).getTime();
									
									night_begin_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 18:00", dateFormat2).getTime();
									night_begin_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 18:30", dateFormat2).getTime();
									
									night_end_begin = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 21:30",dateFormat2).getTime();
									night_end_end = DateUtils.string2Date(cell.getRichStringCellValue().getString() + " 22:00", dateFormat2).getTime();
								}
								date = cell.getRichStringCellValue().getString();
								break;
							case "时间":
								String[] times = cell.getRichStringCellValue().getString().split(" ");
//								boolean flag = true;
								if (times.length > 1) {
									// 正常上下班
									for (String string : times) {
										long time = 0;
										try {
											time = DateUtils.string2Date(date + " " + string,"yyyy/MM/dd hh:mm").getTime();
										}catch(Exception e) {
											time = DateUtils.string2Date(date + " " + string,"yyyy-MM-dd hh:mm").getTime();
										}
										if(time >= morning_begin_begin && time <= morning_begin_end) {
											//早上上班正常
											morningNormalWorkBegin = true;
										}
										if(time >= morning_end_begin && time <= morning_end_end) {
											//早上下班正常
											morningNormalWorkEnd = true;
										}
										if(time > morning_begin_end && time < (morning_end_begin + 46800000)) {
											//早上上班迟到或者早退
											morningLate = time - morning_begin_end;
										}
										
										//下午正常上班
										if(time >= afternoon_begin_begin && time <= afternoon_begin_end) {
											afternoonNormalWorkBegin = true;
										}
										
										//下午正常下班
										if(time >= afternoon_end_begin && time <= afternoon_end_end) {
											afternoonNormalWorkEnd = true;
										}
										
										//下午迟到或者早退
										if(time > afternoon_begin_end && time < afternoon_end_begin) {
											//早上上班迟到或者早退
											afternoonLate = time - afternoon_begin_end;
										}
										
										//晚上正常上班
										if(time >= night_begin_begin && time <= night_begin_end) {
											nightNormalWorkBegin = true;
										}
										//晚上正常下班
										if(time >= night_end_begin && time <= night_end_end) {
											nightNormalWorkEnd = true;
										}
										
										//晚上迟到或者早退
										if(time > night_begin_end && time < night_end_begin) {
											//早上上班迟到或者早退
											nightLate = time - night_begin_end;
										}
									}
									StringBuffer sw = new StringBuffer();
									StringBuffer xw = new StringBuffer();
									StringBuffer ws = new StringBuffer();
									if (morningNormalWorkBegin && morningNormalWorkEnd) {
										sw.append("上午上班4小时");
									}
									if(morningNormalWorkBegin && !morningNormalWorkEnd) {
										sw.append("上午早退，上班时间为：" + (double)morningLate/60000 + "分钟");
									}
									if(!morningNormalWorkBegin && morningNormalWorkEnd) {
										sw.append("上午迟到：" + (double)morningLate/60000 + "分钟");
									}
									if (afternoonNormalWorkBegin && afternoonNormalWorkEnd) {
										xw.append("下午上班4小时");
									}
									if (afternoonNormalWorkBegin && !afternoonNormalWorkEnd) {
										xw.append("下午早退，上班时间为：" + (double)afternoonLate/60000 + "分钟");
									}
									if (!afternoonNormalWorkBegin && afternoonNormalWorkEnd) {
										xw.append("下午迟到：" + (double)afternoonLate/60000 + "分钟");
									}
									if (nightNormalWorkBegin && nightNormalWorkEnd) {
										ws.append("晚上加班两个小时");
									}
									if (nightNormalWorkBegin && !nightNormalWorkEnd) {
										ws.append("晚上早退，上班时间为：" + (double)nightLate/60000 + "分钟");
									}
									if (!nightNormalWorkBegin && nightNormalWorkEnd) {
										ws.append("晚上迟到，迟到：" + (double)nightLate/60000 + "分钟");
									}
									System.out.println(name + "----" + department + ":" + date + "----" + sw + "----" + xw  + "----" + ws);
								}
								break;
							}
							j++;
						}
					}
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
