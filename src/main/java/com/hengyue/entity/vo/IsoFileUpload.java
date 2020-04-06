package com.hengyue.entity.vo;

import java.util.Date;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengyue.entity.CustomDateTimeSerializer;

/**
 * 文件上传实体类
 * @author 章家宝
 *
 */
@Entity
@Table(name = "t_iso_file_upload")
public class IsoFileUpload {


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
	
	@Column(length = 50)
	private String type;			//文件类型（文件扩展名）
	
	private Integer label;			//标签
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;				//文件上传时间
	
	private Integer download;		//下载次数
	
	private String remarks;			//备注
	
	private String content;			//主要描述
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="folderTypeId")
	private FolderType folderType;

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

	public Integer getDownload() {
		return download;
	}

	public void setDownload(Integer download) {
		this.download = download;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public FolderType getFolderType() {
		return folderType;
	}

	public void setFolderType(FolderType folderType) {
		this.folderType = folderType;
	}

	@Override
	public String toString() {
		return "IsoFileUpload [id=" + id + ", name=" + name + ", formatname=" + formatname + ", url=" + url + ", size="
				+ size + ", type=" + type + ", label=" + label + ", time=" + time + ", download=" + download
				+ ", remarks=" + remarks + ", content=" + content + "]";
	}
	
	
}
