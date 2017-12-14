package convertorImpl;

import core.TypeConvertor;

public class SqlServerTypeConvertor implements TypeConvertor{
	/**
	 * 类型转化器，将数据库类型转化为java类型
	 */
	@Override
	public String databaseType2JavaType(String columnType) {
		// varchar-->String
				if ("varchar".equalsIgnoreCase(columnType) 
						|| "nvarchar".equalsIgnoreCase(columnType)
						|| "char".equalsIgnoreCase(columnType)
						|| "datetime".equalsIgnoreCase(columnType) 
						|| "text".equalsIgnoreCase(columnType)
						|| "ntext".equalsIgnoreCase(columnType)) {
					return "String";
				} else if ("int".equalsIgnoreCase(columnType) 
						|| "tinyint".equalsIgnoreCase(columnType)
						|| "smallint".equalsIgnoreCase(columnType) 
						|| "integer".equalsIgnoreCase(columnType)) {
					return "Integer";
				} else if ("bigint".equalsIgnoreCase(columnType)) {
					return "Long";
				} else if ("double".equalsIgnoreCase(columnType) 
						|| "float".equalsIgnoreCase(columnType)
						|| "money".equalsIgnoreCase(columnType)) {
					return "Double";
				} else if ("text".equalsIgnoreCase(columnType)) {
					return "java.sql.CLob";
				} else if ("date".equalsIgnoreCase(columnType)) {
					return "java.sql.Date";
				} else if ("time".equalsIgnoreCase(columnType)) {
					return "java.sql.Time";
				} else if ("timestamp".equalsIgnoreCase(columnType)) {
					return "java.sql.Timestamp";
				}
		
		
		return null;
	}
	/**
	 * 类型转化器，将java类型转化为数据库类型
	 */
	@Override
	public String javaType2databaseType(String javaDataType) {
		
		if ("String".equalsIgnoreCase(javaDataType)) {
			return "varchar";
		} else if ("Char".equalsIgnoreCase(javaDataType)) {
			return "char";
		} else if ("java.lang.Integer".equalsIgnoreCase(javaDataType)) {
			return "int";
		} else if ("Long".equalsIgnoreCase(javaDataType)) {
			return "bigint";
		} else if ("double".equalsIgnoreCase(javaDataType) 
				|| "float".equalsIgnoreCase(javaDataType)) {
			return "Double";
		} else if ("java.sql.Clob".equalsIgnoreCase(javaDataType)) {
			return "text";
		} else if ("java.sql.Date".equalsIgnoreCase(javaDataType)) {
			return "date";
		} else if ("java.sql.Time".equalsIgnoreCase(javaDataType)) {
			return "Time";
		} else if ("java.sql.Timestamp".equalsIgnoreCase(javaDataType)) {
			return "timestamp";
		}
		return null;
	}
	
}
