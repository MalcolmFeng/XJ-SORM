package creatorImpl;

import bean.TableInfo;
import core.XMLMapperCreator;

/**
 * 封装了java文件（源代码）常用的操作。
 * @author Malcolm
 *
 */
public class XMLMapperCreatorSQLServer extends XMLMapperCreator{
	

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
	public XMLMapperCreatorSQLServer(TableInfo tableInfo,Boolean add,Boolean update,Boolean queryRows,Boolean queryAll,Boolean queryList,Boolean delete) {
		super(tableInfo,add,update,queryRows,queryAll,queryList,delete);
	}
	
	public XMLMapperCreatorSQLServer(Boolean add,Boolean update,Boolean queryRows,Boolean queryAll,Boolean queryList,Boolean delete) {
		super(add,update,queryRows,queryAll,queryList,delete);
	}
	
	public XMLMapperCreatorSQLServer(TableInfo tableInfo,XMLMapperCreator xmlMapperCreator) {
		super(tableInfo,xmlMapperCreator);
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
