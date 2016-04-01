package com.ecnu.ob.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ecnu.ob.jdbc.Connector;
import com.ecnu.ob.model.DataBaseInfo;
import com.ecnu.ob.model.TableInfo;

/**
 * 提供查询接口
 * @author Administrator
 *
 */
public class QueryInterface {

	private Connection connection;
	
	private String currentSql;
	
	private ResultSet result;
	
	private DataBaseInfo dbInfo;
	
	private TableInfo tbInfo;
	
	public QueryInterface(DataBaseInfo dbInfo) {
		this.dbInfo = dbInfo;
		connection = Connector.getJDBCConnection(dbInfo.getLmsIPPort(), dbInfo.getDb_name(), dbInfo.getUserName(), dbInfo.getUserPass());
	}
	public QueryInterface(TableInfo tbInfo) {
		this.tbInfo = tbInfo;
		connection = Connector.getJDBCConnection(tbInfo.getLmsIPPort(), tbInfo.getDb_name(), tbInfo.getUserName(), tbInfo.getUserPass());
	}
	public Connection getConnection() {
		return connection;
	}
	public String getCurrentSql() {
		return currentSql;
	}
	public ResultSet getResult() {
		return result;
	}
	public DataBaseInfo getDbInfo() {
		return dbInfo;
	}
	public TableInfo getTbInfo() {
		return tbInfo;
	}
	
	
	public boolean execute(String sql) throws Exception {
		this.currentSql = sql;
		try {
			Statement stmt = connection.createStatement();
			return stmt.execute(sql);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public int executeUpdate(String sql) throws Exception {
		this.currentSql = sql;
		try {
			Statement stmt = connection.createStatement();
			return stmt.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	
	public ResultSet executeQuery(String sql) throws Exception {
		this.currentSql = sql;
		try {
			Statement stmt = connection.createStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
}
