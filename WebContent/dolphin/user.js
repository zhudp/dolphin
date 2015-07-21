/**
 * 用户管理.
 */
/**
 * 编程流程 1、定义该模块访问后台数据的所有 URL 2、如果有Grid，定义Grid的 record 3、定义
 * 查询formPanel及其中的各个控件、查询方法，提供返回FormPanel、查询方法、初始化Grid的接口 4、定义 GridPanel
 * （必要参数：callback方法，一般为刷新grid的方法） 5、定义 增、改 所需的Form、Win 6、使用 Viewport+border嵌套fit
 * 布局渲染到页面上
 */
Ext.namespace("dolphin.user");

dolphin.user.URL = {
	initPassword : '/dolphin/user!initPassword.do',
	queryUserURL : '/dolphin/user!queryPaged.do',
	saveUserURL : '/dolphin/user!save.do',
	getUserURL : '/dolphin/user!get.do',
	getComboxTree : '/dolphin/department!departmentTreeList.do',
	queryUserByDepartment : '/dolphin/user!queryUserByDepartment.do',
	changePassword : '/dolphin/user!changePassword.do',
	getDeptListURL : '/dolphin/department!getDeptListByUser.do',
	deleteUserURL : '/dolphin/user!delete.do'
};

dolphin.user.Record = [
	 {name:"id"}
	,{name:"idCard"}
	,{name:"userName"}
	,{name:"email"}
	,{name:"phone"}
	,{name:"mobile"}
	,{name:"address"}
	,{name:"zipCode"}
	,{name:"sex"}
	,{name:"birthday"}
	,{name:"deptId"}
	,{name:"department.deptName"}
	,{name:"userSecurity.account"}
	,{name:"userSecurity.lastLoginTime"}
	,{name:"userSecurity.loginCount"}
	,{name:"userSecurity.lastLoginIp"}
	,{name:"status"}
	,{name:"deptIdList"}
	,{name:"isManager"}
	,{name:"communityId"}
	,{name:"communityName"}
	,{name:"reseauInfo"}
	,{name:"imeiNumber"}
	,{name:"userType"}
	,{name:"creator"}
	,{name:"gmtCreate"}
];

/**
 * 定义搜索条件区域的FormPanel及搜索方法
 * 
 * @param config.sexStroe
 *          性别Store
 * @param config.deptTreeLoader
 *          部门树Loader
 * @param config.grid
 *          用户搜索结果列表Grid
 * @param config.panelConfig
 *          搜索Panel 的配置参数
 */
