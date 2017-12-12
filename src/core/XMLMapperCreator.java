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
	
	protected TableInfo tableInfo;
	protected String add;
	protected String update;
	protected String queryRows;
	protected String queryList;
	protected String queryAll;
	protected String delete;
	
	public XMLMapperCreator(TableInfo tableInfo,String add,String update,String queryRows,String queryAll,String queryList,String delete) {
		this.tableInfo = tableInfo;
		this.add = add;
		this.update = update;
		this.queryRows = queryRows;
		this.queryList = queryList;
		this.queryAll = queryAll;
		this.delete = delete;
	}
	
	public String createXMLHeaderSrc() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sBuffer.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		sBuffer.append("<mapper namespace=\"com."+ DBManager.conf.getCompanyName() +"."+ DBManager.conf.getProjectName() +".dao.I"+ StringUtils.changeFirstToUpper(tableInfo.getTname()) +"Dao\">\n");
		return sBuffer.toString();
	}
	
	public String createXMLFooterSrc() {
		return "</mapper>";
	}
	
	
	public String createResultMapSrc(){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("\t<resultMap id=\""+ tableInfo.getTname()+ "\" type=\"com."+ DBManager.conf.getCompanyName() +"."+ DBManager.conf.getProjectName() +".bean."+ StringUtils.changeFirstToUpper( tableInfo.getTname() ) +"Bean\" >\n");
		
		for (Map.Entry<String, ColumnInfo> temp : tableInfo.getColumns().entrySet() ) {
			String columnsname = temp.getKey();
			sBuffer.append("\t\t<result column=\""+ columnsname +"\" property=\""+ columnsname +"\"/>\n");
		}
		sBuffer.append("\t</resultMap>\n");
		
    		return sBuffer.toString();
	}
	
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
	
	public abstract String createAddSrc();
	
	public abstract String createUpdateSrc();

	public abstract String createQueryRowsSrc();
	
	public abstract String createQueryListSrc();
	
	public abstract String createQueryAllSrc();
	
	public abstract String createDeleteSrc();
	
	public String createSrc() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(createXMLHeaderSrc());
		
		sBuffer.append(createResultMapSrc());
		sBuffer.append(createBASEALLCOLUMESrc());
		if (add!=null) {
			sBuffer.append(createAddSrc());
		}
		if (update!=null) {
			sBuffer.append(createUpdateSrc());
		}
		if (queryRows!=null) {
			sBuffer.append(createQueryRowsSrc());
		}
		if (queryList!=null) {
			sBuffer.append(createQueryListSrc());
		}
		if (queryAll!=null) {
			sBuffer.append(createQueryAllSrc());
		}
		if (delete!=null) {
			sBuffer.append(createDeleteSrc());
		}
		sBuffer.append(createXMLFooterSrc());
		
		return sBuffer.toString();
	}
	
}
