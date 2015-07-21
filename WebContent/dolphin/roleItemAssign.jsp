<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.dolphin.domain.roleitem.RoleItem"%>
<%@ page import="com.dolphin.domain.Role"%>
<%@ page import="com.dolphin.service.RoleItemFactory"%>
<%@ page import="java.util.List"%>

<%!int treeMaxLevel;
	String ctxInit;

	// 渲染角色元素到前台.
	private StringBuffer render(List<RoleItem> roleItems) {
		if (roleItems == null)
			return new StringBuffer("");

		StringBuffer sb = new StringBuffer("");

		for (int j = 0; j < roleItems.size(); j++) {
			RoleItem menu = roleItems.get(j);
			sb.append("<tr>");
			for (int k = 0; k <= menu.getLevel().intValue(); k++) {
				sb.append("<td>&nbsp;</td>");
			}
			sb.append("<td align='left'><input type='checkbox' id='tree'");
			sb.append("value='" + menu.getId() + "," + menu.getParentMenu().getId() + "," + menu.getLevel() + "'");
			sb.append("onclick=\"javascript:onTree(this);\"");
			sb.append("width='14' height='15'></td>");

			if (menu.getHasChild() == null)
				sb.append("<td><img src='" + ctxInit + "/resources/index/class.gif' width='16' height='15'></td>");
			else
				sb.append("<td><img src='" + ctxInit + "/resources/index/pkg.gif' width='16' height='15'></td>");
			sb.append("<td colspan='" + (treeMaxLevel - menu.getLevel().intValue() + 1) + "'>" + menu.getText() + "</td></tr>");

			sb.append(render(menu.getSubChildren()));
		}

		return sb;
	}%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<title>功能角色权限分配</title>
		<style>
BODY {
	COLOR: #000000;
	font-size: 12px;
	scrollbar-face-color: #DEE3E7;
	scrollbar-highlight-color: #FFFFFF;
	scrollbar-shadow-color: #DEE3E7;
	scrollbar-3dlight-color: #D1D7DC;
	scrollbar-arrow-color: #006699;
	scrollbar-track-color: #EFEFEF;
	scrollbar-darkshadow-color: #98AAB1;
	margin-top: 0px;
	margin-bottom: 0px;
	background-color: #F4F7F7;
}

