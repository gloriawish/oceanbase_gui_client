package com.ecnu.ob.model;

import java.util.Vector;

public class QueryResult {

	public Vector<Column> headers;
	
	public Vector<String> headerNames;
	
	public Vector<Vector<ColumnValue>> rows;
	
	public boolean isEmptyRow = false;
	
	public String text;
	
	public QueryResult() {
	}
	
	public QueryResult(Vector<Column> headers, Vector<Vector<ColumnValue>> rows, boolean isEmptyRow) {
		if(headers != null) {
			this.headers = new Vector<Column>();
			this.headers.addAll(headers);
		}
		if(rows != null) {
			this.rows = new Vector<Vector<ColumnValue>>();
			this.rows.addAll(rows);
		}
		this.isEmptyRow = isEmptyRow;
	}
	
	public QueryResult(Vector<Column> headers, Vector<String> headerNames, Vector<Vector<ColumnValue>> rows, boolean isEmptyRow) {
		this(headers, rows, isEmptyRow);
		if(headerNames != null) {
			this.headerNames = new Vector<String>();
			this.headerNames.addAll(headerNames);
		}
	}
	
	public QueryResult(String text) {
		this.text = text;
	}
	
	public QueryResult(Vector<Column> headers, Vector<String> headerNames, Vector<Vector<ColumnValue>> rows, boolean isEmptyRow, String text) {
		this(headers, headerNames, rows, isEmptyRow);
		this.text = text;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("QueryResult:");
		if(headers != null) {
			for(Column c: headers) {
				sb.append(c.name);
			}
		}
		if(headerNames != null) {
			for(String s: headerNames) {
				sb.append(s);
			}
		}
		return sb.toString();
	}
}
