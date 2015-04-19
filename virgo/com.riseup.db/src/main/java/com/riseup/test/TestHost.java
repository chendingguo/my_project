package com.riseup.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.riseup.domain.Host;
import com.riseup.persistence.HostMapper;
import com.riseup.service.HostServiceImpl;
import com.riseup.utils.BeanUtil;


public class TestHost extends BaseTest {
	
	@Test
	
	public void testEvent() throws Exception {
		HostServiceImpl hostService=serviceProxy.getService("hostService");
		Host host=new Host();
		
		
		hostService.insertHost(host);
		List<Host> hosts=hostService.selectHost(host);
		
		for(Host h:hosts){
			System.err.println(BeanUtil.buildString(h));
		}
	
		
		
	}
	
}
