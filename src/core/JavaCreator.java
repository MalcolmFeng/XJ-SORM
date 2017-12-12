package core;

import bean.TableInfo;
import utils.StringUtils;

/**
 * 封装了java文件（源代码）常用的操作。
 * @author Malcolm
 *
 */
public abstract class JavaCreator {
	
	protected String module;
	protected String suffix;
	protected TableInfo tableInfo;
	protected TypeConvertor typeConvertor;
	
	protected String tableNameFirstUpper;
	
	public JavaCreator(String module, TypeConvertor typeConvertor, String suffix, TableInfo tableInfo) {
		this.module = module;
		this.suffix = suffix;
		this.tableInfo = tableInfo;
		this.typeConvertor = typeConvertor;
		
		this.tableNameFirstUpper = StringUtils.changeFirstToUpper(tableInfo.getTname().toLowerCase());
	}

	public void createJavaFile(){

		StringBuilder javaSrc = new StringBuilder();
		
		//声明所在包
		javaSrc.append("package com."+DBManager.conf.getCompanyName()+"."+DBManager.conf.getProjectName()+"."+ module.toLowerCase() +";\n\n");
		
		//导入所需jar包
		importPackageSrcCreator(tableInfo,javaSrc);
		
		//类的声明
		declareateModule(tableInfo,javaSrc);
		
		//类
		inclassSrc(tableInfo,javaSrc,typeConvertor);

		

		String src = javaSrc.toString();
		
		FileCreator.createFile(tableInfo, module, suffix, src);
	}

	public abstract void declareateModule(TableInfo tableInfo,StringBuilder javaSrc);
	
	public abstract void importPackageSrcCreator(TableInfo tableInfo,StringBuilder javaSrc);

	public abstract void inclassSrc(TableInfo tableInfo,StringBuilder javaSrc,TypeConvertor typeConvertor);

}
