package com.dolphin.spring.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

public class DolphinUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
		HttpServletResponse response) throws AuthenticationException {

	    String username = obtainUsername(request);
	    String password = obtainPassword(request);

	    if (username == null) {
	      username = "";
	    }

	    if (password == null) {
	      password = "";
	    }

	    username = username.trim();

	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

	    HttpSession session = request.getSession(false);

	    if ((session != null) || (getAllowSessionCreation())) {
	      request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", TextEscapeUtils.escapeEntities(username));
	    }

	    setDetails(request, authRequest);

	    return getAuthenticationManager().authenticate(authRequest);
	}

}
