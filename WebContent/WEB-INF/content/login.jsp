<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.web.WebAttributes"%>
<% String ctx = request.getContextPath(); %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ include file="/commons/ui.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
   
	<title>商业展示道具企业管理系统</title>
	<link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/login/login.css " />
	<link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/ui/default/ui.css" />
	<script src="<%=ctx%>/resources/jquery/jquery.js" type="text/javascript"></script>
	<script src="<%=ctx%>/resources/jquery/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="<%=ctx%>/resources/jquery/validate/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=ctx%>/resources/jquery/validate/messages_cn.js" type="text/javascript"></script>
	
	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
	<script type="text/javascript">
		function refreshCaptcha() {
			$('#captchaImg').hide().attr('src','<%=ctx%>/security/jcaptcha.jpg?' + Math.floor(Math.random()*100)).fadeIn();
		}
	</script>
</head>
<body id="wrap">

<div class="ui_login_area">
	<span>&nbsp;</span>
	 <form id="loginForm" action="<%=ctx%>/j_spring_security_check" method="post" >
		<table id="marginTable" width="300px" height="160px" border="0" cellspacing="0" cellpadding="0"
			style="margin-top:150px; margin-left:340px;">
		<%
		if (request.getAttribute("errorLogin") != null) {
	    %>
	    <tr>
	    <td colspan="3">
	     <div class="error"  align="center"> <%=request.getAttribute("errorLogin") %></div>
	     </td>
	    </tr> 
	    <%
		}
	    %>
		<tr>
			<th>用户名：</th>
			<td colspan="3">
				<input type='text' name='j_username' size='15'  class="input-text"
				<s:if test="not empty param.error">	value='<%=session.getAttribute(WebAttributes.LAST_USERNAME)%>'</s:if> />
			 </td>
			  
		</tr>
		<tr>
			<th>密码：</th>
			<td colspan="3"><input type='password' size='15' name='j_password'  class="input-text"/></td>
			
		</tr>
				<tr>
					<th>验证码：</th>
					<td><input type='text' name='j_captcha' size='5' autocomplete="off" class="input-text" maxlength="4"/></td>
					<td ><img  id="captchaImg" src="<%=ctx%>/security/jcaptcha.jpg"/></td>
					<td ><a href="javascript:refreshCaptcha()">看不清楚换一张</a>
					</td>
				</tr>
				<tr>
					
				</tr>
				<tr>
				    <th ></th>
					<td colspan="2" ><input value="登 录" type="submit" class="btn_login"/></td>
					<td ></td>
				</tr>
			
		</table>
	</form>
</div> 
<script>
if(top.location!=self.location) top.location=self.location;
</script>
</body>
</html>

