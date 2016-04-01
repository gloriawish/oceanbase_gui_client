package com.ecnu.ob.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.ecnu.ob.gui.component.tab.QueryTabPanel.QueryTabType;

public class TabInfo {
	
	public TabInfo() {
		
	}

	//QueryTabPanel
	public QueryResult queryResult;
	
	public String defaultSql;
	
	public String dbName;
	
	public List<String> dbNameList;
	
	public QueryTabType queryTabType;
	
	public TabInfo(Vector<Column> headers, Vector<Vector<ColumnValue>> rows, boolean isEmptyRow,
			String defaultSql, String dbName, List<String> dbNameList, QueryTabType queryTabType) {
		queryResult = new QueryResult(headers, rows, isEmptyRow);
		this.defaultSql = defaultSql;
		this.dbName = dbName;
		if(dbNameList != null) {
			this.dbNameList = new ArrayList<String>();
			this.dbNameList.addAll(dbNameList);
		}
		this.queryTabType = queryTabType;
	}
	
	
	
	//ServerTabPanel
	public ClusterInfo clusterInfo;
	public ServerInfo serverInfo;
	
	//RsAdminTabPanel
	
}
