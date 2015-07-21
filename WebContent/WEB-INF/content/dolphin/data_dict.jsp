<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN" dir="ltr">
<head>
	<title>数据字典管理</title>
	<%@ include file="/commons/meta.jsp" %>
	<script type="text/javascript" src="<%=ctx%>/dolphin/datadict.js"></script>
	
</head>
<body>
<script type="text/javascript">
	Ext.onReady(function(){
   var chooser= new DataDictChooser({
            treeurl:Ext.getDom("root").value+DataDictURL.treeUrl,
    		formUrl:Ext.getDom('root').value+ DataDictURL.formUrl
    	});
    var viewPort =chooser.showViewPort();
    viewPort.show();
    checkButonAuthor();
 });
	</script>
	<input type="hidden" name="root" id="root" value="<%=ctx%>" />
</body>
</html>