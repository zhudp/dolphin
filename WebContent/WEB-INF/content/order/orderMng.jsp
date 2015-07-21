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

String customNo = request.getParameter("customNo");
if(customNo == null || "".equals(customNo)){
	customNo = "";
}
customNo = "'"+customNo+"'";
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
	<head>
		<title>产品管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<%@ include file="/commons/fileUpload.jsp"%>
		<script type="text/javascript" src="<%=ctx%>/order/OrderMng.js"></script>
		<script type="text/javascript" src="<%=ctx%>/order/OrderCard.js"></script>
		<script type="text/javascript" src="<%=ctx%>/order/OrderDetailPanel.js"></script>
		<script type="text/javascript" src="<%=ctx%>/resources/ext/ux/SearchField.js"></script>
		<script type="text/javascript" src="<%=ctx%>/resources/ext/ux/GroupSummary.js"></script>
		<script type="text/javascript" src="<%=ctx%>/custom/CustomChooser.js"></script>
		<script type="text/javascript" src="<%=ctx%>/product/ProductChooser.js"></script>
		<script type="text/javascript" src="<%=ctx%>/commons/UploadImageWindow.js"></script>
		<%@ include file="/commons/fileUpload.jsp"%>
		<script type="text/javascript" src="<%=ctx%>/file/FileListPanel.js"></script>
		<script type="text/javascript" src="<%=ctx%>/commons/UploadFileWindow.js"></script>
		<script type="text/javascript" src="<%=ctx%>/resources/ext/ux/GroupHeaderPlugin.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/ext/ux/GroupHeaderPlugin.css" />
		<script type="text/javascript">
			var initCustomId = <%= customId%>
			var initCustomName = <%= customName%>
			var initCustomNo = <%= customNo%>
			Ext.onReady(function(){
				var mainPanel= new order.manage.MainPanel();
			    mainPanel.showViewPort();
			    checkButonAuthor();
			});
		</script>
		
		<style>
		.stock_area {   
		    background-color: #FFE7BA;
		}
	</style>
		
	</head>
	<body>
		<input type="hidden" name="root" id="root" value="<%=ctx%>" />
	</body>
</html>