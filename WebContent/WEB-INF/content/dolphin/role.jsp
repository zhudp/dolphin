<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
	<title>功能角色管理</title>
	<%@ include file="/commons/meta.jsp" %>
	<script type="text/javascript" src="<%=ctx%>/dolphin/TreeNodeWithCheckboxUI.js"></script>
	<script type="text/javascript" src="<%=ctx%>/dolphin/roleResourceAssignment.js"></script>
	<script type="text/javascript" src="<%=ctx%>/dolphin/role.js"></script>
	<script type="text/javascript">
		Ext.onReady(function(){
		  var role= new dolphin.role.Role({});
	      var viewPort =role.showViewPort();
	      viewPort.show();
	      checkButonAuthor();
		});
	</script>
</head>
<body>
<input type="hidden" name="root" id="root" value="<%=ctx%>" />
</body>
</html>