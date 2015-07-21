/*******************************************************************************
 * 部门信息管理模块 作者：changyw 日期：2008-06-26 修改历史：（修改人 修改日期 修改内容）
 * 
 * 
 ******************************************************************************/

/**
 * 部门记录集
 * 
 */
var departmentRecord = [{
			name : "id"
		}, {
			name : "deptName"
		}, {
			name : "remark"
		}, {
			name : "parentId"
		}, {
			name : "parentDepartment.name"
		}, {
			name : "deptManagerName"
		}, {
			name : "deptPhone"
		}, {
			name : "markCode"
		}, {
			name : "deptEmail"
		}, {
			name : "deptManager"
		}];
/**
 * 部门信息的链接
 * 
 */
var selectManage;
var DepartmentURL = {
	treeUrl : '/dolphin/department!departmentTreeList.do',
	formUrl : '/dolphin/department!queryCurDepartment.do',
	saveUrl : '/dolphin/department!save.do',
	delteDepartmentUrl : '/dolphin/department!delete.do',
	curParentTree : "/dolphin/department!departmentCurParnetTreeList.do",
	getDepartment : "/dolphin/department!get.do"
};
var Department = function(config) {
	this.config = config;

};
Department.prototype = {
	showViewPort : function() {
		if (!this.viewPort) {			
			var cfg = {
				layout : 'border',
				border : true,
				items : [this.getPanel()]
			};
			this.viewPort = new Ext.Viewport(cfg);
		}
		return this.viewPort;
	},
	/**
	 * 返回数据回调函数
	 */
	doCallback : function(node) {
		// alert(node.text);
	},
	getPanel:function(){
		if(!this.panel){
			this.getTree();
			this.getForm();
			this.getTbar();
			var pl = new Ext.Panel({
			   layout : 'border',
			   region:'center',
			   border : true,	
			   layoutConfig:"fit",
			   frame:true,
			   items : [this.tree, this.form],
			   tbar:this.tbar
			});
			this.panel =pl;
		}
		return this.panel;
	},
	/**
	 * 新增一个部门节点
	 * 
	 */
	addDepartment : function() {
		var selNode = this.tree.getSelectionModel().getSelectedNode();
		if (!selNode) {
			hs.MessageHelper.info({
						msg : "请选择部门节点,添加其部门子节点!"
					});
			return;
		}
		if (selNode.id == -1) {
			hs.MessageHelper.info({
						msg : "请先对当前的节点进行保存操作,然后再添加其部门子节点!"
					});
			return;
		}
		var parentDeptName = Ext.getDom('deptName').value;
		if (selNode) {
			selNode.expand(false, false);
			var node = selNode.appendChild(new Ext.tree.TreeNode({
						text : '<span style="color:red;">请输入部门名称 </span>',
						cls : 'album-node',
						isLeaf : true,
						allowDrag : true,
						allowDelete : true,
						allowEdit : true,
						allowChildren : true
					}));
			node.id = -1;
			this.tree.getSelectionModel().select(node);
			var tempForm = this.form.getForm();
			tempForm.clearInvalid();
			tempForm.setValues({
						parentId : selNode.id,
						parentName : parentDeptName,
						deptName : '',
						deptPhone : '',
						deptManagerName : '',
						remark : '',
						deptEmail : ''
					});
			tempForm.findField('deptName').focus(true);
		} else {
			hs.MessageHelper.alert('请选择一个部门节点！');
		}

	},
	/**
	 * 删除一个部门节点
	 * 
	 */
	deleteDepartment : function() {
		var tempForm = this.form.getForm();
		var selNode = this.tree.getSelectionModel().getSelectedNode();
		if (!selNode) {
			hs.MessageHelper.info({
						msg : "请选择要删除的部门节点!"
					});
			return;
		}
		if (selNode.id == '1') {
			hs.MessageHelper.info({
						msg : "该节点为部门信息的根节点不允许删除!"
					});
			return;
		}
		var aParentNode = selNode.parentNode;
		var aTree = this.tree;
		if (selNode) {
			if (selNode.id == -1) {
				selNode.remove();
			} else {
				hs.MessageHelper.confirm("确认删除所选部门吗？", function() {
							Ext.Ajax.request({
										url : Ext.getDom('root').value + DepartmentURL.delteDepartmentUrl,
										method : 'post',
										success : function(result, request) {
											if (Ext.util.JSON.decode(result.responseText).data != null
													&& Ext.util.JSON.decode(result.responseText).data != '') {
												Ext.Msg.alert("提示", Ext.util.JSON.decode(result.responseText).data);
											} else {
												Ext.Msg.alert("成功", '操作成功！');
												selNode.remove();
												var _params = Ext.apply({}, {
															formOrGrid : "form",
															id : aParentNode.id
														});
												hs.FormHelper.load(DepartmentURL.formUrl, _params, function(form, action) {
															var data = Ext.util.JSON.decode(action.result.data);
															form.clearInvalid();
															form.setValues(data);
														}, tempForm);
												aTree.getSelectionModel().select(aParentNode);
											}
										},
										failure : function(result, request) {
											Ext.Msg.alert("失败", Ext.util.JSON.decode(result.responseText).data);
										},
										params : {
											id : selNode.id
										}
									});
						});
			}
		} else {
			hs.MessageHelper.alert('请选择一个部门节点！');
		}
	},

	/**
	 * 保存修改的部门信息
	 * 
	 * 
	 */
	saveDepartment : function() {
		var selNode = this.tree.getSelectionModel().getSelectedNode();
		if (!selNode) {
			hs.MessageHelper.info({
						msg : "请选择部门节点！"
					});
			return;
		}
		if (selNode.id == '1') {
			hs.MessageHelper.info({
						msg : "该节点不允许操作！"
					});
			return;
		}

		if (selNode.id == -1) {
			if (selNode.parentNode.id == -1) {
				hs.MessageHelper.info({
							msg : "请先对父节点信息进行保存操作，然后再对该节点进行操作！"
						});
				return;
			}
		}
		var aTree = this.tree;
		var aParentNode = selNode.parentNode;
		Ext.getDom('id').value = selNode.id;
		hs.FormHelper.submit(DepartmentURL.saveUrl, function(form, action) {
					hs.MessageHelper.succuss({
								callback : function() {
									if (selNode.id == -1) {
										aTree.getLoader().load(aParentNode, function() {
													aTree.expandPath(Ext.getDom('root').value + DepartmentURL.treeUrl);
													aParentNode.expand();
													aTree.getSelectionModel().select(aParentNode);
												});
									}
								}
							});
				}, {}, this.form.getForm());
	},
	/*
     * 创建部门form
	*/
	makeForm : function(config) {
		
		var cfg={
			buttonAlign : 'left',
					frame : true,
					items : [{
								hidden : true,
								items : [new Ext.form.TextField({
													name : 'id',
													id : 'id'
												}), new Ext.form.TextField({
													name : 'parentId',
													id : 'parentId'
												})]
							}, {
								title : '部门信息',
								xtype : 'fieldset',
								layout : 'form',
								anchor: '98%',
								labelAlign : 'right',
								items : [new Ext.form.TextField({
													fieldLabel : '部门名称',
													id : 'deptName',
													name : 'deptName',
													allowBlank : false,
													width : 180,
													listeners : {
														'change' : {
															fn : function(o) {
																this.tree.getSelectionModel().getSelectedNode().setText(String(o.getValue()));
															},
															scope : this
														}
													}
												}), new Ext.form.TextField({
													fieldLabel : '机构代码',
													name : 'orgCode',
													width : 180
												}), new Ext.form.TextField({
													fieldLabel : '部门电话',
													name : 'tel',
													vtype : 'tel',
													allowBlank : true,
													width : 180
												}), new Ext.form.TextField({
													fieldLabel : '传真',
													name : 'fax',
													width : 180
												}), {
											xtype : 'numberfield',
											fieldLabel : '排序号',
											name : 'sortNum',
											id : 'sortNum',
											maxLength : 3,
											width : 100,
											maxLengthText : '超过数位限制,最大序号为999!',
											allowDecimals : false,
											allowBlank : false,
											value : '99'
										}, new Ext.form.TextField({
													fieldLabel : '地址',
													name : 'address',
													width : 350,
													hight : 10
												})]
							}]
			
		};
		Ext.apply(cfg, config);
		var form = new Ext.FormPanel(cfg);
		return form;
	},
	/**
	 * 刷新部门信息form详细信息
	 */

	resetFormDetail : function(node) {
		if (node.id != -1) {
			var _params = Ext.apply({}, {
						formOrGrid : "form",
						id : node.id
					});
			hs.FormHelper.load(this.config.formUrl, _params, function(form, action) {
						var data = Ext.util.JSON.decode(action.result.data);
						form.clearInvalid();
						form.setValues(data);
					}, this.form.getForm());
		}
	},

	/**
	 * 获取部门信息列表树
	 * 
	 */
	getTree : function(config) {
		if (!this.tree) {
			var cfg={
				title : '部门列表树',
						//bodyStyle : 'background:white',
						animate : true,
						region : 'west',
						split : true,
						frame : true,
						containerScroll : true,
						autoScroll : true,
						width : 225,
						loader : new Ext.tree.TreeLoader({
									dataUrl : this.config.treeUrl,
									// baseParams:{parentId:'0'},
									listeners : {
										'load' : {
											fn : function() {
												this.tree.root.firstChild.select();
												this.showDetails(this.tree.root.firstChild);
												this.tree.root.firstChild.expand();
											},
											scope : this,
											single : true
										}
									}
								}),
						
						rootVisible : false,
						listeners : {
							'click' : {
								fn : this.showDetails,
								scope : this
							},
							'dblclick' : {
								fn : this.doCallback,
								scope : this
							}
						},
						root : new Ext.tree.AsyncTreeNode({
									text : '部门机构设置',
									draggable : true,
									allowDrag : true,
									id : '0'
								})
			};
			
			Ext.apply(cfg,config);
			this.tree = new Ext.tree.TreePanel(cfg);
		};
	
		return this.tree;
	},
	
	/**
	 * 获取部门列表Form
	 */
	getForm : function() {
		if (!this.form) {

			var formCfg = {
				// bodyStyle: 'padding-bottom:15px;background:#eee;',
				region : 'center',
				
				frame: true,
				title : '',
				autoScroll : true
			};
			this.form = this.makeForm(formCfg);
		}
		return this.form;
	},

	showDetails : function(node) {
		if (node.id == -1) {
			this.form.getForm().clearInvalid();
			this.form.getForm().setValues({
						parentId : '',
						parentName : '',
						deptName : '',
						deptPhone : '',
						parentCode : '',
						deptType : '',
						remark : '',
						deptEmail : ''
					});
			this.form.getForm().findField('deptName').focus(true);

			if (node.parentNode.id != -1) {
				var deptDs = hs.StoreFactory.getSelectDs(DepartmentURL.getDepartment);
				deptDs.load({
							params : {
								id : node.parentNode.id
							},
							callback : function(r) {
								Ext.getDom('parentName').value = r[0].get('text');
								Ext.getDom('parentId').value = r[0].get('id');
							}
						});
			}
		}
		var detailEl = this.form.getEl();
		if (node) {
			this.resetFormDetail(node);
		} else {
			detailEl.update('');
		}
	},
	/**
	 * 获取部门经理信息
	 */
	selectUser : function() {
		var deptName;
		var parentId;
		var curNode;
		var selNode = this.tree.getSelectionModel().getSelectedNode();
		if (!selNode) {
			hs.MessageHelper.info({
						msg : "请选择部门节点！"
					});
			return;
		}

		if (selNode.parentNode.id == -1) {
			hs.MessageHelper.info({
						msg : "请先对父节点信息进行保存操作，然后再对该节点进行操作！"
					});
			return;
		}
		// 当前的节点为新增的节点，则找到他的父节点
		if (selNode.id == -1) {
			curNode = selNode.parentNode.id;
			parentId = curNode;
			deptName = Ext.getDom('parentName').value;

		} else {
			curNode = selNode.id;
			parentId = curNode;
			deptName = Ext.getDom('deptName').value;

		}
		if (!selectManage)
			selectManage = new selectManager();
		selectManage.showWin({
					curNode : parentId,
					deptName : deptName
				});
	},
	getTbar:function(){
	  if(!this.tbar){
		var tbar = new Ext.Toolbar({id:"tbar"}).add({
									text : '添加部门',
									iconCls : 'hs-button-add',
									id : '900501',
									handler : this.addDepartment,
									scope : this
								}, {
									text : '删除部门',
									id : '900503',
									iconCls : 'hs-button-remove',
									handler : this.deleteDepartment,
									scope : this
								},'-',
									{
								text : '保存',
								id : '900502',
								iconCls : 'icon_save',
								handler : this.saveDepartment,
								scope : this					
		
		});
	   this.tbar=tbar;
	  }
	  return this.tbar;
	}
		
	

};