TD {
	font-size: 12px;
	LINE-HEIGHT: 12px;
	font: 12px 细明体;
	CURSOR: default
}
</style>
	</head>
	<body>
		<input type="hidden" name="root" id="root" value="<%=ctx%>" />
		<input type="hidden" name="roleItemsAssigned" id="roleItemsAssigned" value="<%=request.getAttribute("roleItemsAssigned")%>" />
		<%
			Role role = (Role) request.getAttribute("role");
		%>
		<table border="0" cellpadding="0" cellspacing="0"
			style="margin-top: 15px;">
			<tr>
				<td width="30">
					&nbsp;
				</td>
				<td valign="top" width=320>
					<table border="0" cellpadding="0" cellspacing="0">
						<tr height="24">
							<td>
								<font size="2" style="font-weight: bolder">以下为功能角色信息，请分配权限</font>&nbsp;
							</td>
						</tr>
					</table>
					<div height="1">
						&nbsp;
					</div>
					<table class="listtable" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="80">
								角色：

							</td>
							<td width="270">
								<input type="text" value="<%=role.getRoleName()%>"
									disabled="disabled">
								<input type="hidden" value="<%=role.getId()%>" id="roleId">
							</td>
						</tr>
						<tr>
							<td>
								角色描述：

							</td>
							<td>
								<textarea rows="16" cols="28" disabled="disabled"><%=role.getDescription()%></textarea>
							</td>
						</tr>
						<tr height="50">
							<td colspan="2">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<button onclick="onSave();">
									保存
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<button onclick="window.close();">
									关闭
								</button>
							</td>

						</tr>
					</table>
				</td>

				<td width="5"></td>
				<td bgcolor="#999999" width="1" align="center"></td>
				<td width="5"></td>

				<td width="380">
					<div style="overflow: auto; height: 444;" class="table2">
						<table border=0 cellpadding=0 cellspacing=0>
							<tr height="20">
								<td>
									&nbsp;
								</td>
								<td width="20">
									<img src="<%=ctx%>/resources/index/pkg.gif" width="16"
										height="15">
								</td>
								<td align="left" valign="middle" colspan="3">
									<strong>系统权限</strong>
								</td>
							</tr>
							<%
								List roleItems = RoleItemFactory.getInstance().getMenuList();
								treeMaxLevel = RoleItemFactory.getInstance().getMaxItemLevel();
								ctxInit = request.getContextPath();
								out.print(render(roleItems).toString());
							%>
						</table>
					</div>
				</td>
			</tr>
		</table>

		<script type="text/javascript">
		/**
		 * 保存
		 */
		function onSave(){
			var roleId = document.getElementById("roleId").value;
			var trees = document.all.item("tree");
	  		var treeValues = "";
	   		for(var i=0; i<trees.length; i++){
	   			if(trees[i].checked) {
	   				treeValues+=trees[i].value.split(",")[0];
   					treeValues+=",";
	   			}
	   		}
	   		
		   	hs.ActionHelper.request("/dolphin/role.do?", {
		   		method: "roleItemAssignSave", 
		   		roleId: roleId, 
		   		trees: treeValues
		   	});
		}
		
		/**
		* 选择树节点

		*/
		function onTree(checkingBox){
			var trees = document.all.item("tree");
			if(trees.length==undefined)
				return;
			
			var checkingBoxValues = checkingBox.value.split(",");
			var id = checkingBoxValues[0];
			var parentId = checkingBoxValues[1];
			var level = checkingBoxValues[2]*1;
			var checked = checkingBox.checked;// 当前checkbox选择状况
			
			// 对树中每个节点进行遍历

			for(var i=0;i<trees.length;i++){
				var nodeValues = trees[i].value.split(",");
				var nodeId = nodeValues[0];
				var nodeParentId = nodeValues[1];
				var nodeLevel = nodeValues[2]*1;
				
				// 向下选择
				if(nodeLevel > level) {
					if(nodeId.substring(0,level)==id && trees[i]!=checkingBox){
						trees[i].checked=checked;
					}
				}
				
			}
			if(checked)
				onTreeCheckParent(trees, parentId, checked);
		}
		
		// 向上选择
		function onTreeCheckParent(trees, parentId, checked){
			if(parentId && parentId!=""){
				var parentIdNext;
				for(var i=0;i<trees.length;i++){
					if(trees[i]==undefined)continue;
		   			var nodeValues = trees[i].value.split(",");
					var nodeId = nodeValues[0];
					var nodeParentId = nodeValues[1];
					var nodeLevel = nodeValues[2]*1;
					
		   			if(nodeId==parentId){
		   				parentIdNext = nodeParentId;
		   				trees[i].checked=checked;
		   				break;
		   			}
				}
				
				onTreeCheckParent(trees,parentIdNext,checked);
			}
		}
		
		// 界面加载时渲染上次选中元素
		function renderRoleItemsAssign(){
			var trees = document.all.item("tree");
			var roleItemsAssignedStr = document.all.item("roleItemsAssigned").value; 
			var roleItemsAssigned = roleItemsAssignedStr.split(",");
			for(var i=0; i<trees.length; i++){
				var treeValues = trees[i].value.split(",");
				for(var j=0;j<roleItemsAssigned.length;j++){
					if(roleItemsAssigned[j]==treeValues[0]){
						trees[i].checked=true;
						break;
					}
				}
			}
		}
		function resetTrees(){
	   		var trees = document.all.item("tree");
	   		for(var i=0;i<trees.length;i++)
	   			trees[i].checked=false;
		}
		
		renderRoleItemsAssign();
		</script>
	</body>
</html>
