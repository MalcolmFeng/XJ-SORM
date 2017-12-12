package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import bean.TableInfo;
import utils.StringUtils;

public class FileCreator {
	/**
	 * 源码写入到指定包指定后缀的源文件中。
	 * @param tableInfo 表信息
	 * @param model 模块
	 * @param src 源代码
	 * @param suffix 文件后缀：java xml
	 */
	public static void createFile(TableInfo tableInfo,String module,String suffix,String src){
		String filePath = DBManager.conf.getSrcPath()+"/com/"+DBManager.conf.getCompanyName()+"/"+DBManager.conf.getProjectName()+"/"+module.toLowerCase(); //
		File f = new File(filePath);
		if(!f.exists()){
			f.mkdirs();
		}
		BufferedWriter bw=null;
		try {
			if (suffix.equals("xml")||module.equalsIgnoreCase("Dao")) {
				bw = new BufferedWriter(new FileWriter(f.getAbsolutePath()+"/I"+StringUtils.changeFirstToUpper(tableInfo.getTname()) + StringUtils.changeFirstToUpper(module.toLowerCase()) +"."+ suffix));
			}else {
				bw = new BufferedWriter(new FileWriter(f.getAbsolutePath()+"/"+StringUtils.changeFirstToUpper(tableInfo.getTname()) + StringUtils.changeFirstToUpper(module.toLowerCase()) +"."+ suffix));
			}
			bw.write(src);
			bw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bw!=null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
