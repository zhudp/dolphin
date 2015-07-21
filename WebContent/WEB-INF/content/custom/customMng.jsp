<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
	<head>
		<title>客户管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="<%=ctx%>/custom/CustomMng.js"></script>
		<script type="text/javascript" src="<%=ctx%>/custom/CustomCard.js"></script>
		<%@ include file="/commons/fileUpload.jsp"%>
		<script type="text/javascript" src="<%=ctx%>/file/FileListPanel.js"></script>
		<script type="text/javascript" src="<%=ctx%>/commons/UploadFileWindow.js"></script>
		<script type="text/javascript">
			Ext.onReady(function(){
				var mainPanel= new custom.manage.MainPanel();
			    mainPanel.showViewPort();
			    checkButonAuthor();
			});
		</script>
		
	</head>
	<body>
		<input type="hidden" name="root" id="root" value="<%=ctx%>" />
	</body>
</html>