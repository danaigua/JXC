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
 * 日志实体
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_log")
public class Log {

	public final static String LOGIN_ACTION = "登录操作";
	public final static String LOGOUT_ACTION = "注销操作";
	public final static String SEARCH_ACTION = "查询操作";
	public final static String UPDATE_ACTION = "更新操作";
	public final static String DELETE_ACTION = "删除操作";
	public static final String ADD_ACTION = null;
	
	@Id
	@GeneratedValue
	private Integer id;		//编号
	
	@Column(length = 100)
	private String type;	//日志类型
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;		//操作用户
	
	@Column(length = 3000)
	private String content;	//操作内容
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;		//操作时间

	@Transient
	private Date btime;
	
	private Date etime;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	public Log() {
		super();
	}

	public Log(String type, String content) {
		super();
		this.type = type;
		this.content = content;
	}
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getBtime() {
		return btime;
	}

	public void setBtime(Date btime) {
		this.btime = btime;
	}

	public Date getEtime() {
		return etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}
	
	
}
