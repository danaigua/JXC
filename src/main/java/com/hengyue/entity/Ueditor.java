package com.hengyue.entity;

public class Ueditor {

	private String state;				//SUCCESS或者ERROR
	
	private String url;					//上传地址
	
	private String title;				//图片名称
	
	private String original;			//图片名称
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	
}
