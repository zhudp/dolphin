<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name></x:Name><x:WorksheetOptions><x:Selected/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->
    <style type="text/css">
        .td{
            width: 84px;
        }
        .sheet .tb tr{
            text-align: center;
            vertical-align: middle;
        }
        .sheet .tb th,.sheet .tb td{
            border: 0.5pt solid #000;
            height:30px;
        }
        .sheet .header th{
            font-size: 12pt;
        }
    </style>
</head>
<body>
    <div class="sheet">
        <table class="tb" cellspacing="0" cellpadding="0" border="0" >
            <tr style="height: 40px">
                <th colspan="3">${param.caption}</th>
            </tr>
            <tr>
                <th>编号</th>
                <th>${param.xAxisName}</th>
                <th>${param.yAxisName}</th>
            </tr>
            <c:forEach items="${page.data}" var="data" varStatus="status" >
	            <tr>
	                <td>${status.index+1}</td>
	                <td style="text-align:left;">${data.LABEL}</td>
	                <td style="text-align:right;">${data.ANALYSIS_COUNT}</td>
	            </tr>
			</c:forEach>
        </table>
    </div>
</body>
</html>