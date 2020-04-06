package com.hengyue.entity.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
 *  
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_plan_course")
public class PlanCourse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;						//编号
	
	private String name;					//名称
	
	@Column(length = 3000)
	private String remarks;					//备注
	
	@Column(length = 3000)
	private String content;					//主要描述
	
	private Integer userId;					//操作的用户编号（负责人）
	
	private String keysname;				//执行的标准值
	
	private String valuesname;				//执行的标准值对应的结果
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date times;						//对应的时间
	
	private Integer schedules;				//执行的进度
	
	private Integer finish = 0;				//是否已经完成
	
	private String affirmQuestion;			//不确认原因
	
	private Integer affirm = 0;				//是否确认
	
	private String notFinishQuestion;		//没有完成的原因
	
	private String examine1;				//审批1
	
	private String reply1;					//回复一
	
	private String examine2;				//审批1
	
	private String reply2;					//回复2
	
	private String examine3;				//审批1
	
	private String reply3;					//回复一
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishTime;				//计划完成的时间
	
	@Transient
	private String principal;				//负责人
	
	@Transient
	private String isFinish;				//是否已经完成
	
	@Transient
	private String schedulesString;			//进度
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="planCourseStandardTypeId")
	private PlanCourseStandard planCourseStandard;	//任务
	
	@Transient
	private String projectPrincipal;			//项目总负责人
	
	@Transient
	private String projectName;				//项目名称
	
	@Transient
	private String questionState;			//确认状态
	
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


	public String getKeysname() {
		return keysname;
	}

	public void setKeysname(String keysname) {
		this.keysname = keysname;
	}

	public String getValuesname() {
		return valuesname;
	}

	public void setValuesname(String valuesname) {
		this.valuesname = valuesname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTimes(Date times) {
		this.times = times;
	}

	public void setSchedules(Integer schedules) {
		this.schedules = schedules;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getTimes() {
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
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
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

	public PlanCourseStandard getPlanCourseStandard() {
		return planCourseStandard;
	}

	public void setPlanCourseStandard(PlanCourseStandard planCourseStandard) {
		this.planCourseStandard = planCourseStandard;
	}
	
	

	public String getProjectPrincipal() {
		return projectPrincipal;
	}

	public void setProjectPrincipal(String projectPrincipal) {
		this.projectPrincipal = projectPrincipal;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	

	public String getAffirmQuestion() {
		return affirmQuestion;
	}

	public void setAffirmQuestion(String affirmQuestion) {
		this.affirmQuestion = affirmQuestion;
	}

	public Integer getAffirm() {
		return affirm;
	}

	public void setAffirm(Integer affirm) {
		this.affirm = affirm;
	}
	
	

	public String getQuestionState() {
		return questionState;
	}

	public void setQuestionState(String questionState) {
		this.questionState = questionState;
	}
	
	

	public String getNotFinishQuestion() {
		return notFinishQuestion;
	}

	public void setNotFinishQuestion(String notFinishQuestion) {
		this.notFinishQuestion = notFinishQuestion;
	}

	public String getExamine1() {
		return examine1;
	}

	public void setExamine1(String examine1) {
		this.examine1 = examine1;
	}

	public String getReply1() {
		return reply1;
	}

	public void setReply1(String reply1) {
		this.reply1 = reply1;
	}

	public String getExamine2() {
		return examine2;
	}

	public void setExamine2(String examine2) {
		this.examine2 = examine2;
	}

	public String getReply2() {
		return reply2;
	}

	public void setReply2(String reply2) {
		this.reply2 = reply2;
	}

	public String getExamine3() {
		return examine3;
	}

	public void setExamine3(String examine3) {
		this.examine3 = examine3;
	}

	public String getReply3() {
		return reply3;
	}

	public void setReply3(String reply3) {
		this.reply3 = reply3;
	}

	@Override
	public String toString() {
		return "PlanCourse [id=" + id + ", name=" + name + ", remarks=" + remarks + ", content=" + content + ", userId="
				+ userId + ", keysname=" + keysname + ", valuesname=" + valuesname + ", times=" + times + ", schedules="
				+ schedules + ", finish=" + finish + ", affirmQuestion=" + affirmQuestion + ", affirm=" + affirm
				+ ", notFinishQuestion=" + notFinishQuestion + ", examine1=" + examine1 + ", reply1=" + reply1
				+ ", examine2=" + examine2 + ", reply2=" + reply2 + ", examine3=" + examine3 + ", reply3=" + reply3
				+ ", finishTime=" + finishTime + ", principal=" + principal + ", isFinish=" + isFinish
				+ ", schedulesString=" + schedulesString + ", planCourseStandard=" + planCourseStandard
				+ ", projectPrincipal=" + projectPrincipal + ", projectName=" + projectName + ", questionState="
				+ questionState + "]";
	}

}
