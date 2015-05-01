package com.airsupply.olap.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class MockDataSourceService {

	

	private static MockDataSourceService instance;
	private static final String key = "key";

	private MockDataSourceService() {
	}

	public static MockDataSourceService getInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new MockDataSourceService();
				}
				return instance;
			}
		}
		return instance;
	}
	
	public static DataSource getDataSource(String dataSourceId){
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl("jdbc:mysql://localhost:3306/"+dataSourceId);
		bds.setUsername("root");
		bds.setPassword("123456");
		bds.setMinIdle(3);// 等待的连接个数
		bds.setMaxActive(6);// 最大提供的连接个数
		bds.setMaxWait(50000);// 等待连接的最长时间
		DataSource ds = bds;
		return ds;
		
	}



	public static void close(Connection conn, PreparedStatement pstm,
			ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}