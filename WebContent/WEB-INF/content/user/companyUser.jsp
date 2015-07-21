<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns=" http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
	<title>用户管理</title>
	<%@ include file="/commons/meta.jsp" %>
	<script type="text/javascript" src="<%=ctx%>/user/companyUser.js"></script>
	<script type="text/javascript" src="<%=ctx%>/company/companyChooser.js"></script>
	<script type="text/javascript">
		Ext.onReady(function(){
			company.user.View();
		});
    </script>
</head>
<body>
	<input type="hidden" name="root" id="root" value="<%=ctx%>" />
</body>
</html>