package com.hengyue.entity.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 职位
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_position")
public class Position {
	
	@Id
	@GeneratedValue
	private Integer id;				//编号
	
	@ManyToOne
	@JoinColumn(name = "salary_id")
	private Salary salary;			//薪资
	
	@Column(length = 60)
	private String name;			//名称
	
	@Column(length = 1000)
	private String remarks;			//备注
	
	@Transient
	private String salaryName;		//薪资名称

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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public Salary getSalary() {
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}

	
	public String getSalaryName() {
		return salaryName;
	}

	public void setSalaryName(String salaryName) {
		this.salaryName = salaryName;
	}

	@Override
	public String toString() {
		return "Position [id=" + id + ", salary=" + salary + ", name=" + name + ", remarks=" + remarks + "]";
	}
}
