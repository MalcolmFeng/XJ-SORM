package creatorImpl;

import bean.TableInfo;
import core.DBManager;
import core.JavaCreator;
import core.TypeConvertor;
import utils.StringUtils;

/**
 * 生成Dao java文件
 * @author Malcolm
 *
 */
public class JavaDaoCreator extends JavaCreator{
	
	public JavaDaoCreator(TableInfo tableInfo, String module, String suffix) {
		super(module, suffix,tableInfo);
	}
	
	public JavaDaoCreator(TableInfo tableInfo, TypeConvertor typeConvertor, String module, String suffix) {
		super(module, typeConvertor, suffix,tableInfo);
	}

	
	
	@Override
	public void declareateModule(TableInfo tableInfo, StringBuilder javaSrc) {
		
		
	}

	@Override
	public void importPackageSrcCreator(TableInfo tableInfo, StringBuilder javaSrc) {
		
		javaSrc.append("import com."+ DBManager.conf.getCompanyName() +".common.IBaseDao;\n");
		javaSrc.append("import com."+ DBManager.conf.getCompanyName() +"."+ DBManager.conf.getProjectName() +".bean."+ tableNameFirstUpper +"Bean;\n\n");

	}

	@Override
	public void inclassSrc(TableInfo tableInfo, StringBuilder javaSrc,TypeConvertor convertor) {
		//类的开始
		javaSrc.append("public interface "+"I"+StringUtils.changeFirstToUpper(tableInfo.getTname())+ StringUtils.changeFirstToUpper(module.toLowerCase()) +" extends IBaseDao<"+ tableNameFirstUpper +"Bean>{\n\n");
			
		
		
		//类结束
		javaSrc.append("}");
	}

	
}
