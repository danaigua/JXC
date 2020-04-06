package com.hengyue.entity.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 部门职位实体类
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_department_position")
public class DepartmentPosition {
	
	@Id
	@GeneratedValue
	private Integer id;				//编号
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	
	@ManyToOne
	@JoinColumn(name = "position_id")
	private Position position;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "DepartmentPosition [id=" + id + ", department=" + department + ", position=" + position + "]";
	}
}
