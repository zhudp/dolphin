<%String ctx = request.getContextPath();%>
<%@ taglib prefix="x" uri="/tld/xTag" %>
<%@ page import="com.hundsun.commons.tag.ExtendJSTLTag" %>
var comboJsonData = {};
<%
	String[] datadictType = {
		"SEX",
		"USER_TYPE",
		"USER_STATUS",
		"OPER_TYPE",
		"ID_CODE_STATUS",
		"POLICITAL_STATUS",
		"MARITAL_STATUS",
		"EDUCATION",
		"TECHNICAL_GRADE",
		"LIVE_CAUSE",
		"PLACE_TYPE",
		"CONTRACT",
		"WAGES_ONTIME",
		"COUPLE_TOGETHER",
		"PREGNANT_INFO",
		"CARDS_TYPE",
		"NATION",
		"RELATIONS",
		"YESORNO",
		"COMPANY_TYPE",
		"STRUCTURE_TYPE",
		"USE_TYPE",
		"SEPARATE_TYPE",
		"CHILDREN_RELATION",
		"COMPANY_STATUS",
		"ALERT_TYPE",
		"CUSTOM_CATEGORY",
		"INSPECT_OBJECT_TYPE",
		"REPLY_STATUS",
		"HELP_TYPE",
		"LIVE_CARD_STATUS",
		"ISNORMAL",
		"IS_ACTIVITY",
		"JOB",
		"TAX_PAY_STATUS",
		"NUMBER_STATUS",
		"LEAVE_TYPE"
	};
	for(int i = 0; i < datadictType.length; i++) {
		out.println("comboJsonData." + datadictType[i] + "=" + ExtendJSTLTag.buildComboSelect(datadictType[i],null)+";");
	}
%>
