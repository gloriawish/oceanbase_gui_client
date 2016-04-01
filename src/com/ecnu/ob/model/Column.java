package com.ecnu.ob.model;


public class Column {
	
	public String name;
	
	public String type;
	
	public int rowkey; //0 non-rowkey, 1, 2
	
	public Column() {
		this.name = "#";
		this.type = "&nbsp;";
		this.rowkey = 0;
	}
	
	public Column(String name, String type, int rowkey) {
		this.name = name;
		this.type = type;
		this.rowkey = rowkey;
	}
	
	public String toString() {
		String key = "";
		String cType = type;
		if(rowkey > 0) {
			key = " &lt;key " + rowkey + "&gt;";
		} else if(rowkey < 0) {
			key = " &lt;UNKOWN&gt";
		}
		if(type == null) {
			cType = "UNKOWN";
		}
		return "<html><center><b>" + name + "</b><br>" + cType + key + "</center></html>";
	}
	
}
