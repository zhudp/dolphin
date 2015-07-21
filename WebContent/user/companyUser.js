/**
 * 用户管理.
 */
/**
 * 编程流程 1、定义该模块访问后台数据的所有 URL 2、如果有Grid，定义Grid的 record 3、定义
 * 查询formPanel及其中的各个控件、查询方法，提供返回FormPanel、查询方法、初始化Grid的接口 4、定义 GridPanel
 * （必要参数：callback方法，一般为刷新grid的方法） 5、定义 增、改 所需的Form、Win 6、使用 Viewport+border嵌套fit
 * 布局渲染到页面上
 */
Ext.namespace("company.user");

company.user.URL = {
	initPassword : '/user/company-user!initPassword.do',
	queryUserURL : '/user/company-user!queryPaged.do',
	saveUserURL : '/user/company-user!save.do',
	getUserURL : '/user/company-user!get.do'
};

company.user.Record = [
	 {name:"userId"}
	,{name:"account"}
	,{name:"userName"}
	,{name:"sex"}
	,{name:"email"}
	,{name:"phone"}
	,{name:"mobile"}
	,{name:"companyId"}
	,{name:"companyName"}
	,{name:"status"}
	,{name:"creator"}
	,{name:"gmtCreate"}
];

company.user.SearchPanel = function(config) {
	var grid;
	var accountField = new Ext.form.TextField({
		fieldLabel : '用户名',
		name : 'account',
		width : 120
	});
	var userNameField = new Ext.form.TextField({
		fieldLabel : '姓名',
		name : 'userName',
		width : 120
	});
	var companyNameField = new Ext.form.TextField({
		fieldLabel : '公司名称',
		name : 'companyName',
		width : 120
	});

	var searchBtn = new Ext.Button({
				text : '查询',
				iconCls : 'hs-button-search',
				handler : search
			});
	// 查询方法
	function search() {
		grid.load({
			account : accountField.getValue(),
			userName : userNameField.getValue(),
			companyName : companyNameField.getValue()
		});
	}
	var cfg = {
		frame : false,
		border : false,
		tbar : [{
					xtype : 'buttongroup',
					// title: '查询条件',
					defaults : {
						scale : 'small'
					},
					items : [
				         new Ext.form.Label({text : "公司名称："}), companyNameField, {xtype : 'tbspacer',width : 10},
					     new Ext.form.Label({text : "用户名："}), accountField, {xtype : 'tbspacer',width : 10},
				         new Ext.form.Label({text : "姓名："}), userNameField, {xtype : 'tbspacer',width : 10},
				         searchBtn
					]
				}]
	};
	var panelConfig = config.panelConfig ? config.panelConfig : {};
	Ext.apply(cfg, panelConfig);
	var searchPanel = new Ext.Panel(cfg);
	//支持回车模糊搜索
	accountField.on('specialkey', function(field,e) {
		if (e.getKey()==e.ENTER){				    
			search();
		}
	});
	userNameField.on('specialkey', function(field,e) {
		if (e.getKey()==e.ENTER){				    
			search();
		}
	});
	companyNameField.on('specialkey', function(field,e) {
		if (e.getKey()==e.ENTER){				    
			search();
		}
	});

	return {
		getSearchPanel : function() {
			return searchPanel;
		},
		searchCallback : function() {
			return function() {
				search()
			};
		},
		init : function(config) {
			grid = config.grid;
			search();
		}
	}
}
/**
 * 定义grid
 * 
 * @param config.userURL
 *          :grid 请求数据的URL
 * @param config.search
 *          :更新grid的回调函数
 * @param config.sexStore
 *          :性别Store
 * @param config.statusStore
 *          :用户状态Store
 */
