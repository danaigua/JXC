package com.hengyue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * 报溢单实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_overflowList")
public class OverflowList {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 100)
	private String  overflowNumber;	//报溢单
	
	@ManyToOne
	@JoinColumn(name = "supplierId")
	private Supplier supplier;	//供应商
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date overflowDate; 	//报溢日期
	
	@Transient
	private Date bOverflowDate;		//起始日期时间
	
	@Transient
	private Date eOverflowDate;		//结束日期时间

	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;				//操作用户
	
	@Column(length = 1000)
	private String remarks;			//备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOverflowNumber() {
		return overflowNumber;
	}

	public void setOverflowNumber(String overflowNumber) {
		this.overflowNumber = overflowNumber;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getOverflowDate() {
		return overflowDate;
	}

	public void setOverflowDate(Date overflowDate) {
		this.overflowDate = overflowDate;
	}

	@Override
	public String toString() {
		return "OverflowList [id=" + id + ", overflowNumber=" + overflowNumber + ", supplier=" + supplier + ", overflowDate="
				+ overflowDate + ", bOverflowDate=" + bOverflowDate + ", eOverflowDate=" + eOverflowDate + ", user=" + user
				+ ", remarks=" + remarks + "]";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getbOverflowDate() {
		return bOverflowDate;
	}

	public void setbOverflowDate(Date bOverflowDate) {
		this.bOverflowDate = bOverflowDate;
	}

	public Date geteOverflowDate() {
		return eOverflowDate;
	}

	public void seteOverflowDate(Date eOverflowDate) {
		this.eOverflowDate = eOverflowDate;
	}
	
	
}
