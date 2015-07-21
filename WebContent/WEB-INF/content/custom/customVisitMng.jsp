<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
String customId = request.getParameter("customId");
if(customId == null || "".equals(customId)){
	customId = "''";
}
String customName = request.getParameter("customName");
if(customName == null || "".equals(customName)){
	customName = "";
}
customName = "'"+customName+"'";
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
	<head>
		<title>客户拜访记录管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="<%=ctx%>/custom/CustomVisitMng.js"></script>
		<script type="text/javascript" src="<%=ctx%>/custom/CustomVisitCard.js"></script>
		<script type="text/javascript" src="<%=ctx%>/resources/ext/ux/SearchField.js"></script>
		<script type="text/javascript" src="<%=ctx%>/custom/CustomChooser.js"></script>
		<script type="text/javascript">
			var initCustomId = <%= customId%>
			var initCustomName = <%= customName%>
			Ext.onReady(function(){
				var mainPanel= new custom.visit.MainPanel();
			    mainPanel.showViewPort();
			    checkButonAuthor();
			});
		</script>
		
	</head>
	<body>
		<input type="hidden" name="root" id="root" value="<%=ctx%>" />
	</body>
</html>