package com.ecnu.ob.model;

public class ObConstant {

	public static String[] SYSTEM_LIST = new String[]{
			"__first_tablet_entry",
			"__all_all_column",
			"__all_join_info",
			"__all_sys_param",
			"__all_sys_stat",
			"__all_user",
			"__all_table_privilege",
			"__all_cluster",
			"__all_server",
			"__all_trigger_event",
			"__all_sys_config",
			"__all_sys_config_stat",
			"__all_client",
			"__all_ddl_operation",
			"__all_database",
			"__all_database_privilege",
			"__all_sequence",
			"__all_server_stat",
			"__all_server_session",
			"__all_statement",
			"__all_cchecksum_info",
			"__index_process_info"
	};
	
	public static String SELECT_ALL_CLUSTER = "SELECT * FROM __all_cluster";
	
	public static String SELECT_ALL_SERVER = "SELECT * FROM __all_server";
	
	public static String SELECT_CLUSTER_ALL_SERVER = "SELECT * FROM __all_server WHERE cluster_id=%s";
	
	public static String SELECT_ALL_DATABSE = "SELECT * FROM __all_database";
	
	public static String SELECT_SYSTEM_TABLE = "SELECT * FROM __first_tablet_entry WHERE DB_NAME=''";
	
	public static String SELECT_SEQUENCE = "SELECT * FROM __all_sequence";
	
	public static String SELECT_TABLE_BY_DBNAME = "SELECT * FROM __first_tablet_entry WHERE INDEX_STATUS = 2 AND DATA_TABLE_ID = -1 AND DB_NAME='%s'";
	
	public static String SELECT_TABLE_RESULT = "SELECT * FROM %s";
	
	public static String SELECT_TABLE_INDEX = "SELECT * FROM __first_tablet_entry WHERE db_name = '%s' AND data_table_id = %s";
	
	
	//rs_admin 命令常量
	
	//初始化系统表(只在系统初始建立的时候执行)
	public static String BOOT_STARP = "rs_admin -r <rootserver_ip> -p <rootserver_port> boot_strap";
	
	//设置rs角色(主rs启动时执行)
	public static String SET_OBI_ROLE_MASTER = "rs_admin -r <rootserver_ip> -p <rootserver_port> set_obi_role -o OBI_SLAVE";
	
	//切换备ups
	public static String SET_OBI_ROLE_SLAVE = "rs_admin -r <rootserver_ip> -p <rootserver_port> set_obi_role -o OBI_MASTER";
	
	//切换主ups
	public static String CHANGE_UPS_MASTER = "rs_admin -r <rootserver_ip> -p <rootserver_port> change_ups_master -o ups_ip=<ups_ip>,ups_port=<ups_port>[,force]";
	
	//刷新schema信息
	public static String REFRESH_SCHEMA = "rs_admin -r <rootserver_ip> -p <rootserver_port> refresh_schema";
	
	//清空rootserver的root table信息，强制cs重新汇报root table信息
	public static String CLEAN_ROOT_TABLE = "rs_admin -r <rootserver_ip> -p <rootserver_port> clean_root_table";
	
	//小版本冻结
	//public static String MINOR_FREEZE = "ups_admin -a <updateserver_ip> -p <updateserver_port> -t minor_freeze";
	
	//大版本冻结
	//public static String MAJOR_FREEZE = "ups_admin -a <updateserver_ip> -p <updateserver_port> -t major_freeze";
	
	//cs上创建空tablet(合并时，由于cs上缺少相应的tablet，导致合并不能完成时，在任意一个cs上执行下面的命令，之后重新汇报)
	//public static String CREATE_TABLET = "cs_admin -s  <chunkserver_ip> -p <chunkserver_port> -i \"create_tablet table range range_format last_frozen_version\"";
	
	
	//---------dump系统信息，执行成功后，通过查看rootserver.log查看信息----------------
	
	//dump root table 信息
	public static String DUMP_ROOT_TABLE = "rs_admin -r <rootserver_ip> -p <rootserver_port> dump_root_table";
	
	//dump server 信息
	public static String DUMP_SERVER = "rs_admin -r <rootserver_ip> -p <rootserver_port> dump_server_info";
	
	//dump 异常的root table 信息
	public static String DUMP_UNUSUAL_TABLETS = "rs_admin -r <rootserver_ip> -p <rootserver_port> dump_unusual_tablets";
	
	
	//----------查看信息--------------------------------
	
	//查看所有server
	public static String ALL_SERVER = "rs_admin -r <rootserver_ip> -p <rootserver_port> stat -o all_server";
	
	//查看合并状态
	public static String MERGE = "rs_admin -r <rootserver_ip> -p <rootserver_port> stat -o merge";
	
	//查看合并版本
	public static String FROZEN_VERSION = "rs_admin -r <rootserver_ip> -p <rootserver_port> stat -o frozen_version";
	
	
	
	//-------------CS_ADMIN--------------------------------
	
	//显示磁盘信息
	public static String SHOW_DISK = "cs_admin -s <chunkserver_ip> -p <chunkserver_port>  -i \"show_disk\"";
	
	
	
}
