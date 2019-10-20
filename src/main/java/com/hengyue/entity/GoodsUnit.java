package com.hengyue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品单位实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_goodsunit")
public class GoodsUnit {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 10)
	private String name;		//商品单位名称

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

	@Override
	public String toString() {
		return "GoodsUnit [id=" + id + ", name=" + name + "]";
	}
	
	

}
