<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
	<title>部门管理</title>
	<%@ include file="/commons/meta.jsp" %>
	<script type="text/javascript" src="selectManager.js"></script>
	<script type="text/javascript" src="department.js"></script>

<script type="text/javascript">
Ext.onReady(function(){
    var deptManager,btn,deptManagerWin;
    var deptManager= new Department({
        	treeUrl: Ext.getDom('root').value + DepartmentURL.treeUrl,
    		title: '部门管理',
    		winId: 'win1',
    		formUrl: DepartmentURL.formUrl
    });
    var viewPort =deptManager.showViewPort();
    viewPort.show();
    checkButonAuthor();

});
</script>

</head>
<body>
	<input type="hidden" name="root" id="root" value="<%=ctx%>" />
</body>
</html>