package com.hengyue.entity.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengyue.entity.CustomDateTimeSerializer;
/**
 * 计划-规划实体类
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_plan")
public class Plan implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;						//编号
	
	@Column(length = 60)
	private String name;					//计划名称
	
	private String remarks;					//备注
	
	private String content;					//主要描述
	
	private Integer userId;					//操作的用户编号（负责人）

	@Temporal(TemporalType.TIMESTAMP)
	private Date times;						//对应的时间
	
	private Integer schedules;				//执行的进度
	
	private Integer finish = 0;				//是否已经完成
	
	@Transient
	private String principal;				//负责人
	
	@Transient
	private String isFinish;				//是否已经完成
	
	@Transient
	private String schedulesString;			//进度
	
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getTime() {
		return times;
	}

	public void setTime(Date times) {
		this.times = times;
	}

	public Integer getSchedules() {
		return schedules;
	}

	public void setSchedule(Integer schedules) {
		this.schedules = schedules;
	}

	public Integer getFinish() {
		return finish;
	}

	public void setFinish(Integer finish) {
		this.finish = finish;
	}

	public Date getTimes() {
		return times;
	}

	public void setTimes(Date times) {
		this.times = times;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setSchedules(Integer schedules) {
		this.schedules = schedules;
	}
	

	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}

	public String getSchedulesString() {
		return schedulesString;
	}

	public void setSchedulesString(String schedulesString) {
		this.schedulesString = schedulesString;
	}

	@Override
	public String toString() {
		return "Plan [id=" + id + ", name=" + name + ", remarks=" + remarks + ", content=" + content + ", userId="
				+ userId + ", time=" + times + ", schedule=" + schedules + ", finish=" + finish + "]";
	}
	
}
