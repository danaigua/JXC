package com.hengyue.controller.admin.vo;
/**
 * 获得工资实体
 * @author 章家宝
 *
 */
public class GetSalary {

	private String name;					//员工姓名
	
	private String num;						//员工工号
	
	private double commonTime;				//普通工时
	
	private double overTime;				//加班工时
	
	private int leaveEarly;					//早退次数
	
	private double leaveEarlySalary;		//早退扣费
	
	private int late;						//迟到次数
	
	private double lateSalary;				//迟到扣费
	
	private double feeDeduction;			//罚款
	
	private double salary;					//总工资
	
	private String description;				//迟到早退罚款描述
	
	private String meal;					//餐费

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCommonTime() {
		return commonTime;
	}

	public void setCommonTime(double commonTime) {
		this.commonTime = commonTime;
	}

	public double getOverTime() {
		return overTime;
	}

	public void setOverTime(double overTime) {
		this.overTime = overTime;
	}

	public int getLeaveEarly() {
		return leaveEarly;
	}

	public void setLeaveEarly(int leaveEarly) {
		this.leaveEarly = leaveEarly;
	}

	public double getLeaveEarlySalary() {
		return leaveEarlySalary;
	}

	public void setLeaveEarlySalary(double leaveEarlySalary) {
		this.leaveEarlySalary = leaveEarlySalary;
	}

	public int getLate() {
		return late;
	}

	public void setLate(int late) {
		this.late = late;
	}

	public double getLateSalary() {
		return lateSalary;
	}

	public void setLateSalary(double lateSalary) {
		this.lateSalary = lateSalary;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getFeeDeduction() {
		return feeDeduction;
	}

	public void setFeeDeduction(double feeDeduction) {
		this.feeDeduction = feeDeduction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMeal() {
		return meal;
	}

	public void setMeal(String meal) {
		this.meal = meal;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	
}
