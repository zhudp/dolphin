package com.core.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;


public class StringTool {
    private static final String symbol = "...";

    /**
     * 用于截取字符串前多少个字
     * @param orgStr 源字符串
     * @param tnum 截取的字符数
     * @return 返回截取到的字符串
     * @history yuancong 2008年11月12日16时16分15秒 调整删除了多余的语法
     */
    public synchronized String truncateString(String orgStr, int tnum) {
        String testStr = "中";
        int len = testStr.getBytes().length;
        if (orgStr == null || orgStr.equals("") || tnum <= 0) {
            return "";
        }
        int cnum = orgStr.getBytes().length;
        // 截断长度比实际字符串长要长
        if ((tnum * len) >= cnum) {
            return orgStr;
        } else {
            String temp = new String(orgStr.getBytes(), 0, (tnum * len) + 1);
            return temp.substring(0, temp.length() - 2) + symbol;
        }
    }

    /**
     * added by yanghb 20081119
     * @param orgStr 原始字符串
     * @param tnum 截断的长度
     * @param tail 截断时需要追加的字符串
     */
    public synchronized String truncateString(String orgStr, int tnum, String tail) {
        if (tail == null) {
            return this.truncateString(orgStr, tnum);
        } else {
            String testStr = "中";
            int len = testStr.getBytes().length;
            if (orgStr == null || orgStr.equals("") || tnum <= 0) {
                return "";
            }
            int cnum = orgStr.getBytes().length;
            // 截断长度比实际字符串长要长
            if ((tnum * len) >= cnum) {
                return orgStr;
            } else {
                String temp = new String(orgStr.getBytes(), 0, (tnum * len) + 1);
                return temp.substring(0, temp.length() - 2) + tail;
            }
        }
    }

    /**
     * 读取一个文件到字符串里.
     * @param sFileName 文件名
     * @param sEncode String
     * @return 文件内容
     */
    public static String readTextFile(String sFileName, String sEncode) {
        StringBuffer sbStr = new StringBuffer();
        FileInputStream fis = null;
        InputStreamReader read = null;
        try {
            File ff = new File(sFileName);
            fis = new FileInputStream(ff);
            read = new InputStreamReader(fis, sEncode);
            BufferedReader ins = new BufferedReader(read);
            
            String dataLine = "";
            while (null != (dataLine = ins.readLine())) {
                sbStr.append(dataLine);
                sbStr.append("\r\n");
            }
            ins.close();
        } catch (UnsupportedEncodingException e) {
            //
        } catch (FileNotFoundException e) {
            //
        } catch (IOException e) {
        }finally{
        	//added by yanghb20090116
			try{
				if(fis!=null){
					fis.close();
				}
			}catch(Exception e){
				// 关闭错误
			}
			try{
				if(read!=null){
					read.close();
				}
			}catch(Exception e){
				// 关闭错误
			}
			
        }
        return sbStr.toString();
    }

