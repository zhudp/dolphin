<%@ page contentType="text/html; charset=UTF-8"%><%@ page import="com.core.exception.*"%><%
BusinessException b = (BusinessException)request.getAttribute("exception");
%><%=b==null?"":b.getMessage()%>