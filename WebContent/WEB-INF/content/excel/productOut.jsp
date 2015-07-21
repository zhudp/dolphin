<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<jsp:useBean id="now" class="java.util.Date" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
	<head>
	<title>原料出库单导出</title>
	    <%@ include file="/commons/excelCommons.jsp"%>
	</head>
	<body>
    <div class="sheet">
        <table class="tb" cellspacing="0" cellpadding="0" border="0" >
        	<tr>
        		<td colspan="9" class="title">原料出库单</td>
        	</tr>
        	<tr>
        		<td >订单号：</td><td colspan = "2">${stockOut.orderNo}</td>
        		<td >出库日期：</td><td ><fmt:formatDate value="${stockOut.stockOutDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" /></td>
        		<td>领用车间：</td><td >${stockOut.departmentName}</td>
        		<td>领用人：</td><td >${stockOut.officer}</td>
        	</tr>
            <tr>
                <th>序号</th>
				<th>原料编号</th>
				<th>原料名称</th>
				<th>规格</th>
				<th>单位</th>
				<th>数量</th>
				<th>单价(元)</th>
				<th>合计</th>
				<th>备注</th>
            </tr>
            <c:forEach items="${stockOutDetailList}" var="stockOutDetail" varStatus="status" >
	            <tr>
	                <td>${status.index+1}</td>
					<td>${stockOutDetail.materialNo}</td>
					<td style="text-align:left;">${stockOutDetail.materialName}</td>
					<td>${stockOutDetail.standard}</td>
					<td>${stockOutDetail.unit}</td>
					<td style="text-align:right;">${stockOutDetail.quantity}</td>
					<td>${stockOutDetail.price}</td>
					<td>${stockOutDetail.price*stockOutDetail.quantity}</td>
					<td>&nbsp;</td>
	            </tr>
			</c:forEach>
        </table>
    </div>
	</body>
</html>