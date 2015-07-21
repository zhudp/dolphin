package com.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StringTool {
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
                    str = str.substring(0, index) + newStr + str.substring(index + oldLen);
                    index += newLen;
                }
            }
        } else {
            return "";
        }
    }

    /**
     * lucene转义字符
     * @param str
     * @return
     * @create 2008-11-26 上午09:38:09 wanghh
     * @history
     */
    public static String transform(String str) {
        if (str == null)
            return str;
        String resultStr = str;
        String[] strArray = new String[] { "\\", "\"", "+", "-", "&&", "||", "!", "(", ")", "{", "}", "[", "]", "^",
                "~", "*", "?", ":" };
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
     * @param str 要检查的字符串
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
     * 组合成新的URL地址,把第二个参数连接到第一个参数后面组合成新URL,如果第二个参数是以http://,ftp://,https://开头的话,就不组合,直接返回第二个参数的值
     * @param baseUrlStr
     * @param nextUrlStr
     * @return 
    * @create  Jan 19, 2009 9:34:50 AM yuancong
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
        if (tmp_nextUrlStr.startsWith("http://") || tmp_nextUrlStr.startsWith("ftp://") || tmp_nextUrlStr.startsWith("https://")) {
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
     * @param request
     * @return 
     * @create  2009-2-24 上午09:19:02 yanghb
     * @history  
     */
    public static String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }
    
    public static void setCookie(HttpServletResponse response, String CookieName, String CookieVal, int CookieAge)
    throws UnsupportedEncodingException
	{
	    Cookie cookie = new Cookie(CookieName, URLEncoder.encode(CookieVal, "utf-8"));
	    cookie.setMaxAge(CookieAge);
	    cookie.setPath("/");
	    response.addCookie(cookie);
	}
	
	public static String getCookie(HttpServletRequest request, String CookieName)
	    throws UnsupportedEncodingException
	{
	    Cookie cookies[] = request.getCookies();
	    if(cookies == null)
	        return null;
	    for(int i = 0; i < cookies.length; i++)
	        if(cookies[i].getName().equals(CookieName))
	            return URLDecoder.decode(cookies[i].getValue(), "utf-8");
	
	    return null;
	}
	
	public static String[] getCookie(HttpServletRequest request)
	    throws UnsupportedEncodingException
	{
	    Cookie cookies[] = request.getCookies();
	    ArrayList al = new ArrayList();
	    if(cookies == null)
	        return null;
	    for(int i = 0; i < cookies.length; i++)
	        al.add(cookies[i].getName() + " = " + URLDecoder.decode(cookies[i].getValue(), "utf-8"));
	
	    return (String[])al.toArray(new String[0]);
	}

}
