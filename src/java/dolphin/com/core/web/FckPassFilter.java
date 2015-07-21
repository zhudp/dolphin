package com.core.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class FckPassFilter extends StrutsPrepareAndExecuteFilter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String path = request.getServletPath();
		if ("/editor/filemanager/browser/default/connectors/jsp/connector".equals(path)
				|| "/editor/filemanager/upload/simpleuploader".equals(path)) {
			chain.doFilter(req, res);
		} else {
			super.doFilter(req, res, chain);
		}

	}

}
