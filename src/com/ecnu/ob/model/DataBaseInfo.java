package com.ecnu.ob.model;

import java.util.ArrayList;
import java.util.List;

import com.ecnu.ob.manager.DataBaseManager;

/**
 * 数据库信息
 * @author Administrator
 *
 */
public class DataBaseInfo {
	
	private String lmsIPPort;
	private String userName;
	private String userPass;
	
	private String gm_create;
	private String gm_modify;
	private int db_id;
	private int stat;
	private String db_name;
	private List<TableInfo> tableList;
	
	
	public DataBaseInfo(){
		tableList = new ArrayList<TableInfo>();
	}
	
	public DataBaseInfo(String lmsIPPort, String userName, String userPass){
		tableList = new ArrayList<TableInfo>();
		this.lmsIPPort = lmsIPPort;
		this.userName = userName;
		this.userPass = userPass;
	}
	
	public String getLmsIPPort() {
		return lmsIPPort;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPass() {
		return userPass;
	}
	
	public String getGm_create() {
		return gm_create;
	}

	public void setGm_create(String gm_create) {
		this.gm_create = gm_create;
	}

	public String getGm_modify() {
		return gm_modify;
	}

	public void setGm_modify(String gm_modify) {
		this.gm_modify = gm_modify;
	}

	public int getDb_id() {
		return db_id;
	}

	public void setDb_id(int db_id) {
		this.db_id = db_id;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public String getDb_name() {
		return db_name;
	}
	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}
	
	public void addTable(TableInfo table)
	{
		tableList.add(table);
	}
	/**
	 * 获取当前表中的数据，每次调用都是拉取最新的
	 * @return
	 */
	public List<TableInfo> getTableList() {
		tableList.clear();
		DataBaseManager.loadDatabaseTables(this, lmsIPPort, userName, userPass);
		return tableList;
	}
}
