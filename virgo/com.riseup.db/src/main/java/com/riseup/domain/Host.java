package com.riseup.domain;

import java.io.Serializable;

public class Host implements Serializable {
	private static final long serialVersionUID = 8751282105532159742L;
	private String hostIp;
	private String hostName;
	private String hostDesc;

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostDesc() {
		return hostDesc;
	}

	public void setHostDesc(String hostDesc) {
		this.hostDesc = hostDesc;
	}

}
