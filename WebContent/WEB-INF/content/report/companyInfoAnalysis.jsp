<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
	<title>企业信息分析</title>
	<%@ include file="/commons/meta.jsp" %>
	<script type="text/javascript" src="<%=ctx%>/resources/FusionCharts/FusionCharts.js"></script>
	<script type="text/javascript" src="<%=ctx%>/report/companyInfoAnalysis.js"></script>
	<script type="text/javascript">
		Ext.onReady(function(){
			analysis.company.View("<%=ctx%>");
		});
    </script>
</head>
<body>
	<input type="hidden" name="root" id="root" value="<%=ctx%>" />
	<div id ='divChart'></div>
</body>
</html>