<%String ctx = request.getContextPath();%>
<%@ taglib prefix="x" uri="/tld/xTag" %>
<%@ page import="com.hundsun.commons.tag.ExtendJSTLTag" %>
<%@ page import="com.hundsun.commons.tag.AreaJSTLTag" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/ext/resources/css/ext-all.css" /> 
<link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/hs/css/hs.css" /> 
<link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/ui/default/ui.css" /> 
<script type="text/javascript" src="<%=ctx%>/resources/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/ext/ext-all-debug.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/ext/local/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/ext/plugins/PageComboResizer.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/hs/namespace.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/hs/common.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/hs/factory.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/hs/helper.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/hs/layout.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/hs/constants.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/hs/resource.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/ext.ux/TipMsg.js"></script>
<script type="text/javascript">
	var comboJsonData = function() {};
	<%
		String[] datadictType = {
			"SEX",
			"USER_TYPE",
			"USER_STATUS",
			"OPER_TYPE",
			"CUSTOM_TYPE",
			"CUSTOM_STATUS",
			"CUSTOM_INDUSTRY",
			"PRODUCT_TYPE",
			"PRODUCT_STATUS",
			"ORDER_STATUS",
			"MATERIAL_TYPE",
			"SUPPLIER_TYPE",
			"PURCHASE_TYPE",
			"PURCHASE_STATUS",
			"TASK_STATUS",
			"ISWORK",
			"MARITAL_STATUS",
			"USUAL_UNIT",
			"EDUCATION",
			"MATERIAL_KIND"
		};
		for(int i = 0; i < datadictType.length; i++) {
			out.println("comboJsonData." + datadictType[i] + "=" + ExtendJSTLTag.buildComboSelect(datadictType[i],null) + ";");
		}
	%>



	Ext.BLANK_IMAGE_URL = '<%=ctx%>/resources/ext/images/default/s.gif';
	    
	var myWaitMask;
	function showWaitMask(){
		myWaitMask = new Ext.LoadMask(Ext.getBody(),{msg:"Please Wait ...",msgCls:"x-mask-loading"});
		myWaitMask.show();
	};
	
</script>
<style>
del{
color: red;
}
.red{color: red;font-weight: bold;}
.green{color: green;font-weight: bold;}
</style>