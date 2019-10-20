package com.hengyue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

/**
 * 角色实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_role")
public class Role {

	@Id
	@GeneratedValue
	private Integer id;		//编号
	
	@Column(length = 50)
	private String name;	//名称
	
	@Column(length = 1000)
	private String remarks;	//备注

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

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", remarks=" + remarks + "]";
	}
	
	
}
