<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<jsp:useBean id="now" class="java.util.Date" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
	<head>
	<title>原料入库单导出</title>
	    <%@ include file="/commons/excelCommons.jsp"%>
	</head>
	<body>
    <div class="sheet">
        <table class="tb" cellspacing="0" cellpadding="0" border="0" >
        	<tr>
        		<td colspan="9" class="title">原料入库单</td>
        	</tr>
        	<tr>
        		<td>供应商</td><td colspan = "2">${stockIn.supplierOfficer}&nbsp</td>
        		<td >采购单号：</td><td colspan = "2">${stockIn.supplierNo}</td>
        		<td >下单日期：</td><td colspan = "2"><fmt:formatDate value="${stockIn.ordersDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" /></td>
        	</tr>
        	<tr>
        		<td>供应商负责人：</td><td colspan = "2">${stockIn.supplierOfficer}</td>
        		<td>采购负责人：</td><td colspan = "2">${stockIn.purchaseOfficer}</td>
        		<td>入库日期：</td><td colspan = "2"><fmt:formatDate value="${stockIn.stockInDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" /></td>
        	</tr>
            <tr>
                <th>序号</th>
				<th>原料编号</th>
				<th>原料名称</th>
				<th>单位</th>
				<th>计划数量</th>
				<th>实际数量</th>
				<th>单价(元)</th>
				<th>合计</th>
				<th>备注</th>
            </tr>
            <c:forEach items="${stockInDetailList}" var="stockInDetail" varStatus="status" >
	            <tr>
	                <td>${status.index+1}</td>
					<td>${stockInDetail.materialNo}</td>
					<td>${stockInDetail.unit}</td>
					<td style="text-align:left;">${stockInDetail.materialName}</td>
					<td>${stockInDetail.planQuantity}</td>
					<td style="text-align:right;">${stockInDetail.realQuantity}</td>
					<td>${stockInDetail.price}</td>
					<td>${stockInDetail.price*stockInDetail.realQuantity}</td>
					<td>&nbsp;</td>
	            </tr>
			</c:forEach>
        </table>
    </div>
	</body>
</html>