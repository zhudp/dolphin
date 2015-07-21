<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<jsp:useBean id="now" class="java.util.Date" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
	<head>
	<title>产品入库单导出</title>
	    <%@ include file="/commons/excelCommons.jsp"%>
	</head>
	<body>
    <div class="sheet">
        <table class="tb" cellspacing="0" cellpadding="0" border="0" >
        	<tr>
        		<td colspan="9" class="title">产品入库单</td>
        	</tr>
        	<tr>
        		<td >入库编号</td><td colspan = "3">${stockIn.stockInNo}&nbsp</td>
        		<td >单号：</td><td colspan = "3">${stockIn.orderNo}</td>
        	</tr>
        	<tr>
        		<td >下单日期：</td><td colspan = "2"><fmt:formatDate value="${stockIn.ordersDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" /></td>
        		<td >入库日期：</td><td colspan = "2"><fmt:formatDate value="${stockIn.stockInDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" /></td>
        		<td >经办人：</td><td >${stockIn.officer}</td>
        	</tr>
            <tr>
                <th>序号</th>
				<th>产品编号</th>
				<th>产品名称</th>
				<th>规格</th>
				<th>单位</th>
				<th>计划数量</th>
				<th>实际数量</th>
				<th>备注</th>
            </tr>
            <c:forEach items="${stockInDetailList}" var="stockInDetail" varStatus="status" >
	            <tr>
	                <td>${status.index+1}</td>
					<td>${stockInDetail.productNo}</td>
					<td style="text-align:left;">${stockInDetail.productName}</td>
					<td>${stockInDetail.standard}</td>
					<td>${stockInDetail.unit}</td>
					<td>${stockInDetail.planQuantity}</td>
					<td style="text-align:right;">${stockInDetail.realQuantity}</td>
					<td>&nbsp;</td>
	            </tr>
			</c:forEach>
        </table>
    </div>
	</body>
</html>