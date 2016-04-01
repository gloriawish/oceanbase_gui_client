package com.ecnu.ob.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecnu.ob.jdbc.Connector;
import com.ecnu.ob.model.DataBaseInfo;
import com.ecnu.ob.model.IndexInfo;
import com.ecnu.ob.model.ObConstant;
import com.ecnu.ob.model.SequenceInfo;
import com.ecnu.ob.model.TableInfo;

public class DataBaseManager {

	
	/**
	 * 加载这个数据库的表信息
	 * @param dbInfo
	 * @param lmsServerIPPort
	 * @param userName
	 * @param userPass
	 */
	public static void loadDatabaseTables(DataBaseInfo dbInfo, String lmsServerIPPort, String userName, String userPass) {
		
		Connection conn = Connector.getJDBCConnection(lmsServerIPPort, "", userName, userPass);
		if(conn != null) {
			try {
				Statement stmt = conn.createStatement();
				String sql = String.format(ObConstant.SELECT_TABLE_BY_DBNAME, dbInfo.getDb_name());
				ResultSet set = stmt.executeQuery(sql);
				
				while(set.next()) {
					TableInfo tinfo = new TableInfo(lmsServerIPPort,userName,userPass);
					tinfo.setGm_create(set.getString("gm_create"));
					tinfo.setGm_modify(set.getString("gm_modify"));
					tinfo.setTable_name(set.getString("table_name"));
					tinfo.setCreate_time_column_id(set.getInt("Create_time_column_id"));
					tinfo.setModify_time_column_id(set.getInt("modify_time_column_id"));
					tinfo.setTable_id(set.getInt("table_id"));
					tinfo.setTable_type(set.getInt("table_type"));
					tinfo.setLoad_type(set.getInt("load_type"));
					tinfo.setTable_def_type(set.getInt("table_def_type"));
					tinfo.setRowkey_column_num(set.getInt("rowkey_column_num"));
					tinfo.setColumn_num(set.getInt("column_num"));
					tinfo.setMax_used_column_id(set.getInt("max_used_column_id"));
					tinfo.setReplica_num(set.getInt("replica_num"));
					tinfo.setCreate_mem_version(set.getInt("create_mem_version"));
					tinfo.setTablet_max_size(set.getInt("tablet_max_size"));
					tinfo.setMax_rowkey_length(set.getInt("max_rowkey_length"));
					tinfo.setCompress_func_name(set.getString("compress_func_name"));
					tinfo.setIs_use_bloomfilter(set.getInt("is_use_bloomfilter"));
					tinfo.setMerge_write_sstable_version(set.getInt("merge_write_sstable_version"));
					tinfo.setIs_pure_update_table(set.getInt("is_pure_update_table"));
					tinfo.setRowkey_split(set.getInt("rowkey_split"));
					tinfo.setExpire_condition(set.getString("expire_condition"));
					tinfo.setTablet_block_size(set.getInt("tablet_block_size"));
					tinfo.setIs_read_static(set.getInt("is_read_static"));
					tinfo.setSchema_version(set.getInt("schema_version"));
					tinfo.setData_table_id(set.getInt("data_table_id"));
					tinfo.setIndex_status(set.getInt("index_status"));
					tinfo.setDb_name(set.getString("db_name"));
					
					dbInfo.addTable(tinfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
	}
	
	/**
	 * 加载该表的结果集数据
	 * @param tbInfo
	 * @param lmsServerIPPort
	 * @param userName
	 * @param userPass
	 */
	public static void loadTableResult(TableInfo tbInfo, String lmsServerIPPort, String userName, String userPass) {
		
		Connection conn = Connector.getJDBCConnection(lmsServerIPPort, tbInfo.getDb_name(), userName, userPass);
		if(conn != null) {
			try {
				Statement stmt = conn.createStatement();
				String sql = String.format(ObConstant.SELECT_TABLE_RESULT, tbInfo.getTable_name());
				ResultSet result = stmt.executeQuery(sql);
				tbInfo.setResult(result);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	
	
	public static void loadTableIndex(TableInfo tbInfo, String lmsServerIPPort, String userName, String userPass) {
		
		Connection conn = Connector.getJDBCConnection(lmsServerIPPort, tbInfo.getDb_name(), userName, userPass);
		if(conn != null) {
			try {
				Statement stmt = conn.createStatement();
				String sql = String.format(ObConstant.SELECT_TABLE_INDEX, tbInfo.getDb_name(),tbInfo.getTable_id());
				ResultSet result = stmt.executeQuery(sql);
				while(result.next()) {
					IndexInfo iInfo = new IndexInfo();
					iInfo.setDbName(tbInfo.getDb_name());
					iInfo.setIndexName(result.getString("table_name"));
					iInfo.setStatus(result.getInt("index_status"));
					tbInfo.addIndex(iInfo);
				}
				result.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	
	/**
	 * 获取所有的系统表信息
	 * @param lmsServerIPPort
	 * @param userName
	 * @param userPass
	 * @return
	 */
	public static List<TableInfo> getAllSystemTable(String lmsServerIPPort, String userName, String userPass) {
		
		List<TableInfo> tableInfos = new ArrayList<TableInfo>();
		Connection conn = Connector.getJDBCConnection(lmsServerIPPort, "", userName, userPass);
		if(conn != null) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet set = stmt.executeQuery(ObConstant.SELECT_SYSTEM_TABLE);
				
				while(set.next()) {
					TableInfo tinfo = new TableInfo();
					tinfo.setGm_create(set.getString("gm_create"));
					tinfo.setGm_modify(set.getString("gm_modify"));
					tinfo.setTable_name(set.getString("table_name"));
					tinfo.setCreate_time_column_id(set.getInt("Create_time_column_id"));
					tinfo.setModify_time_column_id(set.getInt("modify_time_column_id"));
					tinfo.setTable_id(set.getInt("table_id"));
					tinfo.setTable_type(set.getInt("table_type"));
					tinfo.setLoad_type(set.getInt("load_type"));
					tinfo.setTable_def_type(set.getInt("table_def_type"));
					tinfo.setRowkey_column_num(set.getInt("rowkey_column_num"));
					tinfo.setColumn_num(set.getInt("column_num"));
					tinfo.setMax_used_column_id(set.getInt("max_used_column_id"));
					tinfo.setReplica_num(set.getInt("replica_num"));
					tinfo.setCreate_mem_version(set.getInt("create_mem_version"));
					tinfo.setTablet_max_size(set.getInt("tablet_max_size"));
					tinfo.setMax_rowkey_length(set.getInt("max_rowkey_length"));
					tinfo.setCompress_func_name(set.getString("compress_func_name"));
					tinfo.setIs_use_bloomfilter(set.getInt("is_use_bloomfilter"));
					tinfo.setMerge_write_sstable_version(set.getInt("merge_write_sstable_version"));
					tinfo.setIs_pure_update_table(set.getInt("is_pure_update_table"));
					tinfo.setRowkey_split(set.getInt("rowkey_split"));
					tinfo.setExpire_condition(set.getString("expire_condition"));
					tinfo.setTablet_block_size(set.getInt("tablet_block_size"));
					tinfo.setIs_read_static(set.getInt("is_read_static"));
					tinfo.setSchema_version(set.getInt("schema_version"));
					tinfo.setData_table_id(set.getInt("data_table_id"));
					tinfo.setIndex_status(set.getInt("index_status"));
					tinfo.setDb_name(set.getString("db_name"));
					
					tableInfos.add(tinfo);
				}
				set.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}
		
		
		for (int i = 0; i < ObConstant.SYSTEM_LIST.length; i++) {
			boolean find = false;
			for (TableInfo tableInfo : tableInfos) {
				if(tableInfo.getTable_name().equals(ObConstant.SYSTEM_LIST[i])) {
					find = true;
					break;
				}
			}
			//没有找到系统表
			if(!find) {
				TableInfo newTableInfo = new TableInfo();
				newTableInfo.setTable_name(ObConstant.SYSTEM_LIST[i]);
				tableInfos.add(newTableInfo);
			}
		}
		
		return tableInfos;
		
	}
	
public static List<SequenceInfo> getAllSequence(String lmsServerIPPort, String userName, String userPass) {
		
		List<SequenceInfo> sequenceInfos = new ArrayList<SequenceInfo>();
		Connection conn = Connector.getJDBCConnection(lmsServerIPPort, "", userName, userPass);
		if(conn != null) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet set = stmt.executeQuery(ObConstant.SELECT_SEQUENCE);
				
				while(set.next()) {
					SequenceInfo sInfo = new SequenceInfo();
					
					sInfo.setSequence_name(set.getString("sequence_name"));
					sInfo.setData_type(set.getInt("data_type"));
					sInfo.setCurrent_value(set.getString("current_value"));
					sInfo.setIncrement_by(set.getString("increment_by"));
					sInfo.setMin_value(set.getString("min_value"));
					sInfo.setMax_value(set.getString("max_value"));
					sInfo.setIs_cycle(set.getInt("is_cycle"));
					sInfo.setCache_num(set.getInt("cache_num"));
					sInfo.setIs_order(set.getInt("is_order"));
					sInfo.setIs_valid(set.getInt("is_valid"));
					sInfo.setConst_start_with(set.getString("const_start_with"));
					sInfo.setCan_use_prevval(set.getInt("can_use_prevval"));
					sInfo.setUse_quick_path(set.getInt("use_quick_path"));
					
					sequenceInfos.add(sInfo);
					
				}
				set.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}
		return sequenceInfos;
		
	}
	
}
