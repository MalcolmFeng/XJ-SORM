package creatorImpl;

import bean.TableInfo;
import core.DBManager;
import core.JavaCreator;
import core.TypeConvertor;
import utils.StringUtils;

/**
 * 生成Controller java文件
 * @author Malcolm
 *
 */
public class JavaControllerCreator extends JavaCreator{
	
	public JavaControllerCreator(TableInfo tableInfo, TypeConvertor typeConvertor, String module, String suffix) {
		super(module, typeConvertor, suffix,tableInfo);
	}

	public JavaControllerCreator(TableInfo tableInfo, String module, String suffix) {
		super(module, suffix,tableInfo);
	}

	@Override
	public void declareateModule(TableInfo tableInfo, StringBuilder javaSrc) {
		javaSrc.append("@Controller\n");
	}

	@Override
	public void importPackageSrcCreator(TableInfo tableInfo, StringBuilder javaSrc) {
		
		javaSrc.append("import org.slf4j.Logger;\n\n");
		
		javaSrc.append("import org.slf4j.LoggerFactory;\n");
		javaSrc.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		javaSrc.append("import org.springframework.stereotype.Controller;\n\n");

		javaSrc.append("import com."+ DBManager.conf.getCompanyName() +"."+ DBManager.conf.getProjectName() +".service."+ tableNameFirstUpper +"Service;\n");
		javaSrc.append("import com."+ DBManager.conf.getCompanyName() +".common.BaseController;\n\n");

	}

	@Override
	public void inclassSrc(TableInfo tableInfo, StringBuilder javaSrc,TypeConvertor convertor) {
		
		//类的开始
		javaSrc.append("public class "+ tableNameFirstUpper + moduleNameFirstUpper +" extends BaseController {\n\n");
			
		javaSrc.append("private static final Logger log = LoggerFactory.getLogger("+ tableNameFirstUpper +"Controller.class);\n\n");
		javaSrc.append("\t@Autowired\n");
		javaSrc.append("\tprivate "+ tableNameFirstUpper +"Service "+ StringUtils.fieldTwoWordToLower(tableNameFirstUpper) +"Service;\n\n\n\n");
		
		
		//类结束
		javaSrc.append("}");
				

	}

	
}
