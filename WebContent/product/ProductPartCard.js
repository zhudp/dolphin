Ext.namespace("product.part.card");

product.part.card.url = {
	 get: '/product/product-part!getProductPart.do'
	,save: '/product/product-part!saveProductPart.do'
};

/**
 * 信息卡片
 * @param {} config
 * @return {}
 */
product.part.card.Win = function(config) {
	this.config = config;
};

product.part.card.Win.prototype = {
	getCardForm: function(){
		
		if(!this.form1){
			var _this = this;
			var defaultsCfg = {
				disabled:true,
				anchor: '95%'
			};
			var oneClmCfg = {
				disabled:true,
				anchor: '97%'
			};
			var productIdField = new Ext.form.Hidden({fieldLabel: '产品ID',name: 'productId',value:_this.config.productId});
			var partIdField = new Ext.form.Hidden({fieldLabel: '部件ID',name: 'partId'});
			var partNoField = new Ext.form.TextField({fieldLabel: '部件编号',name: 'partNo',emptyText:'自动编号',autoFill:true});
			var partNameField = new Ext.form.TextField({fieldLabel: '部件名称',name: 'partName',allowBlank: false});
			var standardField = new Ext.form.TextField({fieldLabel: '规格',name: 'standard',allowBlank: false});
			var primeCostField  = new Ext.form.NumberField({fieldLabel: '成本价',name: 'primeCost',value:0,allowBlank: false,minValue:0,maxValue:9999999,selectOnFocus:true});
			//var workCostField  = new Ext.form.NumberField({fieldLabel: '',name: 'workCost',value:0,allowBlank: false,minValue:0,maxValue:9999999,selectOnFocus:true});

			var remarkField = new Ext.form.TextArea({fieldLabel: '备注',name: 'remark',height:44});
			
			var departmentIdField = new QM.ui.TreeCombo({
		        clearCls: 'allow-float', 
				xtype : 'treecombo',
				name : "departmentId",
				hiddenName:'departmentId',
				fieldLabel : '加工车间',
				lazyInit : true,
				editable : false, // 如果树中结点不是一次全部加载请请此项设为false
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
			
			var form1 = new Ext.form.FormPanel({
				frame: true,
				labelAlign: 'right',
				region: "center",
				border: false,
				autoScroll: true,
	            defaults : {
					labelWidth : 60
				},
				items: [productIdField,partIdField
			        ,hs.FormLayout.twoColumnedRow(partNoField,partNameField,defaultsCfg)
			        ,hs.FormLayout.twoColumnedRow(departmentIdField, standardField,defaultsCfg)
			        ,hs.FormLayout.twoColumnedRow(primeCostField,null, defaultsCfg)
					,hs.FormLayout.wholeOneColumnedRow(remarkField,oneClmCfg)
		        ]
		     });
			this.form1 = form1;
		}
		return this.form1;
	}
	,getWin : function() {
		if (!this.win) {
			var _this = this;
			function eachItem(item,index,length) {
			    if (item.items && item.items.getCount() > 0) {
			       item.items.each(eachItem, this);
			    }
			    else if(!item.autoFill){
			    	if(item.isXType("textfield") || item.isXType("button") ||  item.isXType("searchfield")) {
			    		item.setDisabled(!item.disabled);
			    	}
			    }
			}
			
			var win = new Ext.Window({
				frame : true,
				width : 560,
				height: 200,
				closeAction : 'hide',
				layout: 'border',
				items:[this.getCardForm()],
		        tbar:[{
					text : "编辑",
					iconCls : 'hs-button-edit',
					scope : this,
					enableToggle:true,
					handler : function() {
						_this.getCardForm().items.each(eachItem, this);
						var saveBtn = win.getTopToolbar().get(1);
						saveBtn.setDisabled(!saveBtn.disabled);
					}
				},{
					text : "保存",
					iconCls : 'icon_save2',
					scope : this,
					disabled:true,
					handler : function() {
						hs.FormHelper.submit(product.part.card.url.save,
								function(form, action) {
									hs.MessageHelper.succuss({
										callback : function() {
											if(_this.config && _this.config.callback) {
												_this.config.callback();
											}
											if(_this.config && _this.config.callbackClose) {
												_this.getWin().hide();
											}
										}
									});
								},null, _this.getCardForm().getForm());
					}
				}
		              
		        ]
			});
			
			this.win = win;
		}
		return this.win;
	}
	,show: function(partId){
		this.getWin().show();
		this.getWin().setTitle("产品部件信息");
		//载入信息
		if(partId) {
			this.partId = partId;
			this.load();
		}
		// 新增
		else {
			this.getWin().getTopToolbar().get(0).toggle().handler();
		}
	}
	// 加载数据
	,load : function() {
		var _this = this;
		var formPanel = _this.getCardForm();
		hs.FormHelper.load(product.part.card.url.get, {
			partId: _this.partId
		}, function(form, action) {
			var data = action.result.data;
			form.clearInvalid();
			form.setValues(data);
			_this.getWin().setTitle("产品部件信息："+data.partNo+"__"+data.partName);
		}, formPanel.getForm());
	}
};

