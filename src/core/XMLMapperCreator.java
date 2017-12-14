package core;

import java.util.Map;

import bean.ColumnInfo;
import bean.TableInfo;
import utils.StringUtils;
/**
 * 生成XML Mapper配置文件
 * @author Malcolm
 *
 */
public abstract class XMLMapperCreator {
	
	protected String tableName;
	protected TableInfo tableInfo;
	protected Boolean add;
	protected Boolean update;
	protected Boolean queryRows;
	protected Boolean queryList;
	protected Boolean queryAll;
	protected Boolean delete;
	protected XMLMapperCreator xmlMapperCreator;
	
	public XMLMapperCreator() {
		this.add = true;
		this.update = true;
		this.queryRows = true;
		this.queryList = true;
		this.queryAll = true;
		this.delete = true;
	}
	
	
	public XMLMapperCreator(Boolean add,Boolean update,Boolean queryRows,Boolean queryAll,Boolean queryList,Boolean delete) {
		this.add = add;
		this.update = update;
		this.queryRows = queryRows;
		this.queryList = queryList;
		this.queryAll = queryAll;
		this.delete = delete;
	}
	
	public XMLMapperCreator(TableInfo tableInfo,Boolean add,Boolean update,Boolean queryRows,Boolean queryAll,Boolean queryList,Boolean delete) {
		this(add,update,queryRows,queryAll,queryList,delete);
		this.tableInfo = tableInfo;
		this.tableName=tableInfo.getTname();
	}
	
	public XMLMapperCreator(TableInfo tableInfo,XMLMapperCreator xmlMapperCreator) {
		this.tableInfo = tableInfo;
		this.xmlMapperCreator = xmlMapperCreator;
		
		this.add = xmlMapperCreator.add;
		this.update = xmlMapperCreator.update;
		this.queryRows = xmlMapperCreator.queryRows;
		this.queryList = xmlMapperCreator.queryList;
		this.queryAll = xmlMapperCreator.queryAll;
		this.delete = xmlMapperCreator.delete;
		
		this.tableName=tableInfo.getTname();
	}
	
	
	/**
	 * 生成Mapper的头
	 * @return
	 */
	public String createXMLHeaderSrc() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sBuffer.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		sBuffer.append("<mapper namespace=\"com."+ DBManager.conf.getCompanyName() +"."+ DBManager.conf.getProjectName() +".dao.I"+ StringUtils.changeFirstToUpper(tableInfo.getTname()) +"Dao\">\n");
		return sBuffer.toString();
	}
	
	/**
	 * 生成Mapper的尾
	 * @return
	 */
	public String createXMLFooterSrc() {
		return "</mapper>";
	}
	
	/**
	 * 生成ResultMap
	 * @return
	 */
	public String createResultMapSrc(){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("\t<resultMap id=\""+ tableInfo.getTname()+ "\" type=\"com."+ DBManager.conf.getCompanyName() +"."+ DBManager.conf.getProjectName() +".bean."+ StringUtils.changeFirstToUpper( tableInfo.getTname() ) +"Bean\" >\n");
		
		for (Map.Entry<String, ColumnInfo> temp : tableInfo.getColumns().entrySet() ) {
			String columnsname = temp.getKey();
			sBuffer.append("\t\t<result column=\""+ columnsname +"\" property=\""+ StringUtils.fieldTwoWordToLower(columnsname) +"\"/>\n");
		}
		sBuffer.append("\t</resultMap>\n");
		
    		return sBuffer.toString();
	}
	
	/**
	 * 生成所有列字段名
	 * @return
	 */
	public String createBASEALLCOLUMESrc(){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("\t<sql id=\"Base_Column_List\">\n");
		for (Map.Entry<String, ColumnInfo> temp : tableInfo.getColumns().entrySet() ) {
			String columnsname = temp.getKey();
			sBuffer.append("\t\t"+ columnsname + ",\n");
		}
		String all = sBuffer.toString();
		
		return all.substring(0,all.length()-2)+"\n\t</sql>\n";
	}
	
	/**
	 * 生成add方法
	 * @return
	 */
	public abstract String createAddSrc();
	
	/**
	 * 生成Update方法
	 * @return
	 */
	public abstract String createUpdateSrc();

	/**
	 * 生成QueryRows方法
	 * @return
	 */
	public abstract String createQueryRowsSrc();
	
	/**
	 * 生成QueryList方法
	 * @return
	 */
	public abstract String createQueryListSrc();
	
	/**
	 * 生成QueryAll方法
	 * @return
	 */
	public abstract String createQueryAllSrc();
	
	/**
	 * 生成Delete方法
	 * @return
	 */
	public abstract String createDeleteSrc();
	
	/**
	 * 生成Src源码
	 * @return
	 */
	public String createSrc() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(createXMLHeaderSrc());
		
		sBuffer.append(createResultMapSrc());
		sBuffer.append(createBASEALLCOLUMESrc());
		if (add) {
			sBuffer.append(createAddSrc());
		}
		if (update) {
			sBuffer.append(createUpdateSrc());
		}
		if (queryRows) {
			sBuffer.append(createQueryRowsSrc());
		}
		if (queryList) {
			sBuffer.append(createQueryListSrc());
		}
		if (queryAll) {
			sBuffer.append(createQueryAllSrc());
		}
		if (delete) {
			sBuffer.append(createDeleteSrc());
		}
		sBuffer.append(createXMLFooterSrc());
		
		return sBuffer.toString();
	}
	
	/**
	 * 生成 xml mapper 文件
	 * @param tableInfo
	 */
	public void createXMLFile(TableInfo tableInfo) {
		String module = "Mapper";
		String suffix = "xml";
		String src = createSrc();
		
		FileCreator.createFile(tableInfo, module, suffix, src);
	}
	
}
