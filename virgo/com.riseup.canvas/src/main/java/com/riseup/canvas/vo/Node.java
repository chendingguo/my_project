package com.riseup.canvas.vo;

import java.io.Serializable;

public class Node implements Serializable{

	private static final long serialVersionUID = 3449226795658958734L;
	private String id;
	private String label;
	private String title  ;
	private String x;
	private String y;
	private String allowedToMoveX;
	private String allowedToMoveY;
	private String group;
	private String value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getAllowedToMoveX() {
		return allowedToMoveX;
	}
	public void setAllowedToMoveX(String allowedToMoveX) {
		this.allowedToMoveX = allowedToMoveX;
	}
	public String getAllowedToMoveY() {
		return allowedToMoveY;
	}
	public void setAllowedToMoveY(String allowedToMoveY) {
		this.allowedToMoveY = allowedToMoveY;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	

}
