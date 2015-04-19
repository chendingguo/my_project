/*******************************************************************************
 * Copyright (c) 2013 VMware Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   VMware Inc. - initial contribution
 *******************************************************************************/

package com.riseup.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.riseup.domain.Host;
import com.riseup.service.HostServiceImpl;

/**
 * test the controller and git
 * @version 1.0
 * @author arisupply
 * 
 */
@Controller
public final class RestController {
	@Autowired
	HostServiceImpl hostImplServiceImpl;

	@Autowired
	private SqlSessionFactoryBean sqlSessionFactory;
	private Map<String, Info> model = Collections
			.synchronizedMap(new HashMap<String, Info>());

	public RestController() {
		this.model.put("roy", new Info("Roy T. Fielding",
				"Representational State Transfer"));
	}


	@RequestMapping(value = "/testService", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Object testService() {
		try {
			// SqlSession session = sqlSessionFactory.getObject().openSession();
			// HostMapper mapper = session.getMapper(HostMapper.class);
			List<Host> hosts = hostImplServiceImpl.selectHost(new Host());
			return hosts;

		} catch (Exception e) {

			e.printStackTrace();
			return e.toString();
		}

	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getUsers() {
		return createResponseEntity(toJson(), HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getUser(@PathVariable("userId") String userId) {
		Info info = model.get(userId);
		if (info != null) {
			return createResponseEntity(info.toJson(), HttpStatus.OK);
		} else {
			return createResponseEntity("", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/users/{userId}/{name}/{invention}", method = RequestMethod.PUT)
	public void putUser(@PathVariable("userId") String userId,
			@PathVariable("name") String name,
			@PathVariable("invention") String invention,
			HttpServletResponse httpServletResponse) {
		this.model.put(userId, new Info(name, invention));
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);
	}

	public ResponseEntity<String> createResponseEntity(String json,
			HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json + "\n", headers, status);
	}

	private String toJson() {
		StringBuffer json = new StringBuffer();
		boolean first = true;
		json.append("[");
		for (String name : this.model.keySet()) {
			if (first) {
				first = false;
			} else {
				json.append(", ");
			}
			json.append("/rest/users/" + name);
		}
		json.append("]");
		return json.toString();
	}

	private class Info {

		private String name;

		private String invention;

		Info(String name, String invention) {
			this.name = name;
			this.invention = invention;
		}

		String toJson() {
			return "{ \"name\" : \"" + this.name + "\", \"invention\" : \""
					+ this.invention + "\" }";
		}
	}
}
