package com.ecnu.ob.model;

import java.sql.Types;
import java.util.Date;

@SuppressWarnings("rawtypes")
public class ColumnValue implements Comparable {
	
	public enum ColumnValueType {
		NUKNOWN,
		EMPTY,
		
		INT,
		DOUBLE,
		DATETIME,
		BOOL,
		STRING,
	}
	
	private class Value {
		public long intValue;
		public double doubleValue;
		public Date dateValue;
		public Boolean booleanValue;
		public String stringValue;
	}
	
	private ColumnValueType dataType;
	
	private Value value;
	
	public ColumnValue() {
		value = new Value();
		dataType = ColumnValueType.NUKNOWN;
	}
	
	public ColumnValue(String data) {
		value = new Value();
		dataType = ColumnValueType.STRING;
		value.stringValue = data;
	}
	
	public ColumnValue(Object data, ColumnValueType dataType) {
		this.dataType = dataType;
		value = new Value();
		switch(dataType) {
		case INT:
			value.intValue = (Integer) data;
			break;
		case EMPTY:
			//do nothing
			break;
		default:
			value.stringValue = (String) data;	
			break;
		}
	}
	
	public ColumnValue(Object data, int type) {
		value = new Value();
		switch(type) {
		case Types.BIGINT:
		case Types.INTEGER:
		case Types.NUMERIC:
		case Types.TINYINT:
		case Types.SMALLINT:
			dataType = ColumnValueType.INT;
			if(data != null) {
				value.intValue = Long.parseLong((String) data);
			}
			break;
		case Types.DOUBLE:
		case Types.REAL:
		case Types.FLOAT:
			dataType = ColumnValueType.DOUBLE;
			if(data != null) {
				value.doubleValue = Double.parseDouble((String)data);
			}
			break;
		case Types.BOOLEAN:
			dataType = ColumnValueType.BOOL;
			if(data != null) {
				value.booleanValue = Boolean.parseBoolean((String)data);
			}
			break;
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
		default:
			dataType = ColumnValueType.STRING;
			value.stringValue = (String)data;
			break;
		}
	}
	
	@Override
	public int compareTo(Object object) {
		ColumnValue other = (ColumnValue)object;
		switch(dataType) {
		case INT:
			return value.intValue > other.value.intValue ? 1 : (value.intValue == other.value.intValue ? 0 : -1);
		case DOUBLE:
			return value.doubleValue > other.value.doubleValue ? 1 : (value.doubleValue == other.value.doubleValue ? 0 : -1); 
		case DATETIME:
			return value.dateValue.compareTo(other.value.dateValue);
		case BOOL:
			return value.booleanValue.compareTo(other.value.booleanValue);
		default:
			return value.stringValue.compareTo(other.value.stringValue);
		}
	}
	
	public String toString() {
		if(value != null) {
			switch(dataType) {
			case EMPTY:
				return null;
			case INT:
				return String.valueOf(value.intValue);
			case DOUBLE:
				return String.valueOf(value.doubleValue);
			case BOOL:
				return String.valueOf(value.booleanValue);
			case DATETIME:
			default:
				if(value.stringValue == null) {
					return "NULL";
				} else {
					return value.stringValue;
				}
			}
		}
		return null;
	}
	
}