dolphin.user.SearchPanel = function(config) {
	var grid;
	var nameText = new Ext.form.TextField({
				fieldLabel : '姓名',
				name : 's_name',
				width : 120
			});
	var sexText = new Ext.form.ComboBox({
				fieldLabel : '性别',
				hiddenName : 's_sex',
				displayField : 'text',
				width : 80,
				store : config.sexStore
			});

	var deptComboTree = new QM.ui.TreeCombo({
				clearCls : 'allow-float',
				xtype : 'treecombo',
				fieldLabel : '所在部门',
				lazyInit : true,
				editable : true, // 如果树中结点不是一次全部加载请请此项设为false
				ignoreFolder : false,
				tree : {
					loader : config.deptTreeLoader,
					root : new Ext.tree.AsyncTreeNode({
								text : '所有部门',
								draggable : false,
								id : '-1'
							})
				}
			});

	var searchBtn = new Ext.Button({
				text : '查询',
				iconCls : 'hs-button-search',
				handler : search
			});
	// 查询方法
	function search() {
		grid.load({
			userName : nameText.getValue(),
			sex : sexText.getValue(),
			deptId : deptComboTree.value
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
				         new Ext.form.Label({text : "姓名："}), nameText, {xtype : 'tbspacer',width : 10},
				         new Ext.form.Label({text : "性别："}), sexText, {xtype : 'tbspacer',width : 10}, 
				         new Ext.form.Label({text : "所属部门："}), deptComboTree, {xtype : 'tbspacer',width : 20},
				         searchBtn]
				}]
	};
	var panelConfig = config.panelConfig ? config.panelConfig : {};
	Ext.apply(cfg, panelConfig);
	var searchPanel = new Ext.Panel(cfg);
	//支持回车模糊搜索
	nameText.on('specialkey', function(field,e) {
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
				search();
			};
		},
		init : function(config) {
			grid = config.grid;
			search();
		}
	};
};
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
dolphin.user.UserGrid = function(config) {
	var userAddWin, userUpdateWin,deptCode, deptName;
	var _config = config || {};
	var gridStore = hs.StoreFactory.getStore(_config.userURL, dolphin.user.Record);
	var sexStore = config.sexStore;
	var userStatusStore = _config.statusStore;
	var editBtn = new Ext.Button({
							text : '修改',
							id : '900102',
							iconCls : 'hs-button-edit',
							handler : function() {
								var record = grid.getSelectionModel().getSelected();
								if (!record) {
									hs.MessageHelper.info({
												msg : '请选择记录后再进行操作！'
											});
									return;
								}
								if (userUpdateWin) {
									userUpdateWin = null;
								}
								userUpdateWin = dolphin.user.UserWin(_config);
								userUpdateWin.show("修改用户", record);
							}});
	
	var grid = hs.GridFactory.getGridPanel({
				//title : '用户列表',
				pageSize : 15,
				store : gridStore,
				border:false,
				enableColumnHide: false,
				enableColumnMove: false,
				enableColumnResize: true,
				enableHdMenu: false,
				enableRowHeightSync: false,
				monitorWindowResize: false,
				stripeRows:true,
				trackMouseOver: true,
				cm : [
				      new Ext.grid.RowNumberer()
				    ,{header:"ID",dataIndex:'id',width:60,hidden:true}
					,{header:"用户帐号",dataIndex:'userSecurity.account',sortable:true,width:90}
					,{header:"姓名",dataIndex:'userName',sortable:true,width:70}
//					,{header:"用户类别",dataIndex:'userType',width:140,
//						renderer:function(value, meta, record){
//							var userType = dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["USER_TYPE"]))(value);
//							var reseauInfo = record.get("reseauInfo");
//							return userType+"<br><font color='#666'>"+reseauInfo+"</font>";
//						}
//					}
					,{header:"电话",dataIndex:'phone',sortable:true,width:98}
			      	,{header:"手机",dataIndex:'mobile',sortable:true,width:88}
					,{header:"状态",dataIndex:'status',width:57,renderer:dolphin.resource.Util.renderUserStatusColor(userStatusStore)}
					,{header:"登录次数",dataIndex:'userSecurity.loginCount',sortable:true,width:60}
					,{header:"最后登录时间",dataIndex:'userSecurity.lastLoginTime',sortable:true,width:110}
					,{header:"创建人",dataIndex:'creator',sortable:true,width:80}
					,{header:"创建时间",dataIndex:'gmtCreate',sortable:true,width:80}
				],
				tbar : [{
							text : '新增',
							iconCls : 'hs-button-add',
							id : '900101',
							handler : function() {
								if (userAddWin) {
									userAddWin= null;
								}
								userAddWin = dolphin.user.UserWin(_config);
								userAddWin.show("新增用户");
								userAddWin.setFormValue({
											curNode : "1",
											deptName : "智慧织里",
											'userType' : "0",
											'status' : "1"
										});
							}
						}, editBtn, {
							text : '删除',
							id : '900103',
							iconCls : 'hs-button-remove',
							handler : function() {
								var record = grid.getSelectionModel().getSelected();
								if (!record) {
									hs.MessageHelper.info({
												msg : '请选择记录后再进行操作！'
											});
									return;
								}
								hs.MessageHelper.confirm("确认删除所选记录吗？", function() {
											hs.ActionHelper.request(dolphin.user.URL.deleteUserURL, {
														id : record.get("id")
													}, _config.search)
										});
							}
						}, '-', {
							text : '重置密码',
							id : '900104',
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
											hs.ActionHelper.request(dolphin.user.URL.initPassword, {
														userId : record.get("id")
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
};
/**
 * 定义弹出窗口
 * 
 * @param {Function}
 *          config.callback ：操作成功后的回调函数 
 *          config.deptTreeLoader :部门树Loader
 *          config.sexStore
 * 
 */
dolphin.user.UserWin = function(config) {
	var form = new dolphin.user.UserForm(config);
	var win = new Ext.Window({
		width : 520,
		y:20,
		autoHeight : true,
		items : form.getForm()
	});
	win.addButton("保存", function() {
		
		
		var param = {};
		hs.FormHelper.submit(dolphin.user.URL.saveUserURL, function(form, action) {
			hs.MessageHelper.succuss({
				callback : function() {
					hs.WindowHelper.hide(true, config.search);
				}
			});
		}, param, form.getForm().getForm());
	});
	win.addButton("关闭", function() {
				hs.WindowHelper.hide(true);
			});
	return {
		show : function(title, record) {
			win.setTitle(title);
			win.show();
			form.load(record);
		},
		setFormValue : function(config) {
			form.getForm().getForm().setValues({
						'deptName' : config.deptName,
						'deptId' : config.curNode,
						'userType' : config.userType,
						'status' : config.status
					});
			Ext.getDom('deptId').value = config.curNode;

		}
	};
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
dolphin.user.UserForm = function(config) {
	
	var defaultsCfg = {
		anchor: '97%'
	};
	var readonlyCfg = {
		readOnly: true,
		anchor: '97%',
		cls: 'text-readonly'
	};
		
	var deptComboTree = new QM.ui.TreeCombo({
		xtype : 'treecombo',
		name : "deptId",
		hiddenName : "deptId",
		fieldLabel : '所在部门',
		valueNotFoundText:'--',
		emptyText:'',
		emptyClass:'',
		lazyInit : false,
		editable : false, // 如果树中结点不是一次全部加载请请此项设为false
		ignoreFolder : false,
		width:180,
		tree : {
			loader : config.deptTreeLoader,
			root : new Ext.tree.AsyncTreeNode({
						expanded : false,
						text : '所有部门',
						draggable : false,
						id : '-1'
					})
		}
	});
	
	
	var userIdField = new Ext.form.Hidden({
		name:"id"
	});
	
	var accountField = new Ext.form.TextField({
		fieldLabel : '帐号',
		name : 'account',
		emptyText:'英文或数字',
		allowBlank : false,
		minLength:4
	});
	
	var userNameField = new Ext.form.TextField({
		fieldLabel : '姓名',
		name : 'userName',
		allowBlank : false
	});

	var phoneField = new Ext.form.TextField({
		fieldLabel : '电话',
		name : 'phone'
	});
	var mobileField = new Ext.form.TextField({
		fieldLabel : '手机',
		name : 'mobile'
	});
	
	var creatorField = new Ext.form.TextField({
		fieldLabel : '创建人',
		name : 'creator'
	});
	var gmtCreateField = new Ext.form.TextField({
		fieldLabel : '创建时间',
		name : 'gmtCreate'
	});
	var statusField = new Ext.form.ComboBox({
		fieldLabel : '状态',
		hiddenName : 'status',
		displayField : 'text',
		width:80,
		store : config.statusStore,
		allowBlank : false
	});
	var userTypeField = new Ext.form.ComboBox({
		fieldLabel : '用户类别',
		hiddenName : 'userType',
		displayField : 'text',
		width:80,
		allowBlank : false,
		store : hs.StoreFactory.getComboStore(comboJsonData["USER_TYPE"]),
		listeners : {
			select : function(combo, record, index) {
				
			}
		}
	});
	
	
	var form = new Ext.form.FormPanel({
		autoWidth : true,
		autoHeight : true,
		frame : true,
		labelWidth : 65,
		layout:'form',
		labelAlign : 'right',
		items : [{
				xtype:'fieldset',
			    title: '基本信息',
				labelAlign: 'right',
				bodyBorder: false,
			    items :[
		            userIdField,
		            hs.FormLayout.twoColumnedRow(accountField,userNameField,defaultsCfg),
		            hs.FormLayout.twoColumnedRow(statusField,deptComboTree,defaultsCfg),
		            //hs.FormLayout.twoColumnedRow(userTypeField,statusField,defaultsCfg),
		            hs.FormLayout.twoColumnedRow(phoneField,mobileField,defaultsCfg),
		            hs.FormLayout.twoColumnedRow(creatorField,gmtCreateField,readonlyCfg)
			    ]
         	}         	
		]
	});
	
	return {
		load : function(record) {
			if (record) {
				accountField.getEl().dom.readOnly = true;
				accountField.addClass('text-readonly');
				
				hs.FormHelper.load(dolphin.user.URL.getUserURL, {
							id : record.get("id")
						}, function(form, action) {
							var data = Ext.util.JSON.decode(action.result.data);
							form.clearInvalid();
							form.setValues(data);
							deptComboTree.hiddenField.value = record.get("deptId");
							deptComboTree.el.dom.value = record.get("department.deptName");
							accountField.setValue(record.get("userSecurity.account"));
						}, form.getForm());
			}
			else {
				accountField.getEl().dom.readOnly = false;
				accountField.removeClass('text-readonly');
			}
		},
		getForm : function() {
			return form;
		}
	};
};

/**
 * 将之前定义的容器布局在一个panel中，并渲染到页面的方法
 */
dolphin.user.View = function() {
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
	var searchPanel = dolphin.user.SearchPanel(cfg);

	var statusST = dolphin.resource.Util.getUserStatusComboStore();

	var userGrid = dolphin.user.UserGrid({
				deptTreeLoader : deptTL,
				statusStore : statusST,
				sexStore : sexST,
				userURL : dolphin.user.URL.queryUserURL,
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
							items : [userGrid.getGrid()]
						}]
			});
}
