<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
	<head>
		<title>产品出库管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="<%=ctx%>/resources/ext/ux/SearchField.js"></script>
		<script type="text/javascript" src="<%=ctx%>/commons/CommonMng.js"></script>
		<script type="text/javascript" src="<%=ctx%>/commons/CommonCard.js"></script>
		<script type="text/javascript" src="<%=ctx%>/order/OrderChooser.js"></script>
		<script type="text/javascript" src="<%=ctx%>/product/ProductChooser.js"></script>
		<script type="text/javascript" src="<%=ctx%>/product/ProductStockOutMng.js"></script>
		<script type="text/javascript" src="<%=ctx%>/product/ProductStockOutCard.js"></script>
		<script type="text/javascript" src="<%=ctx%>/product/ProductStockOutDetailMng.js"></script>
		<script type="text/javascript" src="<%=ctx%>/product/ProductStockOutDetailCard.js"></script>
		<script type="text/javascript">
			Ext.onReady(function(){
				var mainPanel= new ProductStockOut.manage.MainPanel({recordIdName:"stockOutId"});
			    mainPanel.showViewPort();
			    checkButonAuthor();
			});
		</script>
	</head>
	<body>
		<input type="hidden" name="root" id="root" value="<%=ctx%>" />
	</body>
</html>