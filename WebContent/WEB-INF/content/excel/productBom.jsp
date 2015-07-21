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
        		<td colspan="9" class="title">产品BOM清单</td>
        	</tr>
        	<tr>
        		<td colspan="3">产品编号：${product.productNo}</td>
        		<td colspan="6">产品名称：${product.productName}</td>
        	</tr>
            <tr>
                <th>序号</th>
				<th>部件/原材料名称</th>
				<th>编号</th>
				<th>规格</th>
				<th>单位</th>
				<th>数量</th>
				<th>单位重量</th>
				<th>单位面积</th>
				<th>备注</th>
            </tr>
            <c:set var="cnt" value="0"></c:set>
            <c:forEach items="${dataList}" var="detail" >
	            <tr>
		            <c:set var="cnt" value="${cnt+1}"></c:set>
	                <td class="center">${cnt}</td>
					<td>${detail.bomType=="material"?"--":""}${detail.name}</td>
					<td>${detail.no}</td>
					<td>${detail.standard}</td>
					<td class="center">${detail.unit}</td>
					<td class="right">${detail.number}</td>
					<td class="right">${detail.weight}</td>
					<td class="right">${detail.area}</td>
					<td class="right">${detail.remark}</td>
	            </tr>
	            <c:forEach items="${detail.children}" var="material" >
		            <tr>
		                <c:set var="cnt" value="${cnt+1}"></c:set>
	                	<td class="center">${cnt}</td>
						<td>&nbsp;--&nbsp;${material.name}</td>
						<td>${material.no}</td>
						<td>${material.standard}</td>
						<td class="center">${material.unit}</td>
						<td class="right">${material.number}</td>
						<td class="right">${material.weight}</td>
						<td class="right">${material.area}</td>
						<td class="right">${material.remark}</td>
		            </tr>
	            </c:forEach>
			</c:forEach>
        </table>
    </div>
	</body>
</html>