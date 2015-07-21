<%@ page isELIgnored="false"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/ext/plugins/FileUploadField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/ext/plugins/ExcelUploadField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/ext/plugins/ImageUploadField.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/ext/plugins/css/fileuploadfield.css" />
<style type=text/css>
.upload-icon {
	background:
		url('<%=request.getContextPath()%>/resources/hs/icons/folder_add.png') no-repeat 0 0 !important;
}
.upload-icon-excel {
	background:
		url('<%=request.getContextPath()%>/resources/hs/icons/page_excel.png') no-repeat 0 0 !important;
}
.upload-icon-image {
	background:
		url('<%=request.getContextPath()%>/resources/hs/icons/picture_add.png') no-repeat 0 0 !important;
}
</style>
