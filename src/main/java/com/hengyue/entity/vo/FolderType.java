package com.hengyue.entity.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 文件夹类型实体类
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_folder_type")
public class FolderType {
	

	@Id
	@GeneratedValue
	private Integer id;		//编号
	
	@Column(length = 50)
	private String name;	//类别名称
	
	private Integer state;	//类别节点类型 1 根节点 0 叶子节点
	
	
	@Column(length = 100)
	private String icon;	//图标样式
	
	private Integer pId;	//父菜单id
	
	@Column(length = 100)
	private String url;		//地址
	
	private Integer userId;	//用户id

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

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
	
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "FolderType [id=" + id + ", name=" + name + ", state=" + state + ", icon=" + icon + ", pId=" + pId
				+ ", url=" + url + ", userId=" + userId + "]";
	}
	
	

}
