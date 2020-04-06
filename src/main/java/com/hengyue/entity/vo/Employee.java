package com.hengyue.entity.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengyue.entity.CustomDateTimeSerializer;
/**
 * 员工实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_employee")
public class Employee {

	@Id
	@GeneratedValue
	private Integer id;				//编号
	
	private String employeeNum;	//工号
	
	@Column(length = 60)
	private String name;			//名称
	
	@Column(length = 1000)
	private String password;		//密码
	
	@Column(length = 20)
	private String telephone;		//联系电话
	
	@Column(length = 20)
	private String eMail;			//电子邮箱
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;	//部门
	
	@Column(length = 50)
	private String position;		//职位
	
	private double workingHours;	//工作时间
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryTime;			//入职时间
	
	@ManyToOne
	@JoinColumn(name = "assess_id")
	private Assess assess;			//绩效考核
	
	private Integer lateTimes;		//迟到次数
	
	@Column(length = 128)
	private String address;			//家庭住址
	
	@Transient
	private String departmentName;	//部门名称

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(double workingHours) {
		this.workingHours = workingHours;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Assess getAssess() {
		return assess;
	}

	public void setAssess(Assess assess) {
		this.assess = assess;
	}

	public Integer getLateTimes() {
		return lateTimes;
	}

	public void setLateTimes(Integer lateTimes) {
		this.lateTimes = lateTimes;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	
	
	public String getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeNum=" + employeeNum + ", name=" + name + ", password=" + password
				+ ", telephone=" + telephone + ", eMail=" + eMail + ", department=" + department + ", position="
				+ position + ", workingHours=" + workingHours + ", entryTime=" + entryTime + ", assess=" + assess
				+ ", lateTimes=" + lateTimes + ", address=" + address + ", departmentName=" + departmentName + "]";
	}
	
	
}
