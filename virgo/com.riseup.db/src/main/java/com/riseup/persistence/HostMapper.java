package com.riseup.persistence;

import java.util.List;

import com.riseup.domain.Host;

public interface HostMapper {

	List<Host> selectHost(Host Host);

	int insertHost(Host Host);
	
	int deleteHost(Host Host);
	
	int updateHost(Host Host);

}
