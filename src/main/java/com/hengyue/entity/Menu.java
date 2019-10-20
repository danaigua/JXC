package com.hengyue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 菜单实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_menu")
public class Menu {

	@Id
	@GeneratedValue
	private Integer id;		//编号
	
	@Column(length = 50)
	private String name;	//名称
	
	@Column(length = 200)
	private String url; 	//菜单地址
	
	private Integer state;	//菜单节点类型 1 根节点 0 叶子节点
	
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
}
