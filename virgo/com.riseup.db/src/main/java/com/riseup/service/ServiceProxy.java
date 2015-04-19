package com.riseup.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ServiceProxy  {
	

	private final String[] springConfigFiles = {"classpath:applicationContext_host.xml" };

	private ClassPathXmlApplicationContext applicationContext;

	private boolean started;

	private ServiceProxy() {
		start();
	}

	private static volatile ServiceProxy _instance = null;

	public static final ServiceProxy getInstance() {
		if (null == _instance) {
			_instance = new ServiceProxy();
		}
		return _instance;
	}

	private void init() throws IOException {
		
		try {
			this.applicationContext = new ClassPathXmlApplicationContext(
					springConfigFiles);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	public  <T> T getService(String serviceName)
			throws Exception {
	
		if (applicationContext == null) {
			
			return null;
		}
		Object serviceBean = applicationContext.getBean(serviceName);
		
		return (T)serviceBean;
	}

	public int getServicesCount() {
		if (applicationContext == null) {
			
			return 0;
		}
		return applicationContext.getBeanDefinitionCount();
	}

	public String[] getServiceNames() {
		if (applicationContext == null) {
			
			return null;
		}
		return applicationContext.getBeanDefinitionNames();
	}

	/**
	 * NOTE: package access
	 */
	public synchronized void start() {
		if (started) {
			return;
		}
		
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ServiceRegistry start error.: ");
		}
		started = true;
	}

	/**
	 * NOTE: package access
	 */
	public void stop() {
		
	}

	public String[] getServiceNamesByType(Class<?> clazz) {
		if (applicationContext == null) {
			
			return null;
		}
		return applicationContext.getBeanNamesForType(clazz);
	}

	public Map<String, Object> getServicesByType(Class<?> clazz)
			 {
		if (applicationContext == null) {
			
			return null;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> services = (Map<String, Object>) applicationContext.getBeansOfType(clazz);
		if ((null == services) || (services.isEmpty())) {
			
			System.err.print("no Service find");
		}
		return services;
	}
}