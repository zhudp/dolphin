/**
 * 功能角色分配.
 */
Ext.ns("dolphin.userRole");
/**
 * 用户列表Bar.
 */ 
 
dolphin.userRole.SearchBar = function() {
	var grid, roleBar;
	var accountField =new Ext.form.TextField({ name:'account', width:100});
	var deptComboTree = new QM.ui.TreeCombo({
        clearCls: 'allow-float', 
		xtype : 'treecombo',
		id : "deptIdText",
		fieldLabel : '所在部门',
		lazyInit : true,
		editable : true, // 如果树中结点不是一次全部加载请请此项设为false
		ignoreFolder : false,
		tree : {
			 loader : dolphin.resource.Util.getDeptComboTreeLoader(),
			 root : new Ext.tree.AsyncTreeNode({
						text : '所有部门',
						draggable : false,
						id : '-1'
					})
		}
	});
	
	var userTypeField = new Ext.form.ComboBox({
		fieldLabel : '用户类别',
		hiddenName : 'userType',
		displayField : 'text',
		width:90,
		allowBlank : false,
		store : hs.StoreFactory.getComboStore(comboJsonData["USER_TYPE"],true)
	});
	
	var userNameField = new Ext.form.TextField({
		fieldLabel : '用户姓名',
		hiddenName : 'userName',
		width:90
	});
	
	// searchbar
	var searchBar = new Ext.Toolbar();
	searchBar.add(
		"所在部门",deptComboTree,'-',
		//"用户类别", userTypeField,'-',
		"用户账号", accountField,'-',
		"用户姓名", userNameField,'-',
		{ text:'查询', iconCls: 'hs-button-search', handler: search }
	);
	function search() {
		grid.load({ 
			account: accountField.getValue(),
			//userType: userTypeField.getValue(),
			userName: userNameField.getValue(),
			deptId: deptComboTree.value
		});
	}
	accountField.on('specialkey',function(field, e) {
		if (e.getKey() == Ext.EventObject.ENTER) {
			search();
		}
	});
	userNameField.on('specialkey',function(field, e) {
		if (e.getKey() == Ext.EventObject.ENTER) {
			search();
		}
	});
	return {
		init: function(config) {
			roleBar = config.roleBar;
			grid = config.grid;
			grid.getSelectionModel().on("rowselect",function(selectionModel,rowIndex,record){
				roleBar.search({userId: grid.getSelected().get("id")});
		    });
			search();
		},
		getToolBar:function(){
			return searchBar;
		},
		doSearch : function() {
			return function() {
				search()
			};
		}
	}
}
 
/**
 * 功能角色列表Bar
 */ 
dolphin.userRole.RoleBar = function() {
	var grid, userId;
	
	function search(_userId) {
		grid.load({
			method: 'userRoleAssignQueryPaged'
			,userId: userId
		});
	}
	
	// toolbar
	var toolBar= new Ext.Toolbar();
	toolBar.add({ 
		text:'分配角色给所选用户',id:'900105',iconCls: 'hs-button-add', handler: function() {
			if(!userId){ 
	       		hs.MessageHelper.info({msg: '请选择下面分配的用户！'});
	       		return;
	      	}
			var record = grid.getSelected();
	    	if(!record){ 
	       		hs.MessageHelper.info({msg: '请选择下面需要分配给用户的角色！'});
	       		return;
	      	}
	      	
	      	hs.ActionHelper.request(dolphin.role.URL.userRoleAssign, {userId: userId, roleId: record.get("id")}, search);
		}
	},{ 
		text:'取消分配',id:'900106', iconCls: 'hs-button-remove', handler: function() {
			if(!userId){ 
	       		hs.MessageHelper.info({msg: '请选择下面分配的用户！'});
	       		return;
	      	}
	      	
			var record = grid.getSelected();
	    	if(!record){ 
	       		hs.MessageHelper.info({msg: '请选择下面需要取消分配的角色！'});
	       		return;
	      	}
	      	
	      	hs.ActionHelper.request(dolphin.role.URL.userRoleUnassign, {userId: userId, roleId: record.get("id")}, search);
		}
	});
	
	return {
		init: function(config) {
			grid = config.grid;
			search();
		}
		,search: function(_config) {
			var config = _config || {};
			userId = config.userId;
			search(config.userId);
		},
		getToolBar:function(){
			return toolBar;
		}
	}
}

dolphin.userRole.RoleGrid = function(_config) {
	var config = _config || {};
	var gridStore = hs.StoreFactory.getStore(dolphin.role.URL.userRoleAssignQuery, dolphin.role.Record);
	var grid = hs.GridFactory.getGridPanel({
		layout:'fit',
		store: gridStore,
		border:false,
		cm: [
	    	new Ext.grid.RowNumberer(),
			{ header: "ID", dataIndex: 'id', width: 60, hidden: true }
			,{ header: "角色名称", dataIndex: 'roleName', width: 200 }
			,{ header: "已分配给所选用户", dataIndex: 'userAssined', width: 110,renderer: hs.LabelValue.DEFAULT_WHETHER_RENDER(),  sortable: false }
			,{ header: "描述", dataIndex: 'description', width: 500 }
	    ]
	});
	
    return {
    	load: function(baseParams) {
    		hs.StoreHelper.beforeload(gridStore, baseParams);
    		gridStore.load({params:{start:0, limit:PAGE_SIZE}});
    	},
    	getSelected: function() {
    		return grid.getSelectionModel().getSelected();
    	},
    	getGrid:function(){
    		return grid;
    	}
    }
}
dolphin.userRole.View = function() {
	var searchBar = dolphin.userRole.SearchBar();
	var sexST = dolphin.resource.Util.getSexComboStore();
	var statusST = dolphin.resource.Util.getUserStatusComboStore();
	var deptTL = dolphin.resource.Util.getDeptComboTreeLoader();
	var userGrid = dolphin.user.UserGrid({
		        deptTreeLoader : deptTL,
				userURL : dolphin.user.URL.queryUserURL,
				search : searchBar.doSearch(),
				sexStore:sexST,
				statusStore:statusST
			});
			
	var userRoleAssignBar = dolphin.userRole.RoleBar();
	var userRoleAssignGrid =dolphin.userRole.RoleGrid();
	
	userRoleAssignBar.init({grid: userRoleAssignGrid});
    searchBar.init({grid: userGrid, roleBar: userRoleAssignBar});
    
    var centerPanel = new Ext.Panel({
       layout:'fit',
       region:'center',
       items:[userGrid.getGrid()],
       tbar:searchBar.getToolBar()
    });
    var southPanel = new Ext.Panel({
    	title:'角色设置',
        region:'south',
        layout:'fit',
        height:160,
        split:'true',
        collapsible:true,
        collapsed:true,
        items:[userRoleAssignGrid.getGrid()],
        tbar:userRoleAssignBar.getToolBar()
    });
    
	var userView= new Ext.Viewport({
				layout : "border",
				margins : '0 0 0 0',
				defaults : {
					border : false
				},
				items : [centerPanel,southPanel]
			});
	userView.show();
}
