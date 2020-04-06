package com.hengyue.entity.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 绩效考核实体类
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_assess")
public class Assess {
	
	@Id
	@GeneratedValue
	private Integer id;				//编号
	
	@Column(length = 60)
	private String name;			//名称
	
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "Assess [id=" + id + ", name=" + name + ", remarks=" + remarks + "]";
	}
}
