/**
 * 用户管理.
 */
 /**
  * 编程流程
  * 1、定义该模块访问后台数据的所有 URL
  * 2、如果有Grid，定义Grid的 record
  * 3、定义 查询formPanel及其中的各个控件、查询方法
  * 4、定义 Grid （必要参数：callback方法，一般为刷新grid的方法）
  * 5、定义 增、改 所需的Form、Win
  * 6、定义 Panel，并渲染到body
  */
var userURL = {
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

var userRecord = [{
			name : "id"
		}, {
			name : "idCard"
		}, {
			name : "userName"
		}, {
			name : "email"
		}, {
			name : "phone"
		}, {
			name : "mobile"
		}, {
			name : "address"
		},{
			name : "post"
		}, {
			name : "zipCode"
		}, {
			name : "sex"
		}, {
			name : "birthday"
		}, {
			name : "deptId"
		}, {
			name : "department.deptName"
		}, {
			name : "userSecurity.account"
		}, {
			name : "status"
		}, {
			name : "deptIdList"
		}, {
			name : "isManager"
		}];
common.UserBar = function() {
	var grid;
	var comboxWithTree = new Ext.form.ComboBox({
		fieldLabel: '所在部门',
		store : new Ext.data.SimpleStore({
			fields : [],
			data : [[]]
		}),
		editable : false,
		mode : 'local',
		triggerAction : 'all',
		maxHeight : 200,
		emptyText : '请选择',
		blankText : '请选择',
		tpl : "<tpl for='.'><div style='height:200px'><div id='tree'></div></div></tpl>",
		selectedClass : '',
		onSelect : Ext.emptyFn
	});
	var tree = new Ext.tree.TreePanel({
		loader : new Ext.tree.TreeLoader({
			dataUrl : Ext.getDom("root").value + userURL.getComboxTree
		}),
		border : false,
		root : new Ext.tree.AsyncTreeNode({
			text : '所有部门',
			draggable : false,
			id : '-1'
		})
	});
	tree.on('click', function(node) {
				comboxWithTree.setValue(node.text);
				// if(node.id!='-1')
				Ext.getDom("s_deptId").value = node.id;
				comboxWithTree.collapse();
			});
	comboxWithTree.on('expand', function() {
				tree.render('tree');
				tree.expandAll();
			});

	var sexData = [['0', '女'],['1', '男']];
	var sexSelectDs = new Ext.data.SimpleStore({data: sexData,fields:["id","text"]});
	sexSelectDs.loadData(sexData);
	
	var nameText = new Ext.form.TextField({
		fieldLabel: '姓名',
		name : 's_name',
		id : 's_name',
		width : 120
	});
	var idCardText =  new Ext.form.TextField({
		fieldLabel: '身份证号',
		name : 's_idCard',
		id : 's_idCard',
		width : 120
	});
	var sexText = new Ext.form.ComboBox({
		fieldLabel: '性别',
		hiddenName : 's_sex',
		displayField:'text',
		store: sexSelectDs,
		width : 100
	});
	var searchBtn = new Ext.Button({
		text : '查询',
		iconCls : 'hs-button-search',
		handler : search
	});
	// 查询方法
	function search() {
		grid.load({
//			userName : Ext.getDom("s_name").value,
//			idCard : Ext.getDom("s_idCard").value,
//			sex : Ext.getDom("s_sex").value,
//			deptId : Ext.getDom("s_deptId").value
		});
	}
	Ext.getCmp('s_name').on('specialkey', search);
	Ext.getCmp('s_idCard').on('specialkey', search);
	// searchFormPanel定义
	var searchFormPanel = new Ext.FormPanel({
		bodyStyle:'padding:5px',
		width: '100%',
		height: '100%',
		frame:true,
		labelAlign: 'right',
		labelWidth:70,
		labelSeparator:'：',
		items: [{
			layout:'column',
			width: '810',
			border:false,
			items:[{
				columnWidth:.26,
				layout: 'form',
				items: [nameText] //, idCardText, sexText, comboxWithTree, searchBtn
			}]
		}]
	});
	return {
		getUserBar: function(){
			this.searchFormPanel;
		},
		getUserFn: function(){
			this.search;
		},
		init : function(config) {
			grid = config.grid;
			search();
		}
	}
}
/**
 * 定义grid
 * @param {} _config
 * @return {}
 */
common.UserGrid = function(_config) {
	var config = _config || {};
	var gridStore = hs.StoreFactory.getStore(config.userURL, userRecord);
	var grid = hs.GridFactory.getGridPanel({
		el:  hs.ELFactory.getGridEL({
			id : "UserGrid"
		}),
		pageSize: PAGE_SIZE,
		store: gridStore,
		autoHeight: true,
		width: document.body.offsetWidth,
		cm: [new Ext.grid.RowNumberer(), {
			header : "ID",
			dataIndex : 'id',
			width : 60,
			hidden : true
		}, {
			header : "用户姓名",
			dataIndex : 'userName',
			width : 80
		}, {
			header : "身份证号码",
			dataIndex : 'idCard',
			width : 130
		}, {
			header : "登录帐号",
			dataIndex : 'userSecurity.account',
			width : 60
		}, {
			header : "性别",
			dataIndex : 'sex',
			width : 60,
			renderer : hs.LabelValue.SEX_RENDER()
		}, {
			header : "所在部门",
			dataIndex : 'department.deptName',
			sortable: false,
			width : 140
		}, {
			header : "职位",
			dataIndex : 'post',
			sortable: false,
			width : 140
		},{
			header : "出生年月",
			dataIndex : 'birthday',
			width : 70
		}, {
			header : "状态",
			dataIndex : 'status',
			width : 57,
			renderer : hs.LabelValue.USER_STATUS_RENDER()
		}, {
			header : "电话",
			dataIndex : 'phone',
			width : 100
		}, {
			header : "手机",
			dataIndex : 'mobile',
			width : 100
		}
		, {
			header : "住址",
			dataIndex : 'address',
			width : 130
		}, {
			header : "邮编",
			dataIndex : 'zipCode',
			width : 60
		}, {
			header : "E-mail",
			dataIndex : 'email',
			width : 130
		}],
		tbar: [{
			text : '新增',
			iconCls : 'hs-button-add',
			id:'900201',
			handler : function() {
				if (!userWin) {
					userWin = common.UserWin(config.search);
				}
				userWin.show("新增用户");
				userWin.setFormValue({
					curNode : deptCode,
					deptName : deptName
				});
			}
		}, {
			text : '修改',
			id:'900202',
			iconCls : 'hs-button-edit',
			handler : function() {
				var record = grid.getSelected();
				if (!record) {
					hs.MessageHelper.info({
						msg : '请选择记录后再进行操作！'
					});
					return;
				}
				if (!userWin) {
					userWin = common.UserWin(config.search);
				}
				userWin.show("修改用户", record);
			}
		}, {
			text : '删除',
			id:'900203',
			iconCls : 'hs-button-remove',
			handler : function() {
				var record = grid.getSelected();
				if (!record) {
					hs.MessageHelper.info({
						msg : '请选择记录后再进行操作！'
					});
					return;
				}
				hs.MessageHelper.confirm("确认删除所选记录吗？", function() {
							hs.ActionHelper.request(userURL.deleteUserURL,
									{
										id : record.get("id")
									}, config.search)
						});
			}
		}, {
			text : '密码初始化',
			id:'900204',
			iconCls : 'hs-button-view',
			handler : function() {
				var record = grid.getSelected();
				if (!record) {
					hs.MessageHelper.info({
						msg : '请选择记录后再进行操作！'
					});
					return;
				}
				hs.MessageHelper.confirm("确认对所选用户进行密码初始化吗？", function() {
							hs.ActionHelper.request(userURL.initPassword, {
										userId : record.get("id")
									})
						});
			}
		}]
	});

	return {
		getUserGrid : function() {
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
 * @param {Function}
 *  callback：操作成功后的回调函数
 * 
 */
common.UserWin = function(callback) {
	var form = new common.UserForm(callback);
	var win = new Ext.Window({
		width : 700,
		height : 350,
		items : form.getForm()
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
				'deptId' : config.curNode
			});
			Ext.getDom('deptId').value = config.curNode;

		}
	}
}

/**
 * @param {Function}
 *            callback：操作成功后的回调函数
 * 
 */
common.UserForm = function(callback) {
	var comboxWithTree = new Ext.form.ComboBox({
		fieldLabel : "部门名称",
		hiddenName : 'deptName',
		store : new Ext.data.SimpleStore({
			fields : [],
			data : [[]]
		}),
		editable : false,
		mode : 'local',
		triggerAction : 'all',
		maxHeight : 200,
		tpl : "<tpl for='.'><div style='height:200px'><div id='deptTree'></div></div></tpl>",
		selectedClass : '',
		emptyText : '请选择',
		blankText : '请选择',
		allowBlank : false

	});
	var tree = new Ext.tree.TreePanel({
		loader : new Ext.tree.TreeLoader({
			dataUrl : Ext.getDom("root").value + userURL.getComboxTree
		}),
		border : false,
		root : new Ext.tree.AsyncTreeNode({
			text : '部门机构树',
			draggable : false,
			id : '-1'
		}),
		rootVisible : false
	});
	tree.on('click', function(node) {
		comboxWithTree.setValue(node.text);
		Ext.getDom("deptId").value = node.id;
		comboxWithTree.collapse();
	});
	comboxWithTree.on('expand', function() {
		tree.render('deptTree');
		tree.expandAll();
	});
//	var sexSelectDs = hs.StoreFactory.getSelectDs(hs.strutsUrl.DATADICT_COMMON_URL);
//	sexSelectDs.load({params : {typeStr : 'sex'}});
	var sexData = [['0', '女'],['1', '男']];
	var sexSelectDs = new Ext.data.SimpleStore({data: sexData,fields:["id","text"]});
	sexSelectDs.loadData(sexData);
	
	var form = new Ext.form.FormPanel({
		autoWidth: true,
		autoHeight: true,
		items : [{
					hidden : true,
					items : [new Ext.form.NumberField({
								id : 'id'
							}), new Ext.form.NumberField({
								id : 'deptId'
							})]
				}, {
					title : '用户信息',
					items : [
							hs.FormHelper.twoColumnedRow(
									new Ext.form.TextField({
										fieldLabel : '用户帐号',
										id : 'account',
										allowBlank : false
									}), new Ext.form.TextField({
										fieldLabel : '姓名',
										id : 'userName',
										allowBlank : false
									})),
							hs.FormHelper.twoColumnedRow(
									new Ext.form.TextField({
										fieldLabel : '身份证号码',
										id : 'idCard',
										vtype: 'IDCard',
                                        msgTarget: 'qtip'
									}),
									new Ext.form.ComboBox({
										fieldLabel : '性别',
										hiddenName : 'sex',
										displayField: 'text',
										store: sexSelectDs,
										allowBlank : false
									})),
							hs.FormHelper.twoColumnedRow(
									new Ext.form.TextField({
										fieldLabel : 'E-mail',
										vtype: 'email',
										msgTarget: 'qtip',
										id : 'email'
									}), new Ext.form.DateField({
										fieldLabel : '出生年月',
										id : 'birthday'
									})),
							hs.FormHelper.twoColumnedRow(
									new Ext.form.TextField({
										fieldLabel : '电话号码',
										id : 'phone',
                                        msgTarget: 'qtip',
                                        vtype:"telphone"
									}), new Ext.form.TextField({
										fieldLabel : '手机',
										id : 'mobile',
                                        msgTarget: 'qtip',
                                        vtype:"mobile"
									})),
							hs.FormHelper.twoColumnedRow(
									new Ext.form.TextField({
										fieldLabel : '住址',
										id : 'address'
									}), new Ext.form.TextField({
										fieldLabel : '邮编',
                                        vtype: 'postalcode',
                                        msgTarget: 'qtip',
										id : 'zipCode'
									})),
							hs.FormHelper.twoColumnedRow(comboxWithTree, new Ext.form.TextField({
										fieldLabel : '职位',
										id : 'post'
									})),new Ext.form.TextField({
										id : 'deptId',
										hiddenName : 'deptId',
										hidden : true,
										listeners : {
											'change' : {
												fn : function(o) {
													var idCard = String(o
															.getValue())
													var birthStr = hs.publicFun
															.changeToBirth(idCard);
													var sex = hs.publicFun
															.changeToSex(idCard);
													form.getForm().setValues({
														'birthday' : birthStr,
														'sex' : sex
													});
												}
											}
										}
									})]
				}]
	});
	form.addButton("保存", function() {
				hs.FormHelper.submit(userURL.saveUserURL,
						function(form, action) {
							hs.MessageHelper.succuss({
								callback : function() {
									hs.WindowHelper.hide(true, callback);
								}
							});
						}, null, form.getForm());
			}

	);
	form.addButton("关闭", function() {
				hs.WindowHelper.hide(true);
			});

	return {
		load : function(record) {
			if (record) {
				hs.FormHelper.load(userURL.getUserURL, {
							id : record.get("id")
						}, null, form.getForm());
				comboxWithTree.setValue(record.get("department.deptName"));
				Ext.getDom("deptId").value = record.get("deptId");
				Ext.getDom("account").value = record
						.get("userSecurity.account");
			}
		},
		getForm : function() {
			return form;
		}
	}
}

common.UserPanel = function(){
	var userBar = common.UserBar();
	var userGrid = common.UserGrid({
		userURL: userURL.queryUserURL,
		search: userBar.getUserFn()
	});
	userBar.init({grid: userGrid});
	var userPanel = new Ext.Panel({
		renderTo: Ext.getBody(),
     	layout: 'form',
     	items: [
     		userBar.getUserBar(),
     		userGrid.getUserGrid()
     	]
	});
	return userPanel;
}
