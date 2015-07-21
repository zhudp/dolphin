<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
String produceId = request.getParameter("produceId");
if(produceId == null || "".equals(produceId)){
	produceId = "''";
}
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
	<head>
		<title>生产任务管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="<%=ctx%>/produce/ProduceTaskMng.js"></script>
		<script type="text/javascript" src="<%=ctx%>/produce/ProduceTaskCard.js"></script>
		<script type="text/javascript" src="<%=ctx%>/produce/ProduceTaskDetailPanel.js"></script>
		<script type="text/javascript" src="<%=ctx%>/resources/ext/ux/SearchField.js"></script>
		<script type="text/javascript" src="<%=ctx%>/resources/ext/ux/GroupSummary.js"></script>
		<script type="text/javascript" src="<%=ctx%>/custom/CustomChooser.js"></script>
		<script type="text/javascript" src="<%=ctx%>/order/OrderChooser.js"></script>
		<script type="text/javascript" src="<%=ctx%>/product/ProductChooser.js"></script>
		<script type="text/javascript" src="<%=ctx%>/resources/ext/ux/GroupHeaderPlugin.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/ext/ux/GroupHeaderPlugin.css" />
		<script type="text/javascript">
			var initTaskId = <%= produceId%>
			Ext.onReady(function(){
				var mainPanel= new produce.manage.MainPanel();
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