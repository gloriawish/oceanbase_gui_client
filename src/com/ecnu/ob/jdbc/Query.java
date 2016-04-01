package com.ecnu.ob.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Query {

	private Connection conn = null;
	
	private Statement stmt = null;
	
	ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public Query(String ipPort, String dbName, String user, String password) {
		conn = Connector.getJDBCConnection(ipPort, dbName, user, password);
		try {
			if(conn != null) {
				stmt = conn.createStatement();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public int execute(final String sql, int timeout)  throws Exception {
		int ret = -1;
		if(null != stmt) {
			Callable<Integer> call = new Callable<Integer>() {
				public Integer call() throws Exception {
					return stmt.executeUpdate(sql);
				}
			};
			Future<Integer> future = executor.submit(call);
			if(timeout >= 0) {
				ret = future.get(timeout * 1000, TimeUnit.MILLISECONDS);
			} else {
				ret = future.get();
			}
		} else {
		}
		return ret;
	}
	
	/**
	 * @param sql
	 * @param timeout s
	 * @return
	 * @throws Exception 
	 */
	public ResultSet executeQuery(final String sql, int timeout) throws Exception {
		Callable<ResultSet> call = new Callable<ResultSet>() {
			public ResultSet call() throws Exception {
				return stmt.executeQuery(sql);
			}
		};
		Future<ResultSet> future = executor.submit(call);
		ResultSet rs;
		if(timeout >= 0) {
			rs = future.get(timeout * 1000, TimeUnit.MILLISECONDS);
		} else {
			rs = future.get();
		}
		return rs;
	}
	
	public void close() {
		try{
			if(null != stmt) {
				stmt.close();
			}
			if(null != conn) {
				//conn.close();
			}
			if(null != executor) {
				executor.shutdownNow();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public static void main(String[] args) {
		
	}
}
