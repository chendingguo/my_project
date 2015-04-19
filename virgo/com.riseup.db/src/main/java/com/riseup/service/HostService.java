package com.riseup.service;

import java.util.List;

import com.riseup.domain.Host;

public interface HostService {
	public List<Host> selectHost(Host host);

	public int insertHost(Host host);

}
