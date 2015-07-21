<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.dolphin.domain.RemoteUser" %>
<%@ page import="com.dolphin.domain.User" %>
<% 
	User user = RemoteUser.get();
	String account = user.getAccount();
	String deptName = user.getDepartment().getDeptName();
	String userName = user.getUserName();
	String str = "";
	if(userName==null||"".equals(userName)){
		str = deptName + "，" + account;
	}else{
		str = deptName + "，" + userName;
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<title>商业展示企业管理系统</title>
<%
String ctx = request.getContextPath();
%>
	<script type="text/javascript">
		var docs_index_page = '<%=ctx%>/dolphin/welcome.action';
		<%="this_user_CommunityId =" + user.getCommunityId()+";"%>
		var isInitPwd = ${isInitPwd};
	</script>
	<%@ include file="/commons/ui.jsp" %>
	<link rel="stylesheet" type="text/css"	href="<%=ctx%>/resources/index/docs.css"></link>
	<link rel="stylesheet" type="text/css"	href="<%=ctx%>/resources/index/style.css"></link>
	<link rel="stylesheet" type="text/css"	href="<%=ctx%>/resources/ext/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css"	href="<%=ctx%>/resources/hs/css/hs.css" />
	<script type="text/javascript"	src="<%=ctx%>/resources/ext/adapter/ext/ext-base-debug.js"></script>
	<script type="text/javascript"	src="<%=ctx%>/resources/ext/ext-all-debug.js"></script>
	<script type="text/javascript"	src="<%=ctx%>/resources/ext/local/ext-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/hs/namespace.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/hs/common.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/hs/factory.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/ext.ux/TipMsg.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/hs/helper.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/hs/constants.js"></script>
	<script type="text/javascript"	src="<%=ctx%>/resources/index/TabCloseMenu.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/index/docs.js"></script>
	<script type="text/javascript" src="<%=ctx%>/dolphin/changepwd.js"></script>
	<script type="text/javascript">
		var passWordForm,passWordWin;
		function myLogout() {
			hs.MessageHelper.confirm('  您确定要注销系统吗?',function(){
				location.replace('<%=ctx%>/j_spring_security_logout');
  	    	},'注意');
  	    }
	  	function openWin(){
	  		if(!passWordForm)
	  			passWordForm = new common.ChangepwdForm();
	  		if(!passWordWin)
    			passWordWin = new Ext.Window({
    				title: "密码修改", 
    				width: 340, 
    				height:176,
    				plain:true, 
    				layout:'border',
    				items:[passWordForm.getForm()]
    			});
			passWordWin.show();
	  	}
	  	Ext.onReady(function() {
			//处理session过期，返回到登录页面的问题 
		    Ext.util.Observable.observeClass(Ext.data.Connection);
            Ext.data.Connection.on('requestcomplete', function(con, resp,options) {
                if(resp&&resp.getResponseHeader&&resp.getResponseHeader("sessionstatus")){
            	   location.replace('<%=ctx%>/j_spring_security_logout');
                }
           });	
            
           if(isInitPwd) {
        	   openWin();
           }
		});
	</script>
</head>

<body id="docs">
	<input type="hidden" name="root" id="root" value="<%=ctx%>" />
	<div id="loading-mask" style=""></div>
	<div id="loading">
		<div class="loading-indicator">
			<img src="<%=ctx%>/resources/index/extanim32.gif" width="32" height="32" style="margin-right: 8px;" /> 系统加载中...
		</div>
	</div>
	<%=request.getAttribute("treeMenu")%>
	<div id="header" class="ui_top_banner">
		<div>
			<table border="0" align="right" cellpadding="5" cellspacing="0"	style="font-size: 12px" >
			<tr>
				<td width="400" align="right" style="color: #FF0000;font-size: 16px; padding-right:15px;">
					<span class="bulletin_text">
						<marquee onmouseout="this.start()" onmouseover="this.stop()" scrollamount="3" direction="left">
									${updateLogStr}
						</marquee>
					</span> 
				</td>
				<td>&nbsp;</td>
				<td height="70" align="center" style="color: #FFFFFF"><%=str %>：您好!
					<img src="<%=ctx%>/resources/index/tc2.gif" width="10" height="10"	style="padding-left: 5px" />
				 	&nbsp; 
					<a href="javascript:openWin()"	style="color: #FFFFFF">修改密码</a>
					<img src="<%=ctx%>/resources/index/tc.gif" width="10" height="10" style="padding-left: 5px" /> 
					&nbsp; 
					<a href="javascript:myLogout()"	style="color: #FFFFFF">注销&nbsp;&nbsp;</a>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			</table>
		</div>
	</div>

<!-- keepsession -->
<!--<iframe id="keepsession" name="keepsession" frameborder="0" width="0"
	height="0"></iframe>
<form action="<%=ctx%>/login_keepsession.do" method="post"
	target="keepsession" name="keepsessionform" id="keepsessionform">
</form>
--><!-- END keepsession -->

	<div id="classes"></div>
	<!-- 系统中所有页面主要链接资源 -->
	<input type="hidden" name="mainResource" id="mainResource"	value='<%=request.getAttribute("mainResource")%>' />
	<input type="hidden" name="userResource" id="userResource"	value='<%=request.getAttribute("userResource")%>' />
</body>
</html>
