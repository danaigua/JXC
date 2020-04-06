package com.hengyue.entity.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 薪资模板实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_salary")
public class Salary {

	@Id
	@GeneratedValue
	private Integer id;				//编号
	
	@Column(length = 60)
	private String name;			//名称
	
	private double absenteeism;		//矿工
	
	private double lateFiveMin;			//迟到5分钟
	
	private double lateTenMin;			//迟到5分钟~10分钟
	
	private double lateThanTenLessThirdTenMin;			//迟到10分钟~30分钟
	
	private double lateThanThirdTenMin;			//迟到超过30分钟
	
	private double leavetimeOneMin;			//早退1分钟
	
	private double leavetimeTwoMin;			//早退2分钟
	
	private double hourSalary;				//时薪
	
	private double overtime;		//加班
	
	private double meal;			//报餐
	
	private double putUp;			//住宿
	
	@Column(length = 1000)
	private String remarks;			//备注
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAbsenteeism() {
		return absenteeism;
	}

	public void setAbsenteeism(double absenteeism) {
		this.absenteeism = absenteeism;
	}

	public double getLateFiveMin() {
		return lateFiveMin;
	}

	public void setLateFiveMin(double lateFiveMin) {
		this.lateFiveMin = lateFiveMin;
	}

	public double getLateTenMin() {
		return lateTenMin;
	}

	public void setLateTenMin(double lateTenMin) {
		this.lateTenMin = lateTenMin;
	}

	public double getHourSalary() {
		return hourSalary;
	}

	public void setHourSalary(double hourSalary) {
		this.hourSalary = hourSalary;
	}

	public double getLateThanTenLessThirdTenMin() {
		return lateThanTenLessThirdTenMin;
	}

	public void setLateThanTenLessThirdTenMin(double lateThanTenLessThirdTenMin) {
		this.lateThanTenLessThirdTenMin = lateThanTenLessThirdTenMin;
	}

	public double getLateThanThirdTenMin() {
		return lateThanThirdTenMin;
	}

	public void setLateThanThirdTenMin(double lateThanThirdTenMin) {
		this.lateThanThirdTenMin = lateThanThirdTenMin;
	}


	public double getLeavetimeOneMin() {
		return leavetimeOneMin;
	}

	public void setLeavetimeOneMin(double leavetimeOneMin) {
		this.leavetimeOneMin = leavetimeOneMin;
	}

	public double getLeavetimeTwoMin() {
		return leavetimeTwoMin;
	}

	public void setLeavetimeTwoMin(double leavetimeTwoMin) {
		this.leavetimeTwoMin = leavetimeTwoMin;
	}

	public double getOvertime() {
		return overtime;
	}

	public void setOvertime(double overtime) {
		this.overtime = overtime;
	}

	public double getMeal() {
		return meal;
	}

	public void setMeal(double meal) {
		this.meal = meal;
	}

	public double getPutUp() {
		return putUp;
	}

	public void setPutUp(double putUp) {
		this.putUp = putUp;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	


	@Override
	public String toString() {
		return "Salary [id=" + id + ", name=" + name + ", absenteeism=" + absenteeism + ", lateFiveMin=" + lateFiveMin
				+ ", lateTenMin=" + lateTenMin + ", lateThanTenLessThirdTenMin=" + lateThanTenLessThirdTenMin
				+ ", lateThanThirdTenMin=" + lateThanThirdTenMin + ", leavetimeOneMin=" + leavetimeOneMin
				+ ", leavetimeTwoMin=" + leavetimeTwoMin + ", hourSalary=" + hourSalary + ", overtime=" + overtime
				+ ", meal=" + meal + ", putUp=" + putUp + ", remarks=" + remarks + "]";
	}
	
	
}
