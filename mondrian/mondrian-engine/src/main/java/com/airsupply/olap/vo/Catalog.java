package com.airsupply.olap.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Catalog {
	@XStreamAsAttribute
	String name;

	@XStreamAlias("DataSourceInfo")
	String dataSourceInfo;

	@XStreamAlias("Definition")
	String definition;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataSourceInfo() {
		return dataSourceInfo;
	}

	public void setDataSourceInfo(String dataSourceInfo) {
		this.dataSourceInfo = dataSourceInfo;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}
}