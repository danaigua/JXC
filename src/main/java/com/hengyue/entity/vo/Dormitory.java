package com.hengyue.entity.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 宿舍管理
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_dormitory")
public class Dormitory implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 300)
	private String name;
	
	private Integer employee1;
	
	private Integer employee2;
	
	private Integer employee3;
	
	private Integer employee4;
	
	private Integer employee5;
	
	private Integer employee6;
	
	private Integer employee7;
	
	private Integer employee8;
	
	@Transient
	private String employee1Name;
	
	@Transient
	private String employee2Name;
	
	@Transient
	private String employee3Name;
	
	@Transient
	private String employee4Name;
	
	@Transient
	private String employee5Name;
	
	@Transient
	private String employee6Name;
	
	@Transient
	private String employee7Name;
	
	@Transient
	private String employee8Name;

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

	public Integer getEmployee1() {
		return employee1;
	}

	public void setEmployee1(Integer employee1) {
		this.employee1 = employee1;
	}

	public Integer getEmployee2() {
		return employee2;
	}

	public void setEmployee2(Integer employee2) {
		this.employee2 = employee2;
	}

	public Integer getEmployee3() {
		return employee3;
	}

	public void setEmployee3(Integer employee3) {
		this.employee3 = employee3;
	}

	public Integer getEmployee4() {
		return employee4;
	}

	public void setEmployee4(Integer employee4) {
		this.employee4 = employee4;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	public String getEmployee1Name() {
		return employee1Name;
	}

	public void setEmployee1Name(String employee1Name) {
		this.employee1Name = employee1Name;
	}

	public String getEmployee2Name() {
		return employee2Name;
	}

	public void setEmployee2Name(String employee2Name) {
		this.employee2Name = employee2Name;
	}

	public String getEmployee3Name() {
		return employee3Name;
	}

	public void setEmployee3Name(String employee3Name) {
		this.employee3Name = employee3Name;
	}

	public String getEmployee4Name() {
		return employee4Name;
	}

	public void setEmployee4Name(String employee4Name) {
		this.employee4Name = employee4Name;
	}
	
	

	public Integer getEmployee5() {
		return employee5;
	}

	public void setEmployee5(Integer employee5) {
		this.employee5 = employee5;
	}

	public Integer getEmployee6() {
		return employee6;
	}

	public void setEmployee6(Integer employee6) {
		this.employee6 = employee6;
	}

	public Integer getEmployee7() {
		return employee7;
	}

	public void setEmployee7(Integer employee7) {
		this.employee7 = employee7;
	}

	public Integer getEmployee8() {
		return employee8;
	}

	public void setEmployee8(Integer employee8) {
		this.employee8 = employee8;
	}

	public String getEmployee5Name() {
		return employee5Name;
	}

	public void setEmployee5Name(String employee5Name) {
		this.employee5Name = employee5Name;
	}

	public String getEmployee6Name() {
		return employee6Name;
	}

	public void setEmployee6Name(String employee6Name) {
		this.employee6Name = employee6Name;
	}

	public String getEmployee7Name() {
		return employee7Name;
	}

	public void setEmployee7Name(String employee7Name) {
		this.employee7Name = employee7Name;
	}

	public String getEmployee8Name() {
		return employee8Name;
	}

	public void setEmployee8Name(String employee8Name) {
		this.employee8Name = employee8Name;
	}

	@Override
	public String toString() {
		return "Dormitory [id=" + id + ", name=" + name + ", employee1=" + employee1 + ", employee2=" + employee2
				+ ", employee3=" + employee3 + ", employee4=" + employee4 + "]";
	}
	
}
