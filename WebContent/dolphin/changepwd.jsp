<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
	<title>修改密码</title>
	<%@ include file="/commons/meta.jsp" %>
	<script type="text/javascript" src="<%=ctx%>/dolphin/changepwd.js"></script>
	<script type="text/javascript" src="<%=ctx%>/dolphin/user.js"></script>
	<script type="text/javascript">
		Ext.onReady(function(){
			var win = common.ChangepwdWin();
			win.show();
			checkButonAuthor();
		});
	</script>
</head>
<body>
	<input type="hidden" name="root" id="root" value="<%=ctx%>" />
</body>
</html>