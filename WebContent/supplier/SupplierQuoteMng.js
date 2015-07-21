Ext.namespace("SupplierQuote.manage");
/**
 * 供应商报价主面板
 * @param {} config
 */
SupplierQuote.manage.MainPanel = function(config) {
	SupplierQuote.manage.MainPanel.superclass.constructor.call(this, config);
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
	this.url.query = '/supplier/quote!queryPaged.do';
	this.url.remove = '/supplier/quote!delete.do';
	this.url.get = '/supplier/quote!getSupplierQuote.do';
	this.url.queryPaged = '/supplier/quote!queryPaged.do';
	this.url.saveQuote = '/quote/quote!saveSupplierQuote.do';
};

Ext.extend(SupplierQuote.manage.MainPanel,Common.manage.MainPanel,{
	/**
	 * grid Record 用户创建grid
	 */
	getGridRecord : function(){
		return [{ name:"quoteId"},
		        { name:"materialId"},
		        { name:"supplierId"},
		        { name:"materialNo"},
		        { name:"standard"},
		        { name:"unit"},
		        { name:"materialName"},
		        { name:"supplierName"},
		        { name:"price"},
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
		 {header : "原材料ID",dataIndex:'materialId',width:60,hidden:true},
		 {header : "原材料名称",dataIndex : 'materialName',width : 80 },
		 {header : "原材料编号",dataIndex : 'materialNo',width : 80 },
		 {header : "规格",dataIndex : 'standard',width : 60 },
		 {header : "单位",dataIndex : 'unit',width : 60 },
		 {header : "价格",dataIndex : 'price',width : 70 },
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
			this.materialNameField = new Ext.form.TextField({
				fieldLabel : '原料名称',
				name : 'materialName',
				width: 140,scope:_this
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
			});
			this.materialNoField = new Ext.form.TextField({
				fieldLabel : '原料编码'
				,name : 'materialNo'
				,width: 100
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
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
					      ,new Ext.form.Label({text : "原料名称："})
					      ,this.materialNameField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "原料编码："})
					      ,this.materialNoField
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
				,materialName: this.materialNameField.getValue()
				,materialNo: this.materialNoField.getValue()
				};
	},
	/**
	 * 获取信息编辑窗口
	 */
	getCardWin : function(){
		var cardWin = new SupplierQuote.card.Win({width : 580,height: 350,title:"供应商报价",callbackClose:true});
		cardWin.setCallback(this.searchFn);
		cardWin.setMainPanel(this);
		this.setCardWin(cardWin);
		return this.cardWin;
	}
});
