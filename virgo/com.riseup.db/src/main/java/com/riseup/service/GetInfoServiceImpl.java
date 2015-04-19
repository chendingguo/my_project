package com.riseup.service;

import org.springframework.stereotype.Service;

@Service
public class GetInfoServiceImpl implements GetInfoService{
	
	

	@Override
	public String getInfo() {
		return "hello world";
	}

}
