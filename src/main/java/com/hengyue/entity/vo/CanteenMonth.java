package com.hengyue.entity.vo;
/**
 * 食堂管理
 * @author 章家宝
 *
 */

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_canteen_month")
public class CanteenMonth implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	private Date times;				//时间
	
	@Column(length = 200)
	private String afternoon;		//中午
	
	private Double afternoonMoney;	//中午吃饭的钱
	
	@Column(length = 200)
	private String night;			//晚上吃饭的人
	
	private Double nightMoney;		//晚上吃饭的人的钱
	
	@Column(length = 200)
	private String afternoonAndNight; //吃中午和晚上的人
	
	private Double afternoonAndNightMoney;	//吃中午和晚上的人的钱
	
	@Column(length = 200)
	private String sandun;			//吃三顿的人
	
	private Double sandunMoney;		//吃三顿的人的钱
	
	private Double totalMoney;		//合计金额
	
	@Transient
	private String timeString;		//时间字符串
	
	@Transient
	private String afternoonNum;		//中午人数
	
	@Transient
	private String nightNum;		//晚上人数
	
	@Transient
	private String afternoonAndNightNum;	//中午和晚上的人数
	
	@Transient
	private String sandunNum;		//三顿人数
	
	@Column(length = 2000)
	private String remarks;			//备注
	
	private String month;			//月份
	
	private String year;			//年份
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTimes() {
		return times;
	}

	public void setTimes(Date times) {
		this.times = times;
	}

	public String getAfternoon() {
		return afternoon;
	}

	public void setAfternoon(String afternoon) {
		this.afternoon = afternoon;
	}

	public Double getAfternoonMoney() {
		return afternoonMoney;
	}

	public void setAfternoonMoney(Double afternoonMoney) {
		this.afternoonMoney = afternoonMoney;
	}

	public String getNight() {
		return night;
	}

	public void setNight(String night) {
		this.night = night;
	}

	public Double getNightMoney() {
		return nightMoney;
	}

	public void setNightMoney(Double nightMoney) {
		this.nightMoney = nightMoney;
	}

	public String getAfternoonAndNight() {
		return afternoonAndNight;
	}

	public void setAfternoonAndNight(String afternoonAndNight) {
		this.afternoonAndNight = afternoonAndNight;
	}

	public Double getAfternoonAndNightMoney() {
		return afternoonAndNightMoney;
	}

	public void setAfternoonAndNightMoney(Double afternoonAndNightMoney) {
		this.afternoonAndNightMoney = afternoonAndNightMoney;
	}

	public String getSandun() {
		return sandun;
	}

	public void setSandun(String sandun) {
		this.sandun = sandun;
	}

	public Double getSandunMoney() {
		return sandunMoney;
	}

	public void setSandunMoney(Double sandunMoney) {
		this.sandunMoney = sandunMoney;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public String getAfternoonNum() {
		return afternoonNum;
	}

	public void setAfternoonNum(String afternoonNum) {
		this.afternoonNum = afternoonNum;
	}

	public String getNightNum() {
		return nightNum;
	}

	public void setNightNum(String nightNum) {
		this.nightNum = nightNum;
	}

	public String getAfternoonAndNightNum() {
		return afternoonAndNightNum;
	}

	public void setAfternoonAndNightNum(String afternoonAndNightNum) {
		this.afternoonAndNightNum = afternoonAndNightNum;
	}

	public String getSandunNum() {
		return sandunNum;
	}

	public void setSandunNum(String sandunNum) {
		this.sandunNum = sandunNum;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "CanteenMonth [id=" + id + ", times=" + times + ", afternoon=" + afternoon + ", afternoonMoney="
				+ afternoonMoney + ", night=" + night + ", nightMoney=" + nightMoney + ", afternoonAndNight="
				+ afternoonAndNight + ", afternoonAndNightMoney=" + afternoonAndNightMoney + ", sandun=" + sandun
				+ ", sandunMoney=" + sandunMoney + ", totalMoney=" + totalMoney + ", timeString=" + timeString
				+ ", afternoonNum=" + afternoonNum + ", nightNum=" + nightNum + ", afternoonAndNightNum="
				+ afternoonAndNightNum + ", sandunNum=" + sandunNum + "]";
	}
	
	
}
