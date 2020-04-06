package com.hengyue.entity.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 计划-计划过程实体类
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_plan_plan_course")
public class PlanPlanCourse {
	
	@Id
	@GeneratedValue
	private Integer id;				//编号
	
	@ManyToOne
	@JoinColumn(name = "plan_id")
	private Plan plan;
	
	@ManyToOne
	@JoinColumn(name = "plan_course")
	private PlanCourse planCourse;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public PlanCourse getPlanCourse() {
		return planCourse;
	}

	public void setPlanCourse(PlanCourse planCourse) {
		this.planCourse = planCourse;
	}

	@Override
	public String toString() {
		return "PlanPlanCourse [id=" + id + ", plan=" + plan + ", planCourse=" + planCourse + "]";
	}
	
}
