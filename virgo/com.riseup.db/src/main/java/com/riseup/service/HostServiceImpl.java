package com.riseup.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riseup.domain.Host;
import com.riseup.persistence.HostMapper;
@Service
public class HostServiceImpl implements HostService{
	@Autowired 
	private HostMapper hostMapper;
	
	
	//@PostConstruct  
	public List<Host> selectHost(Host host){
		return hostMapper.selectHost(host);
		
	}
	
	
	public int insertHost(Host host){
		return hostMapper.insertHost(host);
		
	}
	
	
	public int deleteHost(Host host){
		return hostMapper.deleteHost(host);
		
	}
	
	
	public int updateHost(Host host){
		return hostMapper.updateHost(host);
		
	}


}
