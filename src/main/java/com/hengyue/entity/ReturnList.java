package com.hengyue.entity;

import java.util.Date;

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
/**
 * 退货单实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_returnList")
public class ReturnList {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 100)
	private String  returnNumber;	//退货单
	
	@ManyToOne
	@JoinColumn(name = "supplierId")
	private Supplier supplier;	//供应商
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnDate; 	//退货日期
	
	@Transient
	private Date bReturnDate;		//起始日期时间
	
	@Transient
	private Date eReturnDate;		//结束日期时间
	
	private float amountPayable;	//应付金额
	
	private float amountPaid;		//实付金额
	
	private Integer state;			//交易状态		1已付2未付
	
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getreturnDate() {
		return returnDate;
	}

	public void setreturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public float getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(float amountPayable) {
		this.amountPayable = amountPayable;
	}

	public float getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(float amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public String getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(String returnNumber) {
		this.returnNumber = returnNumber;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getbReturnDate() {
		return bReturnDate;
	}

	public void setbReturnDate(Date bReturnDate) {
		this.bReturnDate = bReturnDate;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date geteReturnDate() {
		return eReturnDate;
	}

	public void seteReturnDate(Date eReturnDate) {
		this.eReturnDate = eReturnDate;
	}

	@Override
	public String toString() {
		return "ReturnList [id=" + id + ", returnNumber=" + returnNumber + ", supplier=" + supplier + ", returnDate="
				+ returnDate + ", bReturnDate=" + bReturnDate + ", eReturnDate=" + eReturnDate + ", amountPayable="
				+ amountPayable + ", amountPaid=" + amountPaid + ", state=" + state + ", user=" + user + ", remarks="
				+ remarks + "]";
	}
	
}