    /**
     * 去掉左右空格后字符串是否为空
     * @param astr String
     * @return boolean
     */
    public boolean isTrimEmpty(String astr) {
        if ((null == astr) || (astr.length() == 0)) {
            return true;
        }
        if (isBlank(astr.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 字符串是否为空:null或者长度为0.
     * @param astr 源字符串.
     * @return boolean
     */
    public boolean isBlank(String astr) {
        if ((null == astr) || (astr.length() == 0)) {
            return true;
        }
        return false;
    }

    /**
     * 解析普通文本节点.
     * @param content
     * @throws ParserException
     */
    public synchronized String getTextFromHtml(String content) throws ParserException {
        if (content == null) {
            return "";
        }
        Parser myParser;
        Node[] nodes = null;
        myParser = Parser.createParser(content, null);
        nodes = myParser.extractAllNodesThatAre(TextNode.class); // exception could be thrown here
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nodes.length; i++) {
            TextNode textnode = (TextNode) nodes[i];
            String line = textnode.toPlainTextString().trim();
            if (line.equals(""))
                continue;
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * 计算所含字符的数量
     */
    public synchronized int countSubString(String str, String c) {
        int count = 0;
        String tempStr = str;
        if (str == null || "".equals(str))
            return count;
        for (int i = 0; tempStr.indexOf(c) > 0; i++) {
            tempStr = tempStr.substring(tempStr.indexOf(c) + c.length());
            count++;
        }
        return count;
    }

//    /**
//     * 获得前台的图片等资源的url
//     */
//    public synchronized String getFileWebUrl(String fileUrl) {
//        if (fileUrl == null) {
//            return "";
//        }
//        String base = BaseConfigObj.getInstance().getUploadFileBaseUrl();
//        if (fileUrl.startsWith(base))
//            return fileUrl;
//        return base + fileUrl;
//    }
    
 
    /**
     * 将AJAX访问后要返回的数据封装成特定的XML数据,如果前两个参数都为空(null或""),则不组装成XML,直接返回第三个参数的dataStr数据, "text/xml;charset=UTF-8"
     * @param msgType 分为error和return =>表示返回数据的类型:error表示异常,return:表示正常返回结果
     * @param contentType 分为 js 和 html =>返回的数据操作类型:js表示一段完整的JS代码,接收后使用eval来运行,html表示普通返回结果,具体操作由开发者自己定义
     * @param dataStr 要返回的正常数据
     * @return 返回封装好的XML字符串
     * @create Nov 12, 2008 4:13:57 PM yuancong
     * @history
     */
    public synchronized static String makeAjaxReturnXml(String msgType, String contentType, String dataStr) {
        String xmlString = "";
        if ((null == msgType || msgType.trim().equals("")) && (null == contentType || contentType.trim().equals(""))) {
            xmlString = dataStr;
        } else {
            xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            xmlString += "<result>";
            xmlString += "    <msgType><![CDATA[" + msgType.trim() + "]]></msgType>";
            xmlString += "    <contentType><![CDATA[" + contentType.trim() + "]]></contentType>";
            xmlString += "    <data><![CDATA[" + dataStr + "]]></data>";
            xmlString += "</result>";
        }
        return xmlString;
    }

    /**
     * added by yanghb 20081127 将s中的strb全部换成strh
     * @param s 待处理字符串
     * @param strb 匹配的子字符串
     * @param strh 替换后的子字符串
     * @return
     */
    public synchronized static final String replaceString(String s, String strb, String strh) {
        if (s == null || s.length() == 0)
            return s;
        StringBuffer tmp = new StringBuffer();
        int k;
        s = s.trim();
        while ((k = s.indexOf(strb)) >= 0) {
            tmp.append(s.substring(0, k));
            tmp.append(strh);
            s = s.substring(k + strb.length());
        }
        if (s.length() > 0)
            tmp.append(s);
        return tmp.toString();
    }

    /**
     * 去除Ｓ中的所有的\n\r
     */
    public static final String cuteNR(String s) {
        s = replaceString(s, "\n", "");
        s = replaceString(s, "\r", "");
        return s;
    }

    /**
     * @param timeStr
     * @return
     * @create 2008-12-2 下午01:32:27 wanghh
     * @history
     */
    public static String getDateStr(String timeStr) {
        if (timeStr == null)
            return null;
        String dateStr = "";
        if (timeStr.length() >= 8)
            dateStr = timeStr.substring(0, 4) + "-" + timeStr.substring(4, 6) + "-" + timeStr.substring(6, 8);
        return dateStr;
    }

    /**
     * url编码
     * @param srcStr
     * @return
     * @create 2008-12-2 下午01:32:38 wanghh
     * @history
     */
    public static String getUrlEncode(String srcStr) {
        if (srcStr == null)
            return null;
        try {
            return java.net.URLEncoder.encode(srcStr, "utf-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 转义一个字符串，使之可以在html页面中全部正确显示
     * @param s
     * @return
     */
    public static String escapeHtml(String s) {
        if (s == null) {
            return null;
        }
        return StringEscapeUtils.escapeHtml(s);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param intMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney) {
        return getFormatMoney(strMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param strMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney, String formatStr) {
        return getFormatMoney(strMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param strMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale 使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney, String formatStr, Locale locale) {
        Double doubleMoney;
        if (strMoney == null || strMoney.trim().equals("")) {
            strMoney = "0";
        }
        try {
            doubleMoney = Double.valueOf(strMoney);
        } catch (Exception e) {
            return strMoney;
        }
        return getFormatMoney(doubleMoney, formatStr, locale);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param intMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney) {
        return getFormatMoney(intMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param intMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney, String formatStr) {
        return getFormatMoney(intMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param intMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale 使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney, String formatStr, Locale locale) {
        if (intMoney == null) {
            intMoney = Integer.parseInt("0");
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式(千分位)
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(intMoney);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param doubleMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney) {
        return getFormatMoney(doubleMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式
     * @param doubleMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney, String formatStr) {
        if (doubleMoney == null) {
            doubleMoney = Double.valueOf(0);
        }
        return getFormatMoney(doubleMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式
     * @param doubleMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale 使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney, String formatStr, Locale locale) {
        if (doubleMoney == null) {
            doubleMoney = Double.valueOf(0);
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式(千分位)
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(doubleMoney);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     * @param bigDecimalMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney) {
        return getFormatMoney(bigDecimalMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式
     * @param bigDecimalMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale 使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney, String formatStr) {
        return getFormatMoney(bigDecimalMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式
     * @param bigDecimalMoney
     * @param formatStr 格式(例如: ###.00 元),
     *            #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale 使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney, String formatStr, Locale locale) {
        if (bigDecimalMoney == null) {
            bigDecimalMoney = BigDecimal.valueOf(0.00);
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式(千分位)
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(bigDecimalMoney);
    }

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
    
    public static void main(String[] args) {
        StringTool stringTool = new StringTool();
        System.out.println(StringTool.getFormatMoney(121213344.3455,null,Locale.GERMAN));
        System.out.println(stringTool.truncateString("aaaa1人生命值来", 3));
        System.out.println(stringTool.truncateString("a人生a人生命值来", 3));
    }
}
