package com.hengyue.entity;

import java.util.Date;
import java.util.List;

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
 * 销售实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_SaleList")
public class SaleList {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 100)
	private String  saleNumber;	//进货单saleNumber
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;	//供应商
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date saleDate; 	//进货日期
	
	@Transient
	private Date bSaleDate;		//起始日期时间
	
	@Transient
	private Date eSaleDate;		//结束日期时间
	
	private float amountPayable;	//应付金额
	
	private float amountPaid;		//实付金额
	
	private Integer state;			//交易状态		1已付2未付
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;				//操作用户
	
	@Column(length = 1000)
	private String remarks;			//备注
	
	@Transient
	private List<SaleListGoods> saleListGoodsList = null;
	
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public Date getbSaleDate() {
		return bSaleDate;
	}

	public void setbSaleDate(Date bSaleDate) {
		this.bSaleDate = bSaleDate;
	}

	public Date geteSaleDate() {
		return eSaleDate;
	}

	public void seteSaleDate(Date eSaleDate) {
		this.eSaleDate = eSaleDate;
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
		return "SaleList [id=" + id + ", SaleNumber=" + saleNumber + ", customer=" + customer + ", saleDate=" + saleDate
				+ ", bSaleDate=" + bSaleDate + ", eSaleDate=" + eSaleDate + ", amountPayable=" + amountPayable
				+ ", amountPaid=" + amountPaid + ", state=" + state + ", user=" + user + ", remarks=" + remarks + "]";
	}

	public List<SaleListGoods> getSaleListGoodsList() {
		return saleListGoodsList;
	}

	public void setSaleListGoodsList(List<SaleListGoods> saleListGoodsList) {
		this.saleListGoodsList = saleListGoodsList;
	}
	
	
}
