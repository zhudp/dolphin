<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
	<title>功能角色分配</title>
	<%@ include file="/commons/meta.jsp" %>
	<script type="text/javascript" src="<%=ctx%>/resources/hs/PinyinFilter.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/hs/TreeFilter.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/hs/TreeCombo.js"></script>
	<script type="text/javascript" src="<%=ctx%>/dolphin/userRoleAssign.js"></script>
	<script type="text/javascript" src="<%=ctx%>/dolphin/user.js"></script>
	<script type="text/javascript" src="<%=ctx%>/dolphin/role.js"></script>
	<script type="text/javascript" src="<%=ctx%>/dolphin/department.js"></script>
	<script type="text/javascript" src="<%=ctx%>/area/AreaReseauSelector.js"></script>
	<script type="text/javascript">
		Ext.onReady(function(){
			dolphin.userRole.View();
			checkButonAuthor();
		});
	</script>
</head>
<body>
	<input type="hidden" name="root" id="root" value="<%=ctx%>" />
</body>
</html>