package com.core.web;

import org.apache.log4j.Logger;

/**
 * Web端Controller异常处理.
 * <p>
 * 对业务异常和代码异常进行了区分。对于业务异常，Action层和Service层可直接抛BusinessException异常，并声明异常消息，在此抓获业务异常消息后向前台传递.
 * <p>
 * 当前使用Ext消息传递方式.
 *
 * @author: wanglf
 * @since: Jan 16, 2008 1:00:26 PM
 * @history:update by lilq
 */
public abstract class ExceptionHandle {
	protected final Logger log = Logger.getLogger(this.getClass().getName());

	/*public ActionForward execute(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) {
		try {
			ActionForward success = run();
			return success;
		} catch (BusinessException e) {
		    log.debug("业务异常单条信息提示：" + e);
			return handleException(mapping, request, response, e, e
					.getMessage(),"FALSE");
		} catch (MultiBusinessException e) {
			log.debug("业务异常多条信息提示：" + e);
			return handleException(mapping, request, response, e, e
					.getMessage(),"TRUE");
		} catch (Exception e) {
			e.printStackTrace();
			return handleException(mapping, request, response, e,
					"系统发生异常，请刷新页面或重新登陆后重试；如问题依然存在，请与管理员联系！","FALSE");
		} finally {
			afterExcetue(mapping, form, request, response);
		}
	}

	protected ActionForward handleException(ActionMapping mapping,
			final HttpServletRequest request,
			final HttpServletResponse response, Exception e, String message,String multi) {
		response.setCharacterEncoding("utf-8");

		// 如果客户端是Grid请求，则需设置response状态

		String formOrGrid = request.getParameter("formOrGrid");
		if (StringUtils.isNotBlank(formOrGrid) && formOrGrid.equals("grid")) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else if (StringUtils.isNotBlank(formOrGrid)
				&& formOrGrid.equals("form")) {
			if ("TRUE".equals(multi)) {
				message = WebUtils.getExtMultiFailureMessage(message);
			  }else {
			   message = WebUtils.getExtFailureMessage(message);
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

		return null;
	}

	protected abstract ActionForward run() throws Exception;

	protected void afterExcetue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	}*/

}
