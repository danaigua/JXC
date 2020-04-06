package com.hengyue.entity.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengyue.entity.CustomDateTimeSerializer;

/**
 * 文件上传实体类
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_file_upload")
public class FileUpload {


	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 100)
	private String name; 			//上传文件名称
	
	@Column(length = 100)
	private String formatname;		//文件格式化之后的名称
	
	@Column(length = 255)
	private String url;				//上传路径
	
	@Column(length = 20)
	private String size;			//文件大小
	
	private Integer state;			//文件状态：（1分析过了， 2 没有分析过）
	
	@Column(length = 50)
	private String type;			//文件类型（文件扩展名）
	
	private Integer label;			//标签
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;				//文件上传时间

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

	public String getFormatname() {
		return formatname;
	}

	public void setFormatname(String formatname) {
		this.formatname = formatname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLabel() {
		return label;
	}

	public void setLabel(Integer label) {
		this.label = label;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "FileUpload [id=" + id + ", name=" + name + ", formatname=" + formatname + ", url=" + url + ", size="
				+ size + ", state=" + state + ", type=" + type + ", label=" + label + ", time=" + time + "]";
	}
	
	
}
