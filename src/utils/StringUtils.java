package utils;
/**
 * 封装了字符串常用的操作。
 * @author Malcolm
 *
 */
public class StringUtils {
	/**
	 * 将字符串的首字母变大写
	 * @param word 传入的字符串
	 * @return 返回的字符串
	 */
	public static String changeFirstToUpper(String word){
		String str = word.toUpperCase();
		return str.substring(0, 1)+word.substring(1);
	}
}