company.user.UserGrid = function(config) {
	var userUpdateWin, userAddWin;
	var _config = config || {};
	var gridStore = hs.StoreFactory.getStore(_config.userURL, company.user.Record);
	var sexStore = config.sexStore;
	var userStatusStore = _config.statusStore;
	var editBtn = new Ext.Button({
		text : '修改',
		id : '900202',
		iconCls : 'hs-button-edit',
		handler : function() {
			var record = grid.getSelectionModel().getSelected();
			if (!record) {
				hs.MessageHelper.info({
							msg : '请选择记录后再进行操作！'
						});
				return;
			}
			if (!userUpdateWin) {
				userUpdateWin = company.user.UserWin(_config);
			}
			userUpdateWin.show("修改用户", record);
		}
	});
	
	var grid = hs.GridFactory.getGridPanel({
				title : '用户列表',
				pageSize : 15,
				border:false,
				store : gridStore,
				autoScroll : true, // 允许滚动条
				cm : [
				       new Ext.grid.RowNumberer()
				      ,{header:"ID",dataIndex:'userId',width:60,hidden:true}
				      ,{header:"帐号",dataIndex:'account',sortable:true,width:90}
				      ,{header:"姓名",dataIndex:'userName',sortable:true,width:80}
				      //,{header:"性别",dataIndex:'sex',width:60,sortable:true,renderer:dolphin.resource.Util.render(sexStore)}
				      ,{header:"公司ID",dataIndex:'companyId',sortable:true,width:50,hidden:true}
				      ,{header:"公司名称",dataIndex:'companyName',sortable:true,width:180}
				      ,{header:"电话",dataIndex:'phone',sortable:true,width:98}
				      ,{header:"手机",dataIndex:'mobile',sortable:true,width:88}
				      ,{header:"状态",dataIndex:'status',width:57,renderer:dolphin.resource.Util.renderUserStatusColor(userStatusStore)}
				      ,{header:"创建人",dataIndex:'creator',sortable:true,width:80}
				      ,{header:"创建时间",dataIndex:'gmtCreate',sortable:true,width:80}
				],
				tbar : [{
							text : '新增',
							iconCls : 'hs-button-add',
							id : '900201',
							handler : function() {
								if (!userAddWin) {
									userAddWin = company.user.UserWin(_config);
								}
								userAddWin.show("新增用户");
							}
						}, editBtn,  '-', {
							text : '重置密码',
							id : '900204',
							iconCls : 'hs-button-view',
							handler : function() {
								var record = grid.getSelectionModel().getSelected();
								if (!record) {
									hs.MessageHelper.info({
												msg : '请选择记录后再进行操作！'
											});
									return;
								}
								hs.MessageHelper.confirm("确认对所选用户进行密码初始化吗？", function() {
											hs.ActionHelper.request(company.user.URL.initPassword, {
														userId : record.get("userId")
													})
										});
							}
						}]
			});
			grid.on("rowdblclick",editBtn.handler);

	return {
		getGrid : function() {
			return grid;
		},
		load : function(baseParams) {
			hs.StoreHelper.beforeload(gridStore, baseParams);
			gridStore.load({
						params : {
							start : 0,
							limit : PAGE_SIZE
						}
					});
		},
		getSelected : function() {
			return grid.getSelectionModel().getSelected();
		},
		getSelectionModel : function() {
			return grid.getSelectionModel();
		}
	}
}
/**
 * 定义弹出窗口
 * 
 * @param {Function}
 *          config.callback ：操作成功后的回调函数 
 *          config.deptTreeLoader :部门树Loader
 *          config.sexStore
 * 
 */
company.user.UserWin = function(config) {
	var form = new company.user.UserForm(config);
	var win = new Ext.Window({
		width : 520,
		autoHeight : true,
		items : form.getForm()
	});
	win.addButton("保存", function() {
				hs.FormHelper.submit(company.user.URL.saveUserURL, function(form, action) {
							hs.MessageHelper.succuss({
										callback : function() {
											hs.WindowHelper.hide(true, config.search);
										}
									});
						}, null, form.getForm().getForm());
			}

	);
	win.addButton("关闭", function() {
				hs.WindowHelper.hide(true);
			});
	return {
		show : function(title, record) {
			win.setTitle(title);
			win.show();
			form.load(record);
		}
	}
};

/**
 * 定义弹出窗口中的内容
 * 
 * @param {Function}
 *          config.callback ：操作成功后的回调函数 
 *          config.deptTreeLoader :部门树Loader
 *          config.sexStore
 * 
 */
