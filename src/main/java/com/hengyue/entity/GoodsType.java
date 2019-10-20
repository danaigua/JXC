package com.hengyue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *类别 菜单实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_goodstype")
public class GoodsType {

	@Id
	@GeneratedValue
	private Integer id;		//编号
	
	@Column(length = 50)
	private String name;	//类别名称
	
	private Integer state;	//类别节点类型 1 根节点 0 叶子节点
	
	
	@Column(length = 100)
	private String icon;	//图标样式
	
	private Integer pId;	//父菜单id
	

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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getPid() {
		return pId;
	}

	public void setPid(Integer pId) {
		this.pId = pId;
	}

	@Override
	public String toString() {
		return "GoodsType [id=" + id + ", name=" + name + ", state=" + state + ", icon=" + icon + ", pId=" + pId + "]";
	}
	
}
