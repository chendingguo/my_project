package com.riseup.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class BeanUtil {
	public static Map<String, String> confMap;

	public static String buildString(Object obj) {
		StringBuilder builder = new StringBuilder();
		Method[] methods = obj.getClass().getMethods();
		builder.append("-----------------------------\n");
		for (Method method : methods) {
			if (method.getName().startsWith("get")) {
				try {
					builder.append(method.getName()).append(":")
							.append(method.invoke(obj)).append("\n");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		builder.append("-----------------------------\n");

		return builder.toString();

	}

	public static String covertTimestamp(Date date, String format) {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		String result = sd.format(date);
		return result;

	}

	/**
	 * 当前时间 加N天后的时间
	 * 
	 * @param args
	 * @return
	 */

	public static Date addDate(long millis, int days) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		calendar.add(Calendar.DAY_OF_MONTH, days);
	
		Date date = calendar.getTime();
		return date;

	}

	/**
	 * 算百分比
	 * 
	 * @param y
	 * @param z
	 * @return
	 */
	public static String getPercent(int y, int z) {
		String percent = "";
		double baiy = y * 1.0;
		double baiz = z * 1.0;
		double fen = baiy / baiz;

		DecimalFormat df1 = new DecimalFormat("##%"); // ##.00%
														// 百分比格式，后面不足2位的用0补齐
		percent = df1.format(fen);
		return percent;
	}

	/**
	 * get properties
	 * 
	 * @param key
	 */

	public String getProperties(String key) {
		Map<String, String> propsMap = new HashMap<String, String>();
		Properties properties = new Properties();
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("marketing-config.properties");

		try {
			properties.load(in);
			Set keySet = properties.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String name = (String) it.next();
				propsMap.put(name, properties.getProperty(name));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return propsMap.get(key);

	}

	/**
	 * differ days
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}
	
  
	
	
}
