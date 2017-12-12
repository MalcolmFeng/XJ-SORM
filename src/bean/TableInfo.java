package bean;

import java.util.List;
import java.util.Map;

/**
 * 存储表结构的信息
 * @author Malcolm
 *
 */
public class TableInfo {
	/**
	 * 表名
	 */
	private String tname;
	/**
	 * 所有字段名对应的字段信息
	 */
	private Map<String,ColumnInfo> columns;
	/**
	 * 唯一主键（目前我们只能处理有且只有一个主键的情况）
	 */
	private ColumnInfo onlyPrikey;
	/**
	 * 多个主键（封装到List中）
	 */
	private List<ColumnInfo> Prikeys;
	
	public TableInfo() {
	}
	

	public TableInfo(String tname, Map<String, ColumnInfo> columns, List<ColumnInfo> prikeys) {
		super();
		this.tname = tname;
		this.columns = columns;
		Prikeys = prikeys;
	}


	public TableInfo(String tname, Map<String, ColumnInfo> columns, ColumnInfo onlyPrikey, List<ColumnInfo> prikeys) {
		super();
		this.tname = tname;
		this.columns = columns;
		this.onlyPrikey = onlyPrikey;
		Prikeys = prikeys;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Map<String, ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, ColumnInfo> columns) {
		this.columns = columns;
	}

	public ColumnInfo getOnlyPrikey() {
		return onlyPrikey;
	}

	public void setOnlyPrikey(ColumnInfo onlyPrikey) {
		this.onlyPrikey = onlyPrikey;
	}

	public List<ColumnInfo> getPrikeys() {
		return Prikeys;
	}

	public void setPrikeys(List<ColumnInfo> prikeys) {
		Prikeys = prikeys;
	}	
	
}
