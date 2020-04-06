package com.hengyue.entity.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hengyue.entity.Customer;
import com.hengyue.entity.User;
/**
 * 客户退货实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_ck_customer_return_list")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class CkCustomerReturnList implements Serializable {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 100)
	private String  customerReturnNumber;	//进货单customerReturnNumber
	
	private Integer customer;	//客户
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date customerReturnDate; 	//进货日期
	
	@Transient
	private Date bCkCustomerReturnDate;		//起始日期时间
	
	@Transient
	private Date eCkCustomerReturnDate;		//结束日期时间
	
	private float amountPayable;	//应付金额
	
	private float amountPaid;		//实付金额
	
	private Integer state;			//交易状态		1已付2未付
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;				//操作用户
	
	@Column(length = 1000)
	private String remarks;			//备注
	
	@Column(length = 1000)
	private String customerAddress;			//客户地址
	
	@Transient
	private List<CkCustomerReturnListGoods> customerReturnListGoodsList = null;
	
	@Transient
	private String customerName;
	
	@Transient
	private String customerTelephone;

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



	public Integer getCustomer() {
		return customer;
	}



	public void setCustomer(Integer customer) {
		this.customer = customer;
	}



	public Date getCustomerReturnDate() {
		return customerReturnDate;
	}



	public void setCustomerReturnDate(Date customerReturnDate) {
		this.customerReturnDate = customerReturnDate;
	}



	public Date getbCkCustomerReturnDate() {
		return bCkCustomerReturnDate;
	}



	public void setbCkCustomerReturnDate(Date bCkCustomerReturnDate) {
		this.bCkCustomerReturnDate = bCkCustomerReturnDate;
	}



	public Date geteCkCustomerReturnDate() {
		return eCkCustomerReturnDate;
	}



	public void seteCkCustomerReturnDate(Date eCkCustomerReturnDate) {
		this.eCkCustomerReturnDate = eCkCustomerReturnDate;
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



	public String getCustomerAddress() {
		return customerAddress;
	}



	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}



	public List<CkCustomerReturnListGoods> getCustomerReturnListGoodsList() {
		return customerReturnListGoodsList;
	}



	public void setCustomerReturnListGoodsList(List<CkCustomerReturnListGoods> customerReturnListGoodsList) {
		this.customerReturnListGoodsList = customerReturnListGoodsList;
	}



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getCustomerTelephone() {
		return customerTelephone;
	}



	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}



	@Override
	public String toString() {
		return "CkCustomerReturnList [id=" + id + ", customerReturnNumber=" + customerReturnNumber
				+ ", customerReturnDate=" + customerReturnDate + ", bCkCustomerReturnDate=" + bCkCustomerReturnDate
				+ ", eCkCustomerReturnDate=" + eCkCustomerReturnDate + ", amountPayable=" + amountPayable
				+ ", amountPaid=" + amountPaid + ", state=" + state + ", user=" + user + ", remarks=" + remarks
				+ ", customerReturnListGoodsList=" + customerReturnListGoodsList + "]";
	}
	
	
}
