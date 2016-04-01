package com.ecnu.ob.controller;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractAction;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.gui.ObContentPanel;
import com.ecnu.ob.gui.component.tab.QueryTabPanel;
import com.ecnu.ob.gui.component.tab.QueryTabPanel.QueryTabType;
import com.ecnu.ob.jdbc.Query;
import com.ecnu.ob.main.Main;
import com.ecnu.ob.model.Column;
import com.ecnu.ob.model.ColumnValue;
import com.ecnu.ob.model.QueryResult;
import com.ecnu.ob.model.SqlType;
import com.ecnu.ob.utils.CommonUtil;
import com.ecnu.ob.utils.Triple;

@SuppressWarnings("serial")
public class ExecuteSQLAction extends AbstractAction {

	private ObContentPanel contentPanel;
	
	public ExecuteSQLAction(ObContentPanel contentPanel) {
		putValue(NAME, "Execute");
		putValue(SHORT_DESCRIPTION, "Execute SQL");
		this.contentPanel = contentPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String sql = getQueryTabPanel().getSql();
		if(sql != null && sql.length() > 0) {
			if(sql.contains(";")) {
				MainFrame.alert("Only support Single SQL!");
			} else {
				boolean error = false;
				int timeout = getQueryTabPanel().getTimeout();
				String dbname = getQueryTabPanel().getDbName();
				Query q = new Query(Main.LMSIPPORT, dbname, Main.USERNAME, Main.USERPASS);
				
				SqlType sqlType = SqlType.getSqlType(sql);
				if(sqlType == SqlType.UNKNOWN) {                 // unknown
					MainFrame.alert("Not supported SQL!");
				} else if(sqlType == SqlType.SELECT) {            // select
					String tableName = CommonUtil.getTableName(sql, sqlType);
					
					double timeUsed = 0.0f;
					long rowCount = 0;
					
					Vector<Column> headers = new Vector<Column>();
					Map<String, Column> headerMap = new HashMap<String, Column>();
					Vector<String> headerNames = new Vector<String>();
					
					Vector<Vector<ColumnValue>> rows = new Vector<Vector<ColumnValue>>();
					try {
						long startTime = System.currentTimeMillis();
						ResultSet rs = q.executeQuery(sql, timeout);
						timeUsed = ((double)(System.currentTimeMillis() - startTime))/1000;
						Triple<Vector<String>, Vector<Vector<ColumnValue>>, Long> data = CommonUtil.parseTable(rs);
						
						//Get column TYPE and ROWKEY
						if(tableName != null && tableName.length() > 0) {
							try {
								ResultSet headerRs = q.executeQuery("desc " + tableName, 3);
								while(headerRs.next()) {
									String cName = headerRs.getString(1);
									String cType = headerRs.getString(2);
									int cRowkey = headerRs.getInt(4);
									headerMap.put(cName, new Column(cName, cType, cRowkey));
								}
							} catch (Exception e1) {
								//do nothing
							}
						}
						headerNames.addAll(data.first);
						rows.addAll(data.second);
						rowCount = data.third;
						
						for(String name: headerNames) {
							Column column = headerMap.get(name);
							if(column == null) {
								headers.add(new Column(name, null ,-1));
							} else {
								headers.add(column);
							}
						}
					} catch (Exception e1) {
						error = true;
						if(MainFrame.debug) {
							e1.printStackTrace();
						}
						MainFrame.alert(CommonUtil.parseException(e1));
					} finally {
						q.close();
					}
					if(!error) {
						getQueryTabPanel().setBottomPanel(new QueryResult(headers, rows, (rowCount == 0)), QueryTabType.TABLE);
						getQueryTabPanel().setResultLabel(" " + rowCount + " rows in set (" + timeUsed + " sec)");
					}
				}  else if(sqlType == SqlType.DESC || sqlType == SqlType.DESCRIBE
						|| sqlType == SqlType.SHOW){  // desc
					Vector<String> headerNames = new Vector<String>();
					Vector<Vector<ColumnValue>> rows = new Vector<Vector<ColumnValue>>();
					long rowCount = 0;
					try {
						ResultSet rs = q.executeQuery(sql, timeout);
						Triple<Vector<String>, Vector<Vector<ColumnValue>>, Long> data = CommonUtil.parseTable(rs);
						headerNames.addAll(data.first);
						rows.addAll(data.second);
						rowCount = data.third;
					} catch (Exception e1) {
						error = true;
						if(MainFrame.debug) {
							e1.printStackTrace();
						}
						MainFrame.alert(CommonUtil.parseException(e1));
					} finally {
						q.close();
					}
					if(!error) {
						getQueryTabPanel().setBottomPanel(new QueryResult(null, headerNames, rows, (rowCount == 0)), QueryTabType.TABLE);
					}
				} else if(sqlType == SqlType.OTHER) {      // other
					String result = "timeout!";
					try {
						int ret  = q.execute(sql, timeout);
						result = "Query OK, " + ret + " rows affected.";
					} catch (Exception e1) {
						error = true;
						if(MainFrame.debug) {
							e1.printStackTrace();
						}
						MainFrame.alert(CommonUtil.parseException(e1));
					} finally {
						q.close();
					}
					if(!error) {
						getQueryTabPanel().setBottomPanel(new QueryResult(), QueryTabType.NONE);
						getQueryTabPanel().setResultLabel(" " + result);
					}
				} else if(sqlType == SqlType.EXPLAIN) {
					MainFrame.alert("Please use [Explain] button!");
				}
			}
		}
	}
	
	private QueryTabPanel getQueryTabPanel() {
		return (QueryTabPanel)contentPanel.getCurrentTabPanel();
	}
	
}
