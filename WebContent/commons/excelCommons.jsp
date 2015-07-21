<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%
String path = request.getContextPath();
String ctx = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String slogan = "天行健，君子以自强不息  地势坤，君子以厚德载物";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
    .sheet .tb tr{
        text-align: left;
        vertical-align: middle;
    }
    .sheet .tb td.center{
        text-align: center;
    }
    .sheet .tb td.right{
        text-align: right;
    }
    .sheet .tb th,.sheet .tb td{
        border: 0.5pt solid #000;
        height:30px;
    }
    .sheet .header th{
        font-size: 12pt;
    }
    .sheet .tb .logo{
    	height:60px;
    	text-align:right;
    	vertical-align:bottom;
    	color:teal;
    }
    .sheet .tb .title{
    	height:40px;
    	border-color: -moz-use-text-color windowtext windowtext -moz-use-text-color;
		border-style: none solid solid none;
		border-width: medium 0.5pt 0.5pt medium;
		font-size: 18pt;
		font-weight: 700;
		text-align: center;
    }
</style>