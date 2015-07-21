Ext.namespace("Supplier.manage");
/**
 * 供应商管理主面板
 * @param {} config
 */
Supplier.manage.MainPanel = function(config) {
	Supplier.manage.MainPanel.superclass.constructor.call(this, config);
	this.config = config;
	if(Ext.isObject(config)){
		if(Ext.isDefined(config.cardWin)){
			this.cardWin = config.cardWin;
		}
		if(Ext.isDefined(config.recordIdName)){
			this.recordIdName = config.recordIdName;
		}
	}
	if(!Ext.isDefined(this.url)){
		this.url={};
	}
	this.url.query = '/supplier/supplier!queryPaged.do';
	this.url.remove = '/supplier/supplier!delete.do';
	this.url.get = '/supplier/supplier!getSupplier.do';
	this.url.queryPaged = '/supplier/supplier!queryPaged.do';
	this.url.savesupplier = '/supplier/supplier!saveSupplier.do';
};

Ext.extend(Supplier.manage.MainPanel,Common.manage.MainPanel,{
	/**
	 * grid Record 用户创建grid
	 */
	getGridRecord : function(){
		return [{ name:"supplierId"},
		        { name:"supplierName"},
		        { name:"supplierNo"},
		        { name:"supplyRange"},
		        { name:"supplyStatus"},
		        { name:"bankAccount"},
		        { name:"accountName"},
		        { name:"depositBank"},
		        { name:"address"},
		        { name:"webSite"},
		        { name:"contacts1"},
		        { name:"contacts1Job"},
		        { name:"contacts1Phone1"},
		        { name:"contacts1Phone2"},
		        { name:"contacts1Remark"},
		        { name:"contacts2"},
		        { name:"contacts2Job"},
		        { name:"contacts2Phone1"},
		        { name:"contacts2Phone2"},
		        { name:"contacts2Remark"},
		        { name:"officer"},
		        { name:"remark"},
		        { name:"isDeleted"},
		        { name:"gmtCreate"},
		        { name:"creator"},
		        { name:"creatorId"},
		        { name:"gmtModify"},
		        { name:"modifier"},
		        { name:"modifierId"}];
	}
	,getGridCm : function(){
		var sm =new Ext.grid.CheckboxSelectionModel({singleSelect : true });
		var cm =new Ext.grid.ColumnModel([ 
		 new Ext.grid.RowNumberer(),
		 sm,
		 {header : "供应商id",dataIndex : 'supplierId',width : 60 ,hidden:true},
		 {header : "供应商名称",dataIndex : 'supplierName',width : 100 },
		 {header : "供应商编号",dataIndex : 'supplierNo',width : 100 },
		 {header : "供应范围",dataIndex : 'supplyRange',width : 90 },
		 {header : "合作阶段",dataIndex : 'supplyStatus',width : 60 ,
			 renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["SUPPLIER_TYPE"]))},//价格谈判、正在合作、过期
		 {header : "银行账号",dataIndex : 'bankAccount',width : 60,hidden:true },
		 {header : "银行账号户名",dataIndex : 'accountName',width : 120 ,hidden:true},
		 {header : "开户行",dataIndex : 'depositBank',width : 60 ,hidden:true},
		 {header : "地址",dataIndex : 'address',width : 60 ,hidden:true},
		 {header : "网址",dataIndex : 'webSite',width : 60 ,hidden:true},
		 {header : "联系人",dataIndex : 'contacts1',width : 60 },
		 {header : "职务",dataIndex : 'contacts1Job',width : 60 },
		 {header : "电话",dataIndex : 'contacts1Phone1',width : 60 },
		 {header : "联系人电话2",dataIndex : 'contacts1Phone2',width : 60,hidden:true },
		 {header : "联系人备注",dataIndex : 'contacts1Remark',width : 60 ,hidden:true},
		 {header : "联系人2",dataIndex : 'contacts2',width : 60,hidden:true },
		 {header : "联系人2_职务",dataIndex : 'contacts2Job',width : 60 ,hidden:true},
		 {header : "联系人2_电话1",dataIndex : 'contacts2Phone1',width : 60 ,hidden:true},
		 {header : "联系人2_电话2",dataIndex : 'contacts2Phone2',width : 60,hidden:true },
		 {header : "联系人2_备注",dataIndex : 'contacts2Remark',width : 60,hidden:true },
		 {header : "客户经理",dataIndex : 'officer',width : 60 },
		 {header : "备注",dataIndex : 'remark',width : 60 ,hidden:true},
		 {header : "创建时间",dataIndex : 'gmtCreate',width : 60 ,hidden:true},
		 {header : "创建人",dataIndex : 'creator',width : 60 ,hidden:true},
		 {header : "创建人ID",dataIndex : 'creatorId',width : 60,hidden:true },
		 {header : "更新时间",dataIndex : 'gmtModify',width : 60,hidden:true },
		 {header : "修改人",dataIndex : 'modifier',width : 60 },
		 {header : "修改人编号",dataIndex : 'modifierId',width : 60,hidden:true }
		]);
		return cm;
	}
	/**
	 * 获取查询面板
	 */
	,getTopBar: function(){
		if(!this.tBar){
			var _this = this;
			this.supplierNameField = new Ext.form.TextField({
				fieldLabel : '供应商名称',
				name : 'supplierName',
				width: 140,scope:_this
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
			});
			this.supplierNoField = new Ext.form.TextField({
				fieldLabel : '供应商编码'
				,name : 'supplierNo'
				,width: 100
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
			});
			
			this.supplierStutsField = new Ext.form.ComboBox({
				fieldLabel : '供应商阶段'
				,hiddenName : 'use'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["SUPPLIER_TYPE"],true)
				,width: 80
			});
			var toolbar = new Ext.Panel({
				frame : false,
				border : false,
				region : "north",
				layout : 'fit',
				height: 34,
				tbar:[{
					xtype: 'buttongroup',
					columns: 13,
					labelAlign:'right',
					defaults: {
						labelAlign:'right',
						scale: 'small'
					},
					items:[
					       new Ext.form.Label({text : "供应商名称："})
					      ,this.supplierNameField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "供应商编号："})
					      ,this.supplierNoField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "供应商阶段："})
					      ,this.supplierStutsField
					      ,{xtype:'tbspacer',width:15}
					      ,{
				                text: '查询',
				                scale: 'medium',
								iconCls: 'hs-button-search',
				                handler:function() {
				                	_this.searchFn(_this);
								}
				            }
					]
				}]
			});
			
			this.tBar = toolbar;
		}
		return this.tBar;
	}
	/**
	 * 提供搜索条件
	 */
	,getSearchParm : function(){
		return {supplierName: this.supplierNameField.getValue()
				,supplierNo: this.supplierNoField.getValue()
				,supplierStuts: this.supplierStutsField.getValue()
				};
	},
	/**
	 * 获取信息编辑窗口
	 */
	getCardWin : function(){
		var cardWin = new Supplier.card.Win({width : 680,height: 550,title:"供应商管理",callbackClose:true});
		cardWin.setCallback(this.searchFn);
		cardWin.setMainPanel(this);
		this.setCardWin(cardWin);
		return this.cardWin;
	}
});
