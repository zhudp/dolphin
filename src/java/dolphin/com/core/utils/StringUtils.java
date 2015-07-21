package com.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringUtils {
	/**
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return
	 * @create 2008-11-26 上午08:55:52 wanghh
	 * @history
	 */
	public static String replace(String str, String oldStr, String newStr) {
		if (str != null) {
			int index = 0;
			int oldLen = oldStr.length();
			// oldStr字符串长度
			int newLen = newStr.length();
			// newStr字符串长度
			while (true) {
				index = str.indexOf(oldStr, index);
				if (index == -1) {
					return str;
				} else {
					str = str.substring(0, index) + newStr
							+ str.substring(index + oldLen);
					index += newLen;
				}
			}
		} else {
			return "";
		}
	}

	/**
	 * lucene转义字符
	 * 
	 * @param str
	 * @return
	 * @create 2008-11-26 上午09:38:09 wanghh
	 * @history
	 */
	public static String transform(String str) {
		if (str == null)
			return str;
		String resultStr = str;
		String[] strArray = new String[] { "\\", "\"", "+", "-", "&&", "||",
				"!", "(", ")", "{", "}", "[", "]", "^", "~", "*", "?", ":" };
		for (int i = 0; i < strArray.length; i++) {
			resultStr = replace(resultStr, strArray[i], "\\" + strArray[i]);
		}
		return resultStr;
	}

	/**
	 * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
	 * 
	 * <pre>
	 * StringUtil.isBlank(null)      = true
	 * StringUtil.isBlank(&quot;&quot;)        = true
	 * StringUtil.isBlank(&quot; &quot;)       = true
	 * StringUtil.isBlank(&quot;bob&quot;)     = false
	 * StringUtil.isBlank(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *            要检查的字符串
	 * @return 如果为空白, 则返回<code>true</code>
	 * @create 2009-1-6 下午01:49:48 yanghb
	 * @history
	 */
	public static boolean isBlank(String str) {
		int length;
		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}
		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 组合成新的URL地址,把第二个参数连接到第一个参数后面组合成新URL,如果第二个参数是以http://,ftp://,https://开头的话,
	 * 就不组合,直接返回第二个参数的值
	 * 
	 * @param baseUrlStr
	 * @param nextUrlStr
	 * @return
	 * @create Jan 19, 2009 9:34:50 AM yuancong
	 * @history
	 */
	public static String unite2UrlStr(String baseUrlStr, String nextUrlStr) {
		String tmp_baseUrlStr = baseUrlStr;
		String tmp_nextUrlStr = nextUrlStr;
		if (isBlank(tmp_baseUrlStr)) {
			return nextUrlStr;
		}
		if (isBlank(tmp_nextUrlStr)) {
			return baseUrlStr;
		}
		if (tmp_nextUrlStr.startsWith("http://")
				|| tmp_nextUrlStr.startsWith("ftp://")
				|| tmp_nextUrlStr.startsWith("https://")) {
			return tmp_nextUrlStr;
		}
		tmp_baseUrlStr = tmp_baseUrlStr.replace("\\", "/");
		tmp_nextUrlStr = tmp_nextUrlStr.replace("\\", "/");
		if (!tmp_baseUrlStr.endsWith("/")) {
			tmp_baseUrlStr = tmp_baseUrlStr + "/";
		}
		if (tmp_nextUrlStr.startsWith("/")) {
			tmp_nextUrlStr = tmp_nextUrlStr.substring(1);
		}
		return tmp_baseUrlStr + tmp_nextUrlStr;
	}

	/**
	 * 取得客户端IP,解决使用代理得不到客户端IP的BUG
	 * 
	 * @param request
	 * @return
	 * @create 2009-2-24 上午09:19:02 yanghb
	 * @history
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 将带有'_'的单词转成驼峰状形式
	 * 
	 * @param tableName
	 * @return
	 */
	public static String nameMapping(String tableName) {
		if (tableName.contains("_")) {
			String[] words = tableName.split("_");
			String str = words[0].toLowerCase();
			for (int i = 1; i < words.length; i++) {
				str += StringUtils.wordMapping(words[i]);
			}
			return str;
		} else {
			return StringUtils.wordMapping(tableName);
		}
	}

	/**
	 * 将单词首字母大写，其余字母小写
	 * 
	 * @param word
	 * @return
	 */
	public static String wordMapping(String word) {
		return Character.toUpperCase(word.charAt(0))
				+ word.substring(1, word.length()).toLowerCase();
	}
	
	/**
	 * 隐藏身份证后四位
	 * @param idCard
	 * @return
	 */
	public static String hideLastFourNumb(String idCard) {
		if(!StringUtils.isBlank(idCard) && idCard.length() > 4){
			String replaceStr = idCard.substring(0, (idCard.length() - 4));
			replaceStr += "****";
			return replaceStr;
		}
		return idCard;
	}
	
	/**
	 * 将字符串中的空格、换行、回车、水平制表符去掉
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}
