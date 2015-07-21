package com.core.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.core.BaseEntity;
import com.core.utils.DateUtils;
import com.core.utils.ExcelSheetConfig;
import com.core.utils.ExcelUtils;
import com.core.utils.PBeanUtils;
import com.core.utils.WebUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/*******************************************************************************
 * Struts Action基类.
 * <p>
 * 提供了访问路径校验、异常处理机制.
 * <p>
 * 同时也提供了请求数据组装(组装到Map/EntityBean)、结果数据和消息打印的公共方法.
 * 
 * @author: yanghb
 * @since: 2009-5-13 下午04:31:00
 * @history: ***********************************************
 * @file: StrutsAction.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
@SuppressWarnings({"unchecked","serial"})
public abstract class StrutsAction extends ActionSupport implements
		ModelDriven, Preparable {
	protected Logger log = LoggerFactory.getLogger(getClass());
	public static final String RELOAD = "reload";

	public Object getModel() {
		return null;
	}

	@Override
	public String execute() throws Exception {
		return index();
	}

	public String list() throws Exception {
		return SUCCESS;
	}
	
	public String index()throws Exception{
		return SUCCESS;
	}

	public void prepare() throws Exception {

	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected Map<String, String> bindMap() {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration enumeration = this.getRequest().getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			String value = this.getRequest().getParameter(name);
			// 如果是排序列，并且列名带点的，则两边加双引号
			if ("sort".equals(name)) {
				if (value.indexOf(".") > -1) {
					value = "\"" + value + "\"";
				}
			}
			value = value.replace("\'", "\"");
			this.log.debug("NAME:" + name + ", VALUE:" + value);
			map.put(name, value);
		}
		return map;
	}
	protected Map<String, Object> bindMapObj() {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration enumeration = this.getRequest().getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			String value = this.getRequest().getParameter(name);
			// 如果是排序列，并且列名带点的，则两边加双引号
			if ("sort".equals(name)) {
				if (value.indexOf(".") > -1) {
					value = "\"" + value + "\"";
				}
			}
			value = value.replace("\'", "\"");
			this.log.debug("NAME:" + name + ", VALUE:" + value);
			map.put(name, value);
		}
		return map;
	}

	protected <T extends BaseEntity> T bindEntity(Class<T> clazz)
			throws Exception {
		T entity = clazz.newInstance();
		Enumeration enumeration = this.getRequest().getParameterNames();
		while (enumeration.hasMoreElements()) {
			String propertyName = (String) enumeration.nextElement();
			String propertyValue = this.getRequest().getParameter(propertyName)
					.trim();
			propertyValue = propertyValue.replace("\'", "\"");
			PBeanUtils.setBeanPropertyByName(entity, propertyName,
					propertyValue);
		}
		return entity;
	}
	
	protected <T extends BaseEntity> List<T> bindEntityList(Class<T> clazz) throws Exception {
		
		List<T> list = new ArrayList<T>();
		Map<String, String[]> map = this.getRequest().getParameterMap();
		
		Set<Entry<String, String[]>> set = map.entrySet();
		
		int index = 0;
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Entry<String, String[]> entry = (Entry<String, String[]>) iterator.next();
			String propertyName = entry.getKey();
			String[] propertyValue = entry.getValue();
			if(index == 0) {
				for (int i = 0; i < propertyValue.length; i++) {
					T entity = clazz.newInstance();
					PBeanUtils.setBeanPropertyByName(entity, propertyName, propertyValue[i]);
					list.add(entity);
				}
			} else {
				for (int i = 0; i < propertyValue.length; i++) {
					T entity = list.get(i);
					PBeanUtils.setBeanPropertyByName(entity, propertyName, propertyValue[i]);
				}
			}
			index++;
		}
		return list;
	}

	public String save() throws Exception {
		return RELOAD;
	}

	public String modify() throws Exception {
		return save();
	}

	/**
	 * 直接输出纯Json.
	 */
	protected void printJson(String text) throws IOException {
		doPrint(text, "text/x-json;charset=UTF-8");
	}

	/**
	 * 直接输出纯XML.
	 */
	protected void printXML(String text) throws IOException {
		doPrint(text, "text/xml;charset=UTF-8");
	}

	/**
	 * 直接输出纯HTML.
	 */
	protected void printHtml(String text) throws IOException {
		doPrint(text, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出纯字符串.
	 */
	protected void printText(String text) throws IOException {
		doPrint(text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出.
	 * 
	 * @param contentType
	 *            内容的类型.html,text,xml的值见后，json为"text/x-json;charset=UTF-8"
	 */
	private void doPrint(String text, String contentType) {

		PrintWriter out = null;
		try {
			if (log.isInfoEnabled()) {
				log.debug("输出的字符串: " + text + "");
			}
			ServletActionContext.getResponse().setContentType(contentType);
			out = ServletActionContext.getResponse().getWriter();
			out.write(text);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				out.print("");
				out.close();
			}
		}

	}

	/**
	 * 错误消息外包
	 * </p>
	 * 后台往前台打印消息时，添加的外包，以便前台展现技术获取消息. 根据不同的前台展现技术变更。当前支持Ext.
	 */
	protected String messageFailureWrap(String message) {
		return WebUtils.getExtFailureMessage(message);
	}

	/**
	 * 成功消息外包 </p> 后台往前台打印消息时，添加的外包，以便前台展现技术获取消息. 根据不同的前台展现技术变更。当前支持Ext.
	 */
	protected String messageSuccuseWrap(String message) {
		return WebUtils.getExtSuccuseMessage(message);
	}
	/**
	 * 成功消息外包 </p> 后台往前台打印消息时，添加的外包，以便前台展现技术获取消息. 根据不同的前台展现技术变更。当前支持Ext.
	 */
	protected String messageSuccuseWrapView(String message) {
		return WebUtils.getExtSuccuseMessageView(message);
	}
	/**
	 * 成功消息外包 与messageSuccuseWrap(Str)方法的区别是：向前台返回的消息是Obj类型
	 * 
	 * @M huangrh
	 */
	protected String messageSuccuseWrapObj(String message) {
		return WebUtils.getExtSuccuseMessage(message, true);
	}

	/**
	 * @see #messageSuccuseWrap(String)
	 */
	protected String messageSuccuseWrap() {
		return messageSuccuseWrap(null);
	}



	/**
	 * **********************************************
	 * 
	 * @method：getMethodNameByType
	 * @description：根据传入的字段名称typeStr获取其get方法名称
	 * @create:Oct 27, 2008-5:54:01 PM huangrh
	 */
	protected String getMethodNameByType(String typeStr) {
		String firstStr = typeStr.substring(0, 1).toUpperCase();
		String methodNameString = "get" + firstStr + typeStr.substring(1);
		return methodNameString;
	}

	/**
	 * **********************************************
	 * 
	 * @method：strutsMapByObj
	 * @param obj
	 *            Object对象
	 * @return Map<String, Object>
	 * @description：将javaBean(obj)对象的属性及值组装到Map<String, Object>
	 * @create:Nov 6, 2008-11:45:46 PM huangrh
	 */
	protected Map<String, Object> strutsMapByObj(Object obj)
			throws NoSuchFieldException {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String nameString = field.getName();
			if (field.getType().isAssignableFrom(String.class))
				map.put(nameString, PBeanUtils
						.forceGetProperty(obj, nameString));
			else
				map.put(nameString, PBeanUtils.forceGetProperty(obj, String
						.valueOf(nameString)));
		}
		return map;
	}


	private String formatString(Object obj) {
		if ((null == obj) || ("null".equals(obj))) {
			return "";
		}
		return obj.toString();
	}
   
	/**
	 * print excel			
	 */
	public  void printExcel(List list) throws IOException {
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response= this.getResponse();
		
		String excelTitle = request.getParameter("excelTitle");
		Assert.notNull(excelTitle, "JSON传输Grid对象字符串 is null");
		
		JSONArray jsonArray =JSONArray.fromObject(excelTitle);
		
		String exportFileName = "exportExcel.xls";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=\"" + exportFileName + "\"");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		
		try {
			ExcelSheetConfig sheetConfig = null;
			if(isContainMapList(list)){
				sheetConfig = ExcelSheetConfig.createExcelSheetConfigByMapList(jsonArray,list);
			} else {
				sheetConfig = ExcelSheetConfig.createExcelSheetConfig(jsonArray,list);
			}
			sheetConfig.setSheetName(exportFileName);
			sheetConfig.setExportAlign(ExcelSheetConfig.VERTICAL_ALIGN);
			ExcelUtils.exportToExcel(sheetConfig, response.getOutputStream());
		} catch (IOException e) {
		} finally {
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	private boolean isContainMapList(List list){
		if(list.size() > 0)
			return list.get(0) instanceof Map;
		return false;
	}
	
	/**
	 * 
	 * @param view
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String exportExcel(String view, String fileName, Map dataMap)throws Exception{
		
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		Set<String> key = dataMap.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String k = (String) it.next();
            request.setAttribute(k, dataMap.get(k));
        }
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment;filename=" 
				+ new String(fileName.getBytes("gb2312"),"ISO8859-1")+DateUtils.toDateString(new Date(),"_yyyyMMddHHmmss")+".xls");
		if(view.contains("/")) {
			return view;
		}
		return "/excel/"+view;
	}


}
