package com.hengyue.entity.vo;
/**
 * 食堂管理
 * @author 章家宝
 *
 */

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_canteen")
public class Canteen implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(length = 200)
	private String groupName; // 组别

	@Column(length = 100)
	private String employeeName; // 员工姓名

	private Integer employeeId; // 员工id号码

	@Column(length = 20)
	private String times; // 就餐月份

	@Column(length = 1200)
	private String remarks; // 备注

	private String type; // 类型

	private double money; // 类型的钱
	
	@ManyToOne
	@JoinColumn(name="canteenMonthId")
	private CanteenMonth canteenMonth;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	
	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	public CanteenMonth getCanteenMonth() {
		return canteenMonth;
	}

	public void setCanteenMonth(CanteenMonth canteenMonth) {
		this.canteenMonth = canteenMonth;
	}

	@Override
	public String toString() {
		return "Canteen [id=" + id + ", groupName=" + groupName + ", employeeName=" + employeeName + ", employeeId="
				+ employeeId + ", times=" + times + ", remarks=" + remarks + ", type=" + type + ", money=" + money
				+ ", canteenMonth=" + canteenMonth + "]";
	}

}
