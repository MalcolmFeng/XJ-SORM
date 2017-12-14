package creatorImpl;

import java.util.Map;

import bean.ColumnInfo;
import bean.TableInfo;
import core.JavaCreator;
import core.TypeConvertor;
import utils.StringUtils;

/**
 * 生成javabean
 * @author Malcolm
 *
 */
public class JavaBeanCreator extends JavaCreator{
	
	public JavaBeanCreator(TableInfo tableInfo, TypeConvertor typeConvertor, String module, String suffix) {
		super(module, typeConvertor, suffix,tableInfo);
	}


	@Override
	public void declareateModule(TableInfo tableInfo, StringBuilder javaSrc) {
		
		
		
	}

	@Override
	public void importPackageSrcCreator(TableInfo tableInfo, StringBuilder javaSrc) {
		
		
	}

	@Override
	public void inclassSrc(TableInfo tableInfo, StringBuilder javaSrc,TypeConvertor typeConvertor) {
		//类的开始
		javaSrc.append("public class "+StringUtils.changeFirstToUpper(tableInfo.getTname())+ StringUtils.changeFirstToUpper(module.toLowerCase()) +" {\n\n");
		
		Map<String, ColumnInfo> columns = tableInfo.getColumns();
		
		//遍历所有的字段，进行 field 代码拼接。
		for(ColumnInfo columnInfo:columns.values()){
			//进行类型转换
			String javaFieldType = typeConvertor.databaseType2JavaType(columnInfo.getDataType());
			//代码拼接
			javaSrc.append("\tprivate "+javaFieldType+" "+columnInfo.getName()+";\n");
		}
		
		//遍历所有的字段，进行 get set 代码拼接。
		for(ColumnInfo columnInfo:columns.values()){
			//进行类型转换
			String javaFieldType = typeConvertor.databaseType2JavaType(columnInfo.getDataType());
			//代码拼接
			javaSrc.append("\tpublic "+javaFieldType+" get"+StringUtils.changeFirstToUpper(columnInfo.getName())+"(){\n");		
			javaSrc.append("\t\treturn "+columnInfo.getName()+";\n");
			javaSrc.append("\t}\n");
			javaSrc.append("\tpublic void set"+StringUtils.changeFirstToUpper(columnInfo.getName())+"("+javaFieldType+" "+columnInfo.getName()+"){\n");		
			javaSrc.append("\t\tthis."+columnInfo.getName()+"="+columnInfo.getName()+";\n");
			javaSrc.append("\t}\n\n");
		}
		//类结束
		javaSrc.append("}");
	}

	
}
