package com.airsupply.olap.vo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Catalogs {
	@XStreamImplicit(itemFieldName = "Catalog")
	CopyOnWriteArrayList<Catalog> catalogs;
	
	
	
	public List<Catalog> getCatalogs() {
		return catalogs;
	}



	public void setCatalogs(CopyOnWriteArrayList<Catalog> catalogs) {
		this.catalogs = catalogs;
	}
	
	



	

}
