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
	
	protected String moduleNameFirstUpper;
	
	//首字母大写的表名
	protected String tableNameFirstUpper;
	
	public JavaCreator(String module,  String suffix, TableInfo tableInfo) {
		this.module = module;
		this.suffix = suffix;
		this.tableInfo = tableInfo;
		
		this.moduleNameFirstUpper = StringUtils.changeFirstToUpper(module.toLowerCase());
		this.tableNameFirstUpper = StringUtils.changeFirstToUpper(tableInfo.getTname());
	}
	
	public JavaCreator(String module, TypeConvertor typeConvertor, String suffix, TableInfo tableInfo) {
		this.module = module;
		this.suffix = suffix;
		this.tableInfo = tableInfo;
		this.typeConvertor = typeConvertor;
		
		this.moduleNameFirstUpper = StringUtils.changeFirstToUpper(module.toLowerCase());
		this.tableNameFirstUpper = StringUtils.changeFirstToUpper(tableInfo.getTname());
	}

	public void createJavaFile(){

		StringBuilder javaSrc = new StringBuilder();
		
		//声明所在包
		javaSrc.append("package com."+DBManager.conf.getCompanyName()+"."+DBManager.conf.getProjectName()+"."+ module.toLowerCase() +";\n\n");
		
		//导包
		importPackageSrcCreator(tableInfo,javaSrc);
		
		//类模块声明
		declareateModule(tableInfo,javaSrc);
		
		//整个类及其内部
		inclassSrc(tableInfo,javaSrc,typeConvertor);

		String src = javaSrc.toString();
		FileCreator.createFile(tableInfo, module, suffix, src);
	}

	/**
	 * 生成声明模块src的方法
	 * eg：@Controller  
	 * eg：@Service
	 * 
	 * @param tableInfo 表信息
	 * @param javaSrc 拼接src的StringBuffer
	 */
	public abstract void declareateModule(TableInfo tableInfo,StringBuilder javaSrc);
	
	/**
	 * 生成导入包的src的方法
	 * 
	 * @param tableInfo 表信息
	 * @param javaSrc 拼接src的StringBuffer
	 */
	public abstract void importPackageSrcCreator(TableInfo tableInfo,StringBuilder javaSrc);

	/**
	 * 生成类的src的方法
	 * 
	 * @param tableInfo 表信息
	 * @param javaSrc 拼接src的StringBuffer
	 */
	public abstract void inclassSrc(TableInfo tableInfo,StringBuilder javaSrc,TypeConvertor typeConvertor);

}
