package com.ecnu.ob.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import com.ecnu.ob.model.ColumnValue;
import com.ecnu.ob.model.SqlType;
import com.ecnu.ob.model.ColumnValue.ColumnValueType;

public class CommonUtil {
	
	public static String SEPARATOR = System.getProperty("line.separator");

	public static Triple<Vector<String>, Vector<Vector<ColumnValue>>, Long> parseTable(ResultSet rs) throws SQLException {
		Vector<String> headers = new Vector<String>();
		Vector<Vector<ColumnValue>> rows = new Vector<Vector<ColumnValue>>();
		ResultSetMetaData rSetMetaData = rs.getMetaData(); 
		long rowCount = 0;
		int columnCount = rSetMetaData.getColumnCount();
		for (int i = 1; i <= columnCount; i++) { 
			String columnName = rSetMetaData.getColumnName(i);
			headers.add(columnName);
		} 
		while(rs.next()) {
			Vector<ColumnValue> row = new Vector<ColumnValue>();
			for (int i = 1; i <= columnCount; i++) { 
				Object column = rs.getObject(i);
				if(column == null) {
					ColumnValue cv = new ColumnValue(null);
					row.add(cv);
				} else {
					rSetMetaData.getColumnType(i);
					ColumnValue cv = new ColumnValue(column.toString(), rSetMetaData.getColumnType(i));
					row.add(cv);
				}
			}
			rows.add(row);
			rowCount++;
		}
		if(rows.size() == 0) {
			Vector<ColumnValue> emptyRow = new Vector<ColumnValue>();
			for (int i = 1; i <= columnCount; i++) { 
				emptyRow.add(new ColumnValue(null, ColumnValueType.EMPTY));
			}
			rows.add(emptyRow);
		}
		return new Triple<Vector<String>, Vector<Vector<ColumnValue>>, Long>(headers, rows, rowCount);
	}
	
	public static String subString(String str, String startTag, String endTag) {
		String subStr = null;
		if(str != null && str.length() > 0) {
			int startPos = str.indexOf(startTag);
			if(startPos >= 0) {
				startPos += startTag.length();
				int endPos = str.indexOf(endTag, startPos);
				if(endPos >= 0) {
					subStr = str.substring(startPos, endPos);
				}
			}
		}
		return subStr;
	}
	
	public static String getTableName(String sql, SqlType sqlType) {
		String tableName = null;
		if(sql != null) {
			sql = sql.toUpperCase() + " ";
			if(sqlType == SqlType.SELECT) {
				tableName = subString(sql, " FROM ", " ");
				if(tableName == null) {
					tableName = subString(sql, "*FROM ", " ");
				}
			}
		}
		return tableName;
	}
	
	public static String parseException(Exception e) {
		String exception = "Unexpected Error!";
		String[] exc = e.toString().split(":");
		if(exc.length > 0) {
			exception = exc[exc.length - 1].trim();
		}
		return exception;
	}
	
	public static void main(String args[]) {
		System.out.println(getTableName(" select *from a where", SqlType.SELECT));
	}
}
