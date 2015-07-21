TreePanel = Ext.extend(Ext.tree.TreePanel, {
			animate : true,
			rootVisible : false,
			frame : true,
			autoScroll : true,
			maskDisabled : false,
			clearOnLoad : true,
			bodyStyle : 'background:white',
			split : true
		});
var RoleResourceAssignmentURL = {
	formURL : '/dolphin/role!get.do',
	treeURL : '/dolphin/role!roleResourceAssignEdit.do',
	saveRoleResource : '/dolphin/role!roleItemAssignSave.do'
};
var ResourceTreeView = function(config) {
	this.resourceTree;
	this.config = config;
	this.init();
};
ResourceTreeView.prototype = {
	init : function() {
		if (this.resourceTree) {
			return this.resourceTree;
		}
		var _treeLoader = new Ext.tree.TreeLoader({
					dataUrl : this.config.treeurl || RoleResourceAssignmentURL.treeURL,
					baseAttrs : {
						uiProvider : Ext.tree.TreeNodeWithCheckboxUI
					},
					baseParams : this.config.buildBaseParams
				});

		this.resourceTree = new TreePanel({
					title : this.config.treeTitle || '权限资源树',
					region:'west',
					split:true,
					checkModel : 'cascade',
					onlyLeafCheckable : false,
					width : this.config.treeWidth || 200,
					height : this.config.treeHeight || 420,
					loader : _treeLoader,
					root : new Ext.tree.AsyncTreeNode({
								checked : true
							})
				});
		this.resourceTree.getRootNode().expand(true);
	},
	renderTo : function(el) {
		if (!this.resourceTree) {
			this.init();
		}
		el = (el == 'undefined') ? hs.ELFactory.getTabPanelEL({
					id : Ext.id()
				}) : el;
		this.resourceTree.render(el);
	},
	getTree : function() {
		return this.resourceTree;
	},
	load : function(baseParams) {
		var treeLoader = this.resourceTree.getLoader();
		if (typeof baseParams != 'undefined') {
			treeLoader.baseParams = baseParams;
		}
		if (!treeLoader.isLoading()) {
			var me = this;
			treeLoader.load(this.resourceTree.root, function() {
						me.resourceTree.getRootNode().expand(true);
					});
		}
	},
	getChecked : function() {
		return this.resourceTree.getChecked();
	},
	getCheckedIds : function() {
		var nodeIdList = new Array();
		var checkedNodeList = this.getChecked();
		for (var i = 0, size = checkedNodeList.length; i < size; i++) {
			if (this.resourceTree.getRootNode() != checkedNodeList[i])
				nodeIdList.push(checkedNodeList[i].id);
		}
		return nodeIdList;
	}
};
var RoleForm = function(config) {
	this.config = config ? {} : config;
	this.form;
	this.init();
};
RoleForm.prototype = {
	init : function() {
		if (this.form)
			return this.form;

		var _roleInfoItems = [new Ext.form.TextField({
							fieldLabel : '名称',
							name : 'roleName',
							allowBlank : false,
							width : '50%'
						}), new Ext.form.ComboBox({
							fieldLabel : '状态',
							displayField : 'text',
							hiddenName : 'status',
							store : dolphin.resource.Util.getUserStatusComboStore(),
							allowBlank : false,
							width : '50%'
						}), new Ext.form.TextArea({
							fieldLabel : '描述',
							name : 'description',
							width : '100%'
						})];
		var _hidenItems = [new Ext.form.NumberField({
					id : 'id'
				})];
		this.form = new Ext.FormPanel({
			        frame : true,
					layout : 'form',
					border : false,
					labelWidth : 60,
					region:'center',
					items : [{
								hidden : true,
								items : _hidenItems
							}, {
								title : '功能角色信息',
								items : _roleInfoItems
							}]
				});
		return this.form;
	},
	load : function(params) {
		if (params.roleId) {
			var roleURL = (typeof params.formURL == 'undefined') ? RoleResourceAssignmentURL.formURL : params.formURL;
			hs.FormHelper.load(roleURL, {
						"id" : params.roleId
					},null,this.form.getForm());
		}
	},
	renderTo : function(el) {
		var _renderToEL = (typeof el == 'undefined') ? hs.ELFactory.getTabPanelEL({
					id : Ext.id()
				}) : el;
		this.form.render(_renderToEL);
	},
	submit : function(saveUrl) {
		hs.FormHelper.submit(saveUrl, function(form, action) {
					hs.MessageHelper.succuss({
								callback : function() {
									var record = Ext.util.JSON.decode(action.result.data);
								}
							});
				}, {}, this.form.getForm());
	},
	disableAll : function() {
		var items = this.form.getForm().items;
		items.each(function(f) {
					f.disable();
				});
	},
	getFieldValue : function(id) {
		if (this.form.getForm().findField(id)) {
			return this.form.getForm().findField(id).getValue();
		}
		return;
	},
	getForm : function() {
		return this.form;
	}
};
var RoleResourceAssignment = function(config) {
	this.config = config;
	this.form;
	this.tree;
	this.win;
	this.initDefault();
};
RoleResourceAssignment.prototype = {
	initDefault : function() {
		if (!this.tree)
			this.tree = new ResourceTreeView(this.config);
		if (!this.form)
			this.form = new RoleForm(this.config);
	},
	renderToElByWindow : function(otherConfig, el) {
		if (!this.win) {
			var _cfg = {
				title : this.config.winTitle || '权限资源分配',
				layout : 'border',
				width : this.config.winWidth || 600,
				height : this.config.winHeight || 500,
				closeAction : 'hide',
				border : true,
				items : [this.tree.getTree(), this.form.getForm()],
				buttons : [{
							id : 'ok_btn',
							text : '确定',
							handler : this.saveRoleResource,
							scope : this
						}, {
							id : 'cancel_btn',
							text : '取消',
							handler : function() {
								this.win.hide();
							},
							scope : this
						}]
			};
			Ext.apply(_cfg, otherConfig);
			this.win = new Ext.Window(_cfg);
		}
		if (typeof el == 'undefined') {
			this.win.show();
		} else {
			this.win.show(el);
		}
		return this.win;
	},
	loadByParams : function(params) {
		this.tree.load(params);
		this.form.load(params);
		this.form.disableAll();
	},
	renderToPage : function(otherConfig) {
	},
	saveRoleResource : function() {
		var me = this;
		var roleId = this.form.getFieldValue("id");
		var treeValues = this.tree.getCheckedIds();
		var callback = function() {
			me.win.hide();
		}
		hs.ActionHelper.request(RoleResourceAssignmentURL.saveRoleResource, {
					roleId : roleId,
					trees : (treeValues.toString())
				}, callback);
	},
	load : function() {

	},
	show : function() {

	}
};
