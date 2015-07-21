package com.dolphin.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.exception.BusinessException;
import com.core.exception.MultiBusinessException;
import com.core.utils.WebUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/*******************************************************************************
 * @author: yanghb
 * @since: 2009-5-14 下午07:25:42
 * @history: ***********************************************
 * @file: ExceptionInterceptor.java
 * @Copyright: 2008 HundSun Electronics Co.,Ltd. All right reserved.
 ******************************************************************************/
@SuppressWarnings("serial")
public class ExceptionInterceptor extends AbstractInterceptor {
	protected static final Logger logger = LoggerFactory
			.getLogger(ExceptionInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = "error";
		try {
			result = invocation.invoke();
		} catch (BusinessException ex) {
//			ex.printStackTrace();
			logger.error(ex.toString());
			handleException(ex.getMessage(),true);
	    } catch (MultiBusinessException ex) {
//		  ex.printStackTrace();
		   logger.error(ex.toString());
		   handleException(ex.getMessage(),false);
	    } catch (org.springframework.dao.DuplicateKeyException ex) {
			logger.error(ex.toString());
			int s = ex.getMessage().lastIndexOf("for key");
			handleException("本该唯一的值发生重复" + ex.getMessage().substring(s).replace("'", ""), true);
		}
	    catch(Exception ex){
	    	 logger.error(ex.toString());
			 handleException("系统内部异常,请联系系统管理员!",true);
	    }
		return result;
	}
	/**
	 *  处理业务异常消息，仅处理ajax请求调用，非Ajax请求，未作解决。
	 * @param message
	 * @param isSingle 是否多条消息
	 */
	//TODO 该方法还需改进
	private void handleException(String message ,boolean isSingle){
		HttpServletResponse response= ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request instanceof MultiPartRequestWrapper){
			response.setContentType("text/html;charset=UTF-8");
		}
		else {
			response.setContentType("text/x-json;charset=UTF-8");
		}
		// 如果客户端是Grid请求，则需设置response状态
		String formOrGrid = request.getParameter("formOrGrid");
		if (StringUtils.isNotBlank(formOrGrid) && formOrGrid.equals("grid")) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else if (StringUtils.isNotBlank(formOrGrid)) {
			if (isSingle) {
				 message = WebUtils.getExtFailureMessage(message);
			  }else {
				  message = WebUtils.getExtMultiFailureMessage(message);
			}
		
		}
		// 使用ajax提交（已不考虑页面跳转）
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(message);
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}