<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
	<title>条形码测试</title>
</head>
<body id="wrap">
	<img src="<%=ctx%>/barcode?fmt=jpeg&type=code128&msg=1234567890&height=8"  width=400px/>
</div> 
</body>
</html>

