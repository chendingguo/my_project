package com.riseup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riseup.domain.Host;
import com.riseup.service.GetInfoService;
import com.riseup.service.HostService;

@Controller
public class CanvasController {
	@Autowired
	private GetInfoService getInfoServiceImpl;
	
	@Autowired
	private HostService hostServiceImpl;

	@RequestMapping(value = "/testService", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Object testService() {
		try {
			return hostServiceImpl.selectHost(new Host());
		} catch (Exception e) {
			return e;
		}

	}

}
