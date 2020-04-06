package com.hengyue.entity.vo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengyue.entity.CustomDateTimeSerializer;

@Entity
@Table(name = "t_plan_course_standard")
public class PlanCourseStandard {
	
	@Id
	@GeneratedValue
	private Integer id;						//编号
	
	private String name;					//名称
	
	private String standard;				//标准
	
	private String explains;				//标准说明
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTimes;				//标准创立时间
	
	private String remarks;					//备注
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="planCourseStandardTypeId")
	private PlanCourseStandardType planCourseStandardType;

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

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	
	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateTimes() {
		return createTimes;
	}

	public void setCreateTimes(Date createTimes) {
		this.createTimes = createTimes;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

	public PlanCourseStandardType getPlanCourseStandardType() {
		return planCourseStandardType;
	}

	public void setPlanCourseStandardType(PlanCourseStandardType planCourseStandardType) {
		this.planCourseStandardType = planCourseStandardType;
	}

	@Override
	public String toString() {
		return "PlanCourseStandard [id=" + id + ", name=" + name + ", standard=" + standard + ", explains=" + explains
				+ ", createTimes=" + createTimes + ", remarks=" + remarks + "]";
	}
	
}
