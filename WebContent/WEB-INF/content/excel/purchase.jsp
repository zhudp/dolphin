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
        		<td colspan="9" class="title">采购单</td>
        	</tr>
        	<tr>
        		<td>采购编号：</td><td colspan = "2">${purchase.purchaseNo}</td>
        		<td>采购名称：</td><td>${purchase.purchaseName}</td>
        		<td>采购类别：</td><td>${purchase.purchaseType}</td>
        		<td>采购状态：</td><td>${purchase.status}</td>
        	</tr>
        	<tr>
        		<td>供应商：</td><td  colspan = "2">${purchase.supplierName}</td>
        		<td>负责人：</td><td>${purchase.officer}</td>
        		<td>采购日期：</td><td >${purchase.orderDate}</td>
        		<td>计划入库日期：</td><td >${purchase.planStoreinDate}</td>
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
            <c:forEach items="${purchaseDetailList}" var="purchaseDetail" varStatus="status" >
	            <tr>
	                <td>${status.index+1}</td>
					<td>${purchaseDetail.materialNo}</td>
					<td style="text-align:left;">${purchaseDetail.materialName}</td>
					<td>${purchaseDetail.standard}</td>
					<td>${purchaseDetail.unit}</td>
					<td style="text-align:right;">${purchaseDetail.planNumber}</td>
					<td>${purchaseDetail.price}</td>
					<td>${purchaseDetail.price*purchaseDetail.planNumber}</td>
					<td>&nbsp;</td>
	            </tr>
			</c:forEach>
        </table>
    </div>
	</body>
</html>