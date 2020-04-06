package com.hengyue.entity.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengyue.entity.CustomDateTimeSerializer;
/**
 * 上班时间实体类
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_time")
public class Time {

	@Id
	@GeneratedValue
	private Integer id;				//编号
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date workDate;				//工作日期
	
	private double late;			//迟到时间
	
	private double leavetime;			//早退
	
	private double workTime;		//工作时间
	
	private double morningWorkTime;	//早上工作时间
	
	private int morningLateTime;	//早上迟到时间
	
	private int morningLeaveTime;	//早上早退时间
	
	private double afternoonWorkTime;	//下午工作时间
	
	private int afternoonLateTime;	//下午迟到时间
	
	private int afternoonLeaveTime;	//下午早退时间
	
	private double nightWorkTime;		//晚上工作时间
	
	private int nightLateTime;		//晚上迟到时间
	
	private int nightLeaveTime;		//晚上早退时间
	
	private double salary;			//一天工资
	
	private Integer employeeId;		//员工id号
	
	private String employeeName;	//员工姓名
	
	private double workHours;		//员工的工作小时数
	
	private double fine;			//其他扣费
	
	private double award;			//其他奖励
	
	private double overtime;		//加班时间
	
	@Transient
	private Date bTime;				//开始时间
	
	@Transient
	private Date eTime;				//结束时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public double getLate() {
		return late;
	}

	public void setLate(double late) {
		this.late = late;
	}

	public double getLeavetime() {
		return leavetime;
	}

	public void setLeavetime(double leavetime) {
		this.leavetime = leavetime;
	}

	public double getWorkTime() {
		return workTime;
	}

	public void setWorkTime(double workTime) {
		this.workTime = workTime;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public double getWorkHours() {
		return workHours;
	}

	public void setWorkHours(double workHours) {
		this.workHours = workHours;
	}

	
	public double getMorningWorkTime() {
		return morningWorkTime;
	}

	public void setMorningWorkTime(double morningWorkTime) {
		this.morningWorkTime = morningWorkTime;
	}

	public int getMorningLateTime() {
		return morningLateTime;
	}

	public void setMorningLateTime(int morningLateTime) {
		this.morningLateTime = morningLateTime;
	}

	public int getMorningLeaveTime() {
		return morningLeaveTime;
	}

	public void setMorningLeaveTime(int morningLeaveTime) {
		this.morningLeaveTime = morningLeaveTime;
	}

	public double getAfternoonWorkTime() {
		return afternoonWorkTime;
	}

	public void setAfternoonWorkTime(double afternoonWorkTime) {
		this.afternoonWorkTime = afternoonWorkTime;
	}

	public int getAfternoonLateTime() {
		return afternoonLateTime;
	}

	public void setAfternoonLateTime(int afternoonLateTime) {
		this.afternoonLateTime = afternoonLateTime;
	}

	public int getAfternoonLeaveTime() {
		return afternoonLeaveTime;
	}

	public void setAfternoonLeaveTime(int afternoonLeaveTime) {
		this.afternoonLeaveTime = afternoonLeaveTime;
	}

	public double getNightWorkTime() {
		return nightWorkTime;
	}

	public void setNightWorkTime(double nightWorkTime) {
		this.nightWorkTime = nightWorkTime;
	}

	public int getNightLateTime() {
		return nightLateTime;
	}

	public void setNightLateTime(int nightLateTime) {
		this.nightLateTime = nightLateTime;
	}

	public int getNightLeaveTime() {
		return nightLeaveTime;
	}

	public void setNightLeaveTime(int nightLeaveTime) {
		this.nightLeaveTime = nightLeaveTime;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	
	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	public double getAward() {
		return award;
	}

	public void setAward(double award) {
		this.award = award;
	}

	public double getOvertime() {
		return overtime;
	}

	public void setOvertime(double overtime) {
		this.overtime = overtime;
	}
	public Date getbTime() {
		return bTime;
	}

	public void setbTime(Date bTime) {
		this.bTime = bTime;
	}

	public Date geteTime() {
		return eTime;
	}

	public void seteTime(Date eTime) {
		this.eTime = eTime;
	}
	@Override
	public String toString() {
		return "Time [id=" + id + ", workDate=" + workDate + ", late=" + late + ", leavetime=" + leavetime
				+ ", workTime=" + workTime + ", morningWorkTime=" + morningWorkTime + ", morningLateTime="
				+ morningLateTime + ", morningLeaveTime=" + morningLeaveTime + ", afternoonWorkTime="
				+ afternoonWorkTime + ", afternoonLateTime=" + afternoonLateTime + ", afternoonLeaveTime="
				+ afternoonLeaveTime + ", nightWorkTime=" + nightWorkTime + ", nightLateTime=" + nightLateTime
				+ ", nightLeaveTime=" + nightLeaveTime + ", salary=" + salary + ", employeeId=" + employeeId
				+ ", employeeName=" + employeeName + ", workHours=" + workHours + ", fine=" + fine + ", award=" + award
				+ "]";
	}
	
}
