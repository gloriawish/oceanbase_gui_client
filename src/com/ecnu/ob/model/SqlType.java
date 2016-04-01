package com.ecnu.ob.model;

public enum SqlType {
	UNKNOWN,
	
	SELECT,
	
	DESC, 
	DESCRIBE,
	
	SHOW,
	
	EXPLAIN,
	
	CREATE,  // create  => other
	ALTER,   // alter   => other
	DROP,    // drop    => other
	INSERT,  // insert  => other
	REPLACE, // replace => other
	UPDATE,  // update  => other
	DELETE,  // delete  => other
	OTHER; 
	
	public static SqlType getSqlType(String sql) {
		SqlType type = UNKNOWN;
		sql = sql.trim();
		String[] sqls = sql.split(" ");
		if(sqls.length > 1) {
			try {
				type = SqlType.valueOf(sqls[0].trim().toUpperCase());
				if(type == CREATE || type == ALTER || type == DROP || 
				   type == INSERT || type == REPLACE || type == UPDATE || 
				   type == DELETE) {
					type = OTHER;
				}
			} catch (Exception e) {
				//do nothing
			}
		}
		return type;
	}
	public static void main(String[] args) {
		System.out.println(SqlType.getSqlType(" e select 2"));
	}
}
