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
        		<td colspan="7" class="title">产品报价单</td>
        	</tr>
        	<tr>
        		<td colspan="7">
        		客户：${requestParam.customName}&nbsp;&nbsp;&nbsp;&nbsp;
        		日期：<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" />
        		</td>
        	</tr>
            <tr>
                <th>序号</th>
				<th>产品编号</th>
				<th>产品名称</th>
				<th>单位</th>
				<th>单价(元)</th>
				<th>合计</th>
				<th>备注</th>
            </tr>
            <c:forEach items="${dataList}" var="product" varStatus="status" >
	            <tr>
	                <td>${status.index+1}</td>
					<td>${product.productNo}</td>
					<td style="text-align:left;">${product.productName}</td>
					<td>${product.unit}</td>
					<td style="text-align:right;"><fmt:formatNumber value="${product.price}" type="number" pattern="#,#00.00#" /></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
	            </tr>
			</c:forEach>
        </table>
    </div>
	</body>
</html>