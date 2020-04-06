package com.hengyue.entity.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 客户退货单商品实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_ck_customer_return_list_goods")
public class CkCustomerReturnListGoods {

	@Id
	@GeneratedValue
	private Integer id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "customerReturnListId")
	private CkCustomerReturnList customerReturnList;		//客户退货单
	
	@Column(length=50)
	private String code; // 商品编码
	
	@Column(length=50)
	private String name; // 商品名称
	
	@Column(length=50)
	private String model; // 商品型号
	
	@ManyToOne
	@JoinColumn(name="typeId")
	private CkGoodsType type; // 商品类别
	
	private Integer goodsId;		//商品id
	
	@Column(length=10)
	private String unit;			//商品单位
	
	private float price;	//单价
	
	private int num;		//数量
	
	private float total;	//总金额
	
	@Transient
	private Integer typeId;		//类别id
	
	@Transient
	private String codeOrName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CkCustomerReturnList getCustomerReturnList() {
		return customerReturnList;
	}

	public void setCustomerReturnList(CkCustomerReturnList customerReturnList) {
		this.customerReturnList = customerReturnList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public CkGoodsType getType() {
		return type;
	}

	public void setType(CkGoodsType type) {
		this.type = type;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getCodeOrName() {
		return codeOrName;
	}

	public void setCodeOrName(String codeOrName) {
		this.codeOrName = codeOrName;
	}

	@Override
	public String toString() {
		return "CkCustomerReturnListGoods [id=" + id + ", customerReturnList=" + customerReturnList + ", code=" + code
				+ ", name=" + name + ", model=" + model + ", type=" + type + ", goodsId=" + goodsId + ", unit=" + unit
				+ ", price=" + price + ", num=" + num + ", total=" + total + ", typeId=" + typeId + ", codeOrName="
				+ codeOrName + "]";
	}
	
	

	

}