/**
 * 功能角色管理.
 */
Ext.ns("dolphin.role");

dolphin.role.URL = {
	listRoleURL : '/dolphin/role!queryPaged.do',
	getRoleURL : '/dolphin/role!get.do',
	saveRoleURL : '/dolphin/role!save.do',
	userRoleUnassign : '/dolphin/role!userRoleUnassign.do',
	userRoleAssign : '/dolphin/role!userRoleAssign.do',
	userRoleAssignQuery:'/dolphin/role!userRoleAssignQueryPaged.do',
	deleteRoleURL : '/dolphin/role!delete.do'
};

dolphin.role.Record = [{
			name : "id"
		}, {
			name : "roleName"
		}, {
			name : "description"
		}, {
			name : "status"
		}, {
			name : "userAssined"
		}];

dolphin.role.Role = function(config) {
	this.config = config;
	this.statusStore; // 角色状态store
	this.gridStore;// 角色列表store
	this.viewPort;// viewport
	this.roleGrid;// 角色列表
};
dolphin.role.Role.prototype = {
	initComponent : function() {		
		this.statusStore = dolphin.resource.Util.getUserStatusComboStore();
		this.gridStore = hs.StoreFactory.getStore(dolphin.role.URL.listRoleURL, dolphin.role.Record);
		this.buildSearchPanel();
		this.buildGrid();
	},
	showViewPort : function() {
		if (!this.viewPort) {
			this.initComponent();
			var cfg = {
				layout : 'border',
				items : [this.searchPanel,this.roleGrid]
			};
			this.viewPort = new Ext.Viewport(cfg);
			this.search();
		}
		return this.viewPort;
	},
	search : function() {
		hs.StoreHelper.beforeload(this.gridStore, {
					roleName : this.nameTextField.getValue(),
					status : this.statusComboField.getValue()
				});
		this.gridStore.load({
					params : {
						start : 0,
						limit : PAGE_SIZE
					}
				})

	},
	/**
	 * 构建搜索条件Panel
	 */
	buildSearchPanel : function() {
		this.nameTextField = new Ext.form.TextField({
					fieldLabel : '角色名称',
					name : 's_name',
					width : 100
				});
		this.statusComboField = new Ext.form.ComboBox({
					fieldLabel : '角色状态',
					hiddenName : 's_status',
					displayField : 'text',
					store : this.statusStore
				});
		this.searchBtn = new Ext.Button({
					text : '查询',
					iconCls : 'hs-button-search',

					handler : this.search.createDelegate(this)
				});
		this.searchPanel = new Ext.Panel({
			region : "north",
			frame : false,
			border:false,
			//height:36,
			tbar : [{
					xtype : 'buttongroup',
					defaults : {
						scale : 'small'
					},
					items:[new Ext.form.Label({
								text : "名称："
							}), this.nameTextField,new Ext.Spacer(), new Ext.form.Label({
								text : "  状态："
							}), this.statusComboField, this.searchBtn]
			}]});
		this.nameTextField.on('specialkey', function(field,e) {
				if (e.getKey()==e.ENTER){				    
					this.search();
				}
			});
	},
	buildDialogWin : function() {
		this.dialog = new dolphin.role.DialogWin({
					store : this.statusStore,
					callback : this.search.createDelegate(this)
				})
		this.dialog.getWin();
	},
	add : function() {
		if (!this.dialog) {
			this.buildDialogWin();
		}
		this.dialog.reset();
		this.dialog.show("新增功能角色");
	},
	// 修改记录
	edit : function() {
		var record = this.roleGrid.getSelectionModel().getSelected();
		if (!record) {
			hs.MessageHelper.info({
						msg : '请选择记录后再进行操作！'
					});
			return;
		}
		if (!this.dialog) {
			this.buildDialogWin();
		}
		this.dialog.show("修改功能角色");
		this.dialog.load(record.get("id"));
	},
	// 删除记录
	remove : function() {
		var record = this.roleGrid.getSelectionModel().getSelected();
		if (!record) {
			hs.MessageHelper.info({
						msg : '请选择记录后再进行操作！'
					});
			return;
		}

		hs.MessageHelper.confirm("确认删除所选记录吗？", function() {
					hs.ActionHelper.request(dolphin.role.URL.deleteRoleURL, {
								id : record.get("id")
							}, this.search.createDelegate(this));
				}.createDelegate(this));
	},
	// 权限分配
	dispatch : function() {
		var record = this.roleGrid.getSelectionModel().getSelected();
		if (!record) {
			hs.MessageHelper.info({
						msg : '请选择记录后再进行操作！'
					});
			return;
		}
		var params = {
			roleId : record.get('id'),
			id : record.get('id')
		};
		if (!this.resourceWin) {
			this.resourceWin = new RoleResourceAssignment({
						treeurl : Ext.getDom('root').value + RoleResourceAssignmentURL.treeURL,
						buildBaseParams : params
					});
		}
		this.resourceWin.renderToElByWindow();
		this.resourceWin.loadByParams(params);
	},
	buildGrid : function(config) {
		this.roleGrid = hs.GridFactory.getGridPanel({
					title:"角色列表",
					region : 'center',
					layout : 'fit',
					border:false,
					store : this.gridStore,
					cm : [new Ext.grid.RowNumberer(), {
								header : "ID",
								dataIndex : 'id',
								width : 60,
								hidden : true
							}, {
								header : "名称",
								dataIndex : 'roleName',
								sortable : true,
								width : 200
							}, {
								header : "状态",
								dataIndex : 'status',
								width : 100,
								renderer : dolphin.resource.Util.render(this.statusStore)
							}, {
								header : "描述",
								dataIndex : 'description',
								width : 500
							}],
					tbar : [{
								text : '新增',
								id : '900301',
								iconCls : 'hs-button-add',
								handler : this.add.createDelegate(this)
							}, {
								text : '修改',
								id : '900302',
								iconCls : 'hs-button-edit',
								handler : this.edit.createDelegate(this)
							}, {
								text : '删除',
								id : '900303',
								iconCls : 'hs-button-remove',
								handler : this.remove.createDelegate(this)
							}, {
								text : '角色权限',
								id : '900304',
								iconCls : 'hs-button-view',
								id : 'assignment',
								handler : this.dispatch.createDelegate(this)
							}]
				});
		this.roleGrid.on('rowdblclick',this.edit.createDelegate(this));
	}
};

