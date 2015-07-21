<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/commons/meta.jsp"%>
<html>
<head>
	<title>系统维护公告</title>
	<script type="text/javascript" src="<%=ctx%>/dolphin/sysUpdateLog.js"></script>
	<script type="text/javascript">
		Ext.onReady(function(){
			dolphin.sysUpdateLog.View();
		});
    </script>
</head>
<body>
	<input type="hidden" name="root" id="root" value="<%=ctx%>" />
</body>
</html>

