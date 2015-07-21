package com.core.tool;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;





/**
 * veloctiy工具类，帮助通过模板文件生成对应的文件          
 * @author  ：方浩      
 * @since: 
 * @history：
******************************************************************
 * @file : VelocityTool.java              ----java 文件名
 * @Copyright ® 2007 HundSun Electronic Co. Ltd. ---版权信息
 *  All right reserved.
******************************************************************
 */
public final class VelocityTool {
	private VelocityEngine ve;
	private List<VelocityToolInfo> toolinfo = new ArrayList<VelocityToolInfo>();
	private VelocityContext context;
	private String velocityWebPath = "";
	private static Log log = LogFactory.getLog(VelocityTool.class);
	public void addTool(VelocityToolInfo info)
    {
        toolinfo.add(info);
    }
	public VelocityTool(String baseTemplatePath) {
		ve = new VelocityEngine();
		try {
			Properties p = new Properties();
			//设置模板文件根路径
			p.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, baseTemplatePath);
			//设置Velocity初始化参数
			//URL velocitypath = this.getClass().getResource("VelocityTool.class");
			
		    InputStream fis = null;
			try{
			
				fis = this.getClass().getResourceAsStream("velocity.properties");
				p.load(fis);
			}catch(Exception e){
				log.error("VelocityTool load error",e);
			}finally{
				try{
					if(fis!=null){
						fis.close();
					}
				}catch(Exception e){
					log.error("velocity.properties 关闭错误");
				}
			}
			context = new VelocityContext();
			ve.init(p);
		} catch (Exception e) {
			log.error("VelocityTool error",e);
			throw new VelocityException("模板引擎初始化失败");
		}
	}
    
	protected void loadVeloctyTool()
	{
		InputStream input = null;
		try {
			
			input = this.getClass().getResourceAsStream("toolbox.xml");
			Digester digester= new Digester();
			digester.push( this);
			digester.addObjectCreate( "toolbox/tool", VelocityToolInfo.class);
			digester.addBeanPropertySetter( "toolbox/tool/key");
			digester.addBeanPropertySetter( "toolbox/tool/scope");
			digester.addBeanPropertySetter( "toolbox/tool/class","clazz");
			digester.addSetNext( "toolbox/tool", "addTool");
			/*生成velocty帮助类的列表*/
			digester.parse(input);
			for(Iterator<VelocityToolInfo> it = toolinfo.iterator();it.hasNext();)
			{
				VelocityToolInfo vti= it.next();
				context.put(vti.getKey(), Class.forName(vti.getClazz()).newInstance());
			}
		} catch (Exception e) {
			log.error("loadVeloctyTool error",e);
		}finally{
			//updated by yanghb20090116
			try{
				if(input!=null){
					input.close();
				}
			}catch(Exception e){
				log.error("com.hs.dolphin.sys.utils.VelocityTool:WEB-INF/toolbox.xml 关闭错误");
			}
		}
		

        
	}
	
	
	@SuppressWarnings("unchecked")
	/**
　　  *　翻译模板文件
　　  *@param templateFile 需要翻译的模板文件
     *@param targetFile 翻译后的模板文件
     *@param ct 翻译模板文件的上下文参数
     *@param initTool 是否加载veloctiy工具类
	 **/
	public void translateFile(String templateFile, String targetFile,
			Map<String, Object> ct,Boolean initTool ) {
		try {
			String templateString = translateString(templateFile,ct,initTool);
		    OutputStream os1= new FileOutputStream(targetFile);
		    //设置输出文件的编码方式
	        OutputStreamWriter osw1 = new OutputStreamWriter(os1,"UTF-8");
	        BufferedWriter fw1=new BufferedWriter(osw1);
	        fw1.write(new String(templateString.getBytes("UTF-8"),"UTF-8"));
	        fw1.flush();
	        fw1.close();
		} catch (IOException e) {
			log.error("translateFile error",e);
			throw new ResourceNotFoundException("无法加载目标文件");
		}
	}
	
	public void translateFile(String templateFile, String targetFile,
			Map<String, Object> ct) {
		translateFile(templateFile,targetFile,ct,Boolean.TRUE);
	}
	/**
　　  *翻译模板文件到对应的字符串
　　  *@param templateFile 需要翻译的模板文件
     *@param ct 翻译模板文件的上下文参数
     *@param initTool 是否加载veloctiy工具类
     *@return 返回的翻译后的模板字符
	**/
	@SuppressWarnings("unchecked")
	public String translateString(String templateFile,Map<String, Object> ct,Boolean initTool ){
		Template t;
		try {
			t = ve.getTemplate(templateFile);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("无法加载模板文件");
		}
		
		for (Iterator it = ct.entrySet().iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			context.put((String) entry.getKey(), entry.getValue());
		}
		//加载veloctiy帮助类
		if(initTool.booleanValue())
		{
	    	loadVeloctyTool();
		}
	   	try {
			StringWriter sw = new StringWriter();
			//解析模板文件
			t.merge(context, sw);
	        String rString = new String(sw.toString().getBytes("UTF-8"),"UTF-8");
	        sw.close();
	        return rString;
			
		} catch (IOException e) {
			log.error("translateFile error",e);
			return "";
		}
	}
	
	

}
