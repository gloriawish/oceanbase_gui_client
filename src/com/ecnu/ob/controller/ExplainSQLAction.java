package com.ecnu.ob.controller;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;

import javax.swing.AbstractAction;

import com.ecnu.ob.gui.MainFrame;
import com.ecnu.ob.gui.ObContentPanel;
import com.ecnu.ob.gui.component.tab.QueryTabPanel;
import com.ecnu.ob.gui.component.tab.QueryTabPanel.QueryTabType;
import com.ecnu.ob.jdbc.Query;
import com.ecnu.ob.main.Main;
import com.ecnu.ob.model.QueryResult;
import com.ecnu.ob.model.SqlType;
import com.ecnu.ob.utils.CommonUtil;

@SuppressWarnings("serial")
public class ExplainSQLAction extends AbstractAction {

	private ObContentPanel contentPanel;
	
	public ExplainSQLAction(ObContentPanel contentPanel) {
		putValue(NAME, "Explain");
		putValue(SHORT_DESCRIPTION, "Explain SQL");
		this.contentPanel = contentPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String sql = getQueryTabPanel().getSql();
		if(sql != null && sql.length() > 0) {
			//do something
			if(sql.contains(";")){
				MainFrame.alert("Only support Single SQL!");
			}else {
				String result_store = null;
				boolean error = false;
				String dbname = getQueryTabPanel().getDbName();
				Query q = new Query(Main.LMSIPPORT, dbname, Main.USERNAME, Main.USERPASS);
				
				SqlType sqlType = SqlType.getSqlType(sql);
				if(sqlType != SqlType.EXPLAIN) {
					sql = "explain " + sql;
				} 
				try{
					ResultSet rs = q.executeQuery(sql, 3);
					while(rs.next())
					{
						result_store = rs.getString(1);
					}
				}catch (Exception e1){
					error = true;
					MainFrame.alert(CommonUtil.parseException(e1));
				} finally{
					q.close();
				}
				if(!error) {
					getQueryTabPanel().setBottomPanel(new QueryResult(result_store), QueryTabType.PLAN);
				}
			} 
		}
	}
	
	private QueryTabPanel getQueryTabPanel() {
		return (QueryTabPanel)contentPanel.getCurrentTabPanel();
	}

	public static void main(String[] args) {
		
	}
}