company.user.UserForm = function(config) {
	
	var defaultsCfg = {
		anchor: '97%'
	};
	var readonlyCfg = {
		readOnly: true,
		anchor: '97%',
		cls: 'text-readonly'
	};
		
	var userIdField = new Ext.form.Hidden({
		name:"userId"
	});
	var accountField = new Ext.form.TextField({
		fieldLabel : '帐号',
		name : 'account',
		allowBlank : false,
		emptyText:'英文或数字',
		minLength:4
	});
	var userNameField = new Ext.form.TextField({
		fieldLabel : '姓名',
		name : 'userName',
		allowBlank : false
	});
	
	var companyChooser = new Ext.ux.chooser.CompanyChooser({
		callBack: function(rec) {
			if(null==rec){
				companyIdField.setValue(0);
				companyNameField.setValue("");
			}else{
				companyIdField.setValue(rec.data.companyId);
				companyNameField.setValue(rec.data.companyName);
			}
		}
	});
	
	var companyIdField = new Ext.form.Hidden({
		name:"companyId"
	});
	var companyNameField = new Ext.form.TriggerField({
		fieldLabel : '公司名称',
		name : 'companyName',
		allowBlank : false,
		editable:false,
		triggerClass: 'x-form-search-trigger',
		onTriggerClick:function() {
			companyChooser.show();
		}
	});

	var phoneField = new Ext.form.TextField({
		fieldLabel : '电话',
		name : 'phone'
	});
	var mobileField = new Ext.form.TextField({
		fieldLabel : '手机',
		name : 'mobile'
	});
	
	var statusField = new Ext.form.ComboBox({
		fieldLabel : '状态',
		name : 'status',
		hiddenName : 'status',
		displayField : 'text',
		width:80,
		store : config.statusStore,
		allowBlank : false
	});
	
	var creatorField = new Ext.form.TextField({
		fieldLabel : '创建人',
		name : 'creator'
	});
	var gmtCreateField = new Ext.form.TextField({
		fieldLabel : '创建时间',
		name : 'gmtCreate'
	});

	var form = new Ext.form.FormPanel({
				autoWidth : true,
				autoHeight : true,
				frame : true,
				labelWidth : 65,
				layout:'form',
				labelAlign : 'right',
				items : [
			         userIdField,companyIdField,
			         hs.FormLayout.twoColumnedRow(accountField,userNameField,defaultsCfg),
			         hs.FormLayout.twoColumnedRow(companyNameField,statusField,defaultsCfg),
			         hs.FormLayout.twoColumnedRow(phoneField,mobileField,defaultsCfg),
			         hs.FormLayout.twoColumnedRow(creatorField,gmtCreateField,readonlyCfg)
				]
			});
	return {
		load : function(record) {
			if (record) {
				accountField.getEl().dom.readOnly = true;
				accountField.addClass('text-readonly');
				hs.FormHelper.load(company.user.URL.getUserURL, {
							userId : record.get("userId")
						}, null, form.getForm());
			}
		},
		getForm : function() {
			return form;
		}
	}
}

/**
 * 将之前定义的容器布局在一个panel中，并渲染到页面的方法
 */
company.user.View = function() {
	var sexST = dolphin.resource.Util.getSexComboStore();
	var deptTL = dolphin.resource.Util.getDeptComboTreeLoader();
	var cfg = {
		sexStore : sexST,
		panelConfig : {
			region : "north",
			height : 32,
			border : false
		},
		deptTreeLoader : deptTL
	};
	var searchPanel = company.user.SearchPanel(cfg);

	var statusST = dolphin.resource.Util.getUserStatusComboStore();

	var userGrid = company.user.UserGrid({
				deptTreeLoader : deptTL,
				statusStore : statusST,
				sexStore : sexST,
				userURL : company.user.URL.queryUserURL,
				search : searchPanel.searchCallback()
			});
	searchPanel.init({
				grid : userGrid
			}); // 使用 userGrid.getUserGrid() 页面报错：this.body is null

	var userView = new Ext.Viewport({
				layout : "border",
				margins : '0 0 0 0',
				defaults : {
					border : false
				},
				items : [searchPanel.getSearchPanel(), {
							region : "center",
							layout : 'fit',
							items : [userGrid.getGrid()]
						}]
			});
}
