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
/**
 * 客户退货实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_customerReturnList")
public class CustomerReturnList {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 100)
	private String  customerReturnNumber;	//进货单customerReturnNumber
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;	//供应商
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date customerReturnDate; 	//进货日期
	
	@Transient
	private Date bCustomerReturnDate;		//起始日期时间
	
	@Transient
	private Date eCustomerReturnDate;		//结束日期时间
	
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


	public String getCustomerReturnNumber() {
		return customerReturnNumber;
	}

	public void setCustomerReturnNumber(String customerReturnNumber) {
		this.customerReturnNumber = customerReturnNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getCustomerReturnDate() {
		return customerReturnDate;
	}

	public void setCustomerReturnDate(Date customerReturnDate) {
		this.customerReturnDate = customerReturnDate;
	}

	public Date getbCustomerReturnDate() {
		return bCustomerReturnDate;
	}

	public void setbCustomerReturnDate(Date bCustomerReturnDate) {
		this.bCustomerReturnDate = bCustomerReturnDate;
	}

	public Date geteCustomerReturnDate() {
		return eCustomerReturnDate;
	}

	public void seteCustomerReturnDate(Date eCustomerReturnDate) {
		this.eCustomerReturnDate = eCustomerReturnDate;
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

	@Override
	public String toString() {
		return "CustomerReturnList [id=" + id + ", CustomerReturnNumber=" + customerReturnNumber + ", customer=" + customer + ", customerReturnDate=" + customerReturnDate
				+ ", bCustomerReturnDate=" + bCustomerReturnDate + ", eCustomerReturnDate=" + eCustomerReturnDate + ", amountPayable=" + amountPayable
				+ ", amountPaid=" + amountPaid + ", state=" + state + ", user=" + user + ", remarks=" + remarks + "]";
	}
	
	
}