// 构建角色维护弹出窗体
dolphin.role.DialogWin = function(config) {
	this.config = config;
	this.roleForm = new Ext.form.FormPanel({
				autoWidth : true,
				autoHeight : true,
				labelWidth : 50,
				labelAlign : 'right',
				layout : 'form',
				frame : true,
				items : [{
							xtype : 'textfield',
							id : 'id',
							name : 'id',
							hidden : true
						},{
									xtype : 'textfield',
									fieldLabel : '名称',
									id : 'roleName',
									msgTarget : 'qtip',
									name : 'roleName',
									allowBlank : false,
									maxLength:50,
									width:180
								}, {
									xtype : 'combo',
									fieldLabel : '状态',
									hiddenName : 'status',
									displayField : 'text',
									msgTarget : 'qtip',
									store : this.config.store,
									allowBlank : false,
									width:80
								}, {
									xtype : 'textarea',
									fieldLabel : '描述',
									name : "description",
									msgTarget : 'qtip',
									maxLength:255,
									width:260
									// id : 'description',
//									anchor : "100%"
								}]
			});
};
dolphin.role.DialogWin.prototype = {
	getWin : function() {
		if (!this.win) {
			this.win = new Ext.Window({
//						model : true,
						frame : true,
						layout : 'fit',
						width : this.dlgWidth ? this.dlgWidth : 380,
						
						closeAction : 'hide',
						items : [this.roleForm]
					});
			this.win.addButton("保存", this.save.createDelegate(this, [this.roleForm, this.config.callback]));
			this.win.addButton("取消", this.cancel.createDelegate(this));
		}
		return this.win;
	},
	/**
	 * 保存form
	 * 
	 * @param {}
	 *          formPanel
	 * @param {}
	 *          call
	 */
	save : function(formPanel, call) {
		var form = formPanel.getForm();
		if (form.isValid()) {
			hs.FormHelper.submit(dolphin.role.URL.saveRoleURL, function(form, action) {
						hs.MessageHelper.succuss({
									callback : function() {
										hs.WindowHelper.hide(true, call);
									}
								})
					}, null, form);
		} else {
			hs.MessageHelper.info({
						msg : '数据填写有误,请查看红色提示!'
					});
		}
	},
	/**
	 * 取消窗体
	 */
	cancel : function() {
		hs.WindowHelper.hide(true);
	},
	/**
	 * 清空Form数据
	 */
	reset : function() {
		this.roleForm.getForm().items.each(function(f) {
					f.setValue(null);
				});
	},
	/*
	 * 加载form数据
	 */
	load : function(keyValue) {
		hs.FormHelper.load(dolphin.role.URL.getRoleURL, {
					id : keyValue
				}, null, this.roleForm.getForm());
	},
	show : function(title) {
		this.win.setTitle(title);
		this.win.show();
	}
}
