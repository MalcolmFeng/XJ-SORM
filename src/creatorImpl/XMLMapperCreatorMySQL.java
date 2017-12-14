package creatorImpl;

import bean.TableInfo;
import core.XMLMapperCreator;

/**
 * 封装了java文件（源代码）常用的操作。
 * @author Malcolm
 *
 */
public class XMLMapperCreatorMySQL extends XMLMapperCreator{
	
	/**
	 * 
	 * @param tableInfo 表信息
	 * @param add 增加
	 * @param update 修改
	 * @param queryRows 查询所有行数
	 * @param queryAll 查询所有
	 * @param queryList 分页查询
	 * @param delete 删除
	 */
	public XMLMapperCreatorMySQL(TableInfo tableInfo,Boolean add,Boolean update,Boolean queryRows,Boolean queryAll,Boolean queryList,Boolean delete) {
		super(tableInfo,add,update,queryRows,queryAll,queryList,delete);
	}
	
	public XMLMapperCreatorMySQL(Boolean add,Boolean update,Boolean queryRows,Boolean queryAll,Boolean queryList,Boolean delete) {
		super(add,update,queryRows,queryAll,queryList,delete);
	}
	
	public XMLMapperCreatorMySQL(TableInfo tableInfo,XMLMapperCreator xmlMapperCreator) {
		super(tableInfo,xmlMapperCreator);
	}
	
	public XMLMapperCreatorMySQL() {
		super();
	}
	
	@Override
	public String createAddSrc() {
		return "";
	}

	@Override
	public String createUpdateSrc() {
		return "";
	}

	@Override
	public String createQueryRowsSrc() {
		return "";
	}

	@Override
	public String createQueryListSrc() {
		return "";
	}

	@Override
	public String createQueryAllSrc() {
		return "";
	}

	@Override
	public String createDeleteSrc() {
		return "";
	}
	
}
