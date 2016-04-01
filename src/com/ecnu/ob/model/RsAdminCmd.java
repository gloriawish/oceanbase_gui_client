package com.ecnu.ob.model;

public enum RsAdminCmd {

		NULL,
		//初始化系统表(只在系统初始建立的时候执行)
		BOOT_STARP,
		
		//设置rs角色(主rs启动时执行)
		SET_OBI_ROLE_MASTER,
		
		//切换备ups
		SET_OBI_ROLE_SLAVE,
		
		//切换主ups
		CHANGE_UPS_MASTER,
		
		//刷新schema信息
		REFRESH_SCHEMA,
		
		//清空rootserver的root table信息，强制cs重新汇报root table信息
		CLEAN_ROOT_TABLE,
		
		//小版本冻结
		//MINOR_FREEZE,
		
		//大版本冻结
		//MAJOR_FREEZE,
		
		//cs上创建空tablet(合并时，由于cs上缺少相应的tablet，导致合并不能完成时，在任意一个cs上执行下面的命令，之后重新汇报)
		//CREATE_TABLET,
		
		
		//---------dump系统信息，执行成功后，通过查看rootserver.log查看信息----------------
		
		//dump root table 信息
		DUMP_ROOT_TABLE,
		
		//dump server 信息
		DUMP_SERVER,
		
		//dump 异常的root table 信息
		DUMP_UNUSUAL_TABLETS,
		
		
		//----------查看信息--------------------------------
		
		//查看所有server
		ALL_SERVER,
		
		//查看合并状态
		MERGE,
		
		//查看合并版本
		FROZEN_VERSION,
		
}
