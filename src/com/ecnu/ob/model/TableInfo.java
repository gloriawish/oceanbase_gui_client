package com.ecnu.ob.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ecnu.ob.manager.DataBaseManager;

public class TableInfo {
	
	private String lmsIPPort;
	private String userName;
	private String userPass;
	
	private String gm_create;
	private String gm_modify;
	private String table_name;
	private int create_time_column_id;
	private int modify_time_column_id;
	private int table_id;
	private int table_type;
	private int load_type;
	private int table_def_type;
	private int rowkey_column_num;
	private int column_num;
	private int max_used_column_id;
	private int replica_num;
	private int create_mem_version;
	private int tablet_max_size;
	private int max_rowkey_length;
	private String compress_func_name;
	private int is_use_bloomfilter;
	private int merge_write_sstable_version;
	private int is_pure_update_table;
	private int rowkey_split;
	private String expire_condition;
	private int tablet_block_size;
	private int is_read_static;
	private int schema_version;
	private int data_table_id;
	private int index_status;
	private String db_name;
	private ResultSet result;
	
	private List<IndexInfo> indexList;
	
	public TableInfo() {
		indexList = new ArrayList<IndexInfo>();
	}
	
	public TableInfo(String lmsIPPort, String userName, String userPass){
		indexList = new ArrayList<IndexInfo>();
		this.lmsIPPort = lmsIPPort;
		this.userName = userName;
		this.userPass = userPass;
	}
	
	public String getLmsIPPort() {
		return lmsIPPort;
	}
	public void setLmsIPPort(String lmsIPPort) {
		this.lmsIPPort = lmsIPPort;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	/**
	 * 获取当前表的所有数据，每次调用拉取最新的，所以需要遍历的话，外部用一个变量存储信息
	 * @return
	 */
	public ResultSet getResult() {
		DataBaseManager.loadTableResult(this, lmsIPPort, userName, userPass);
		return result;
	}
	
	public List<IndexInfo> getIndexList() {
		indexList.clear();
		DataBaseManager.loadTableIndex(this, lmsIPPort, userName, userPass);
		return indexList;
	}
	
	public void addIndex(IndexInfo info) {
		indexList.add(info);
	}

	public void setResult(ResultSet result) {
		this.result = result;
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
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public int getCreate_time_column_id() {
		return create_time_column_id;
	}
	public void setCreate_time_column_id(int create_time_column_id) {
		this.create_time_column_id = create_time_column_id;
	}
	public int getModify_time_column_id() {
		return modify_time_column_id;
	}
	public void setModify_time_column_id(int modify_time_column_id) {
		this.modify_time_column_id = modify_time_column_id;
	}
	public int getTable_id() {
		return table_id;
	}
	public void setTable_id(int table_id) {
		this.table_id = table_id;
	}
	public int getTable_type() {
		return table_type;
	}
	public void setTable_type(int table_type) {
		this.table_type = table_type;
	}
	public int getLoad_type() {
		return load_type;
	}
	public void setLoad_type(int load_type) {
		this.load_type = load_type;
	}
	public int getTable_def_type() {
		return table_def_type;
	}
	public void setTable_def_type(int table_def_type) {
		this.table_def_type = table_def_type;
	}
	public int getRowkey_column_num() {
		return rowkey_column_num;
	}
	public void setRowkey_column_num(int rowkey_column_num) {
		this.rowkey_column_num = rowkey_column_num;
	}
	public int getColumn_num() {
		return column_num;
	}
	public void setColumn_num(int column_num) {
		this.column_num = column_num;
	}
	public int getMax_used_column_id() {
		return max_used_column_id;
	}
	public void setMax_used_column_id(int max_used_column_id) {
		this.max_used_column_id = max_used_column_id;
	}
	public int getReplica_num() {
		return replica_num;
	}
	public void setReplica_num(int replica_num) {
		this.replica_num = replica_num;
	}
	public int getCreate_mem_version() {
		return create_mem_version;
	}
	public void setCreate_mem_version(int create_mem_version) {
		this.create_mem_version = create_mem_version;
	}
	public int getTablet_max_size() {
		return tablet_max_size;
	}
	public void setTablet_max_size(int tablet_max_size) {
		this.tablet_max_size = tablet_max_size;
	}
	public int getMax_rowkey_length() {
		return max_rowkey_length;
	}
	public void setMax_rowkey_length(int max_rowkey_length) {
		this.max_rowkey_length = max_rowkey_length;
	}
	public String getCompress_func_name() {
		return compress_func_name;
	}
	public void setCompress_func_name(String compress_func_name) {
		this.compress_func_name = compress_func_name;
	}
	public int getIs_use_bloomfilter() {
		return is_use_bloomfilter;
	}
	public void setIs_use_bloomfilter(int is_use_bloomfilter) {
		this.is_use_bloomfilter = is_use_bloomfilter;
	}
	public int getMerge_write_sstable_version() {
		return merge_write_sstable_version;
	}
	public void setMerge_write_sstable_version(int merge_write_sstable_version) {
		this.merge_write_sstable_version = merge_write_sstable_version;
	}
	public int getIs_pure_update_table() {
		return is_pure_update_table;
	}
	public void setIs_pure_update_table(int is_pure_update_table) {
		this.is_pure_update_table = is_pure_update_table;
	}
	public int getRowkey_split() {
		return rowkey_split;
	}
	public void setRowkey_split(int rowkey_split) {
		this.rowkey_split = rowkey_split;
	}
	public String getExpire_condition() {
		return expire_condition;
	}
	public void setExpire_condition(String expire_condition) {
		this.expire_condition = expire_condition;
	}
	public int getTablet_block_size() {
		return tablet_block_size;
	}
	public void setTablet_block_size(int tablet_block_size) {
		this.tablet_block_size = tablet_block_size;
	}
	public int getIs_read_static() {
		return is_read_static;
	}
	public void setIs_read_static(int is_read_static) {
		this.is_read_static = is_read_static;
	}
	public int getSchema_version() {
		return schema_version;
	}
	public void setSchema_version(int schema_version) {
		this.schema_version = schema_version;
	}
	public int getData_table_id() {
		return data_table_id;
	}
	public void setData_table_id(int data_table_id) {
		this.data_table_id = data_table_id;
	}
	public int getIndex_status() {
		return index_status;
	}
	public void setIndex_status(int index_status) {
		this.index_status = index_status;
	}
	public String getDb_name() {
		return db_name;
	}
	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}
	
}
