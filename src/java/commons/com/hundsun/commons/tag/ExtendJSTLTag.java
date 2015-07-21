package com.hundsun.commons.tag;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.core.utils.JsonUtils;
import com.dolphin.domain.Datadict;
import com.dolphin.service.DatadictService;
import com.hundsun.ctim.service.FileService;

/**
 * 自定义JSTL标签
 * @author: zhudp
 * @since: 2010-12-13  下午02:28:21
 * @history:
 ************************************************
 * @file: FormatTag.java
 * @Copyright: 2010 HundSun Electronics Co.,Ltd.
 * All right reserved.
 ***********************************************
 */
public class ExtendJSTLTag {
	
	static WebApplicationContext context ;
	static {
		if(context == null) {
			context = ContextLoaderListener.getCurrentWebApplicationContext();
		}
	}

	/** 
     * 日期格式化
     * @param iDate 8位整数
     */ 
    public static String fmtInt2Date(int iDate) {
          String sDate = String.valueOf(iDate);
          if(sDate.length()==8){
        	  return sDate.substring(0, 4)+"-"+sDate.substring(4, 6)+"-"+sDate.substring(6, 8);
          }else{
        	  return "";
          }
    }
    
    /**
	 * 根据资源类别和CODE，取得资源名称
	 * @param code 资源CODE
	 * @param type 资源类别
     * @return 
     * @create  2010-10-13 下午03:31:32 zhudp
     * @history
     */
    public static String getResName(String code, String type) {
    	DatadictService datadictService =  (DatadictService) context.getBean("datadictService");
    	datadictService.getResName(code, type);
    	return datadictService.getResName(code, type);
    }
    
    
    public static String getRealImageUrl(String paramUrl){
    	FileService fileService = (FileService) context.getBean("fileServiceImp");
    	
    	String realUrl = "";
    	if(StringUtils.isBlank(paramUrl)){
    		realUrl = fileService.getDefaultImageUrl();
    	}else{
    		realUrl = fileService.getImageUrl(paramUrl);
    	}
    	return realUrl;
    }
    
    /**
     * 对中文进行编码
     * @param url
     * @return 
     * @create  2010-11-24 上午09:53:59 jinrey
     * @history
     */
	public static String enCodeParam(String param){
    	String enCodeParam = "";
		try {
			enCodeParam = URLEncoder.encode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return enCodeParam;
    }
	/**
	 * 对中文进行编码
	 * @param url
	 * @return 
	 * @create  2010-11-24 上午09:53:59 jinrey
	 * @history
	 */
	public static String deCodeParam(String param){
		String deCodeParam = "";
		try {
			deCodeParam = URLDecoder.decode(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return deCodeParam;
	}
	
	/**
	 * 将SYS_RESOURCE 表中中定义的map组装成ext的下拉框
	 * @param resType 	资源类别
	 * @param dataRange 资源范畴
     * @create zhudp 2010-10-13 下午03:31:32 
     * @history
	 */
	public static String buildComboSelect(String resType, String dataRange) {
		DatadictService datadictService =  (DatadictService) context.getBean("datadictService");
		List<Datadict>  list = datadictService.getDatadictMapList(resType);
		return JsonUtils.getJsonComboxSort(list);
	}
	
	/**
	 * 将SYS_RESOURCE 表中中定义的select的下拉框options
	 * @create zhudp 2012-7-22 下午03:31:32 
	 * @history
	 */
	public static String buildSelectOptions(String resType) {
		DatadictService datadictService =  (DatadictService) context.getBean("datadictService");
		
		List<Datadict> list = datadictService.getListByResType(resType);
		StringBuffer sb = new StringBuffer();
		
		 for(Datadict d : list) {
			//过滤掉根节点数据
				if(!d.getParentId().equals(Datadict.rootNode)){
					sb.append("['").append(d.getResCode()).append("', '").append(d.getResName()).append("'],");
				}
		 }  
		return sb.substring(0, sb.length()-1);
	}

}
