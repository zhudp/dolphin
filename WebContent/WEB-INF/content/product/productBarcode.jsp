<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="/tld/xTag" %>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" " http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>产品条码打印</title>
	</head>
	<body>
	<div style="width:100%;margin:0 auto;">
		<c:forEach begin="1" end="5">
			<table style="margin:10px auto">
				<tr>
					<td><img src="<%=ctx%>/barcode?fmt=jpeg&type=code128&msg=${product.productNo}&height=10&hrsize=1.7mm" width=350px/></td>
				</tr>
				<tr>
					<td style="text-indent:30px;font-size:13px;text-align:left;">${product.productName}(${product.standard})</td>
				</tr>
			</table>
		</c:forEach>
	</div>
	</body>
</html>