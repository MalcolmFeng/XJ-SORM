package creator;

import bean.TableInfo;
import core.FileCreator;
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
	public XMLMapperCreatorMySQL(TableInfo tableInfo,String add,String update,String queryRows,String queryAll,String queryList,String delete) {
		super(tableInfo,add,update,queryRows,queryAll,queryList,delete);
	}

	@Override
	public String createAddSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createUpdateSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createQueryRowsSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createQueryListSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createQueryAllSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createDeleteSrc() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 将创建的java源代码写入到文件中
	 * @param tableInfo
	 * @param databaseTypeConvertor
	 * @param string
	 */
	public void createXMLFile(TableInfo tableInfo) {
		String module = "Mapper";
		String suffix = "xml";
		String src = createSrc();
		
		FileCreator.createFile(tableInfo, module, suffix, src);
	}
	
}
