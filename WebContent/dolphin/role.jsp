<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
	<title>功能角色管理</title>
	<%@ include file="/commons/meta.jsp" %>
	<script type="text/javascript" src="<%=ctx%>/dolphin/TreeNodeWithCheckboxUI.js"></script>
	<script type="text/javascript" src="<%=ctx%>/dolphin/roleResourceAssignment.js"></script>
	<script type="text/javascript" src="<%=ctx%>/dolphin/role.js"></script>
	<script type="text/javascript">
		Ext.onReady(function(){
			var roleBar = common.RoleBar();
			var roleGrid = common.RoleGrid();
			roleBar.init({grid: roleGrid});
			checkButonAuthor();
		});
	</script>
</head>
<body>
<input type="hidden" name="root" id="root" value="<%=ctx%>" />
</body>
</html>