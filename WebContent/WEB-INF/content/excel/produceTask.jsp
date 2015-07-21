<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<jsp:useBean id="now" class="java.util.Date" />

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
	<head>
	    <%@ include file="/commons/excelCommons.jsp"%>
	</head>
	<body>
    <div class="sheet">
        <table class="tb" cellspacing="0" cellpadding="0" border="0" >
        	<tr>
        		<td colspan="9" class="title">生产任务单</td>
        	</tr>
        	<tr>
        		<td colspan="3">订单编号：${order.orderNo}</td>
        		<td colspan="3">客户编号：${order.customNo}</td>
        		<td colspan="3">客户名称：${order.customName}</td>
        	</tr>
        	<tr>
        		<td colspan="3">发货日期：<fmt:formatDate value="${order.planDeliveryDate}" type="both" dateStyle="long" pattern="yyyy-MM-dd" /></td>
        		<td colspan="6">发货地址：${order.deliveryAddress}</td>
        	</tr>
        	<tr>
        		<td colspan="3">订单金额：<fmt:formatNumber value="${order.orderPrice}" type="number" pattern="#,#00.00#" />元</td>
        		<td colspan="6">经办人：${order.officer}</td>
        	</tr>
            <tr>
                <th>序号</th>
				<th>产品编号</th>
				<th>产品名称</th>
				<th>规格</th>
				<th>单位</th>
				<th>单价(元)</th>
				<th>数量</th>
				<th>合计(元)</th>
				<th>备注</th>
            </tr>
            <c:forEach items="${detailList}" var="detail" varStatus="status" >
	            <tr>
	                <td class="center">${status.index+1}</td>
					<td>${detail.productNo}</td>
					<td>${detail.productName}</td>
					<td>${detail.standard}</td>
					<td class="center">${detail.unit}</td>
					<td class="right"><fmt:formatNumber value="${detail.price}" type="number" pattern="#,#00.00#" /></td>
					<td class="right">${detail.planNumber}</td>
					<td class="right"><fmt:formatNumber value="${detail.price*detail.planNumber}" type="number" pattern="#,#00.00#" /></td>
					<td>&nbsp;</td>
	            </tr>
			</c:forEach>
        </table>
    </div>
	</body>
</html>