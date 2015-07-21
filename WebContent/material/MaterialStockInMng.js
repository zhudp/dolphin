Ext.namespace("MaterialStockIn.manage");
/**
 * 原料入库管理主面板
 * @param {} config
 */
MaterialStockIn.manage.MainPanel = function(config) {
	MaterialStockIn.manage.MainPanel.superclass.constructor.call(this, config);
	this.url.query = '/stockin/stockin!queryPaged.do';
	this.url.remove = '/stockin/stockin!delete.do';
	this.url.get = '/stockin/stockin!getMaterialStockIn.do';
	this.url.queryPaged = '/stockin/stockin!queryPaged.do';
	this.url.exportExcel = '/stockin/stockin!exportExcel.do';
};

Ext.extend(MaterialStockIn.manage.MainPanel,Common.manage.MainPanel,{
	/**
	 * grid Record 用户创建grid
	 */
	getGridRecord : function(){
		return [{ name:"stockInId"},
		        { name:"stockInNo"},
	       		{ name:"orderId"},
	    		{ name:"orderNo"},
	    		{ name:"supplierId"},
	    		{ name:"supplierName"},
	    		{ name:"supplierNo"},
	    		{ name:"purchaseDate"},
	    		{ name:"ordersDate"},
	    		{ name:"stockInDate"},
	    		{ name:"supplierOfficer"},
	    		{ name:"purchaseOfficer"},
	    		{ name:"remark"},
	    		{ name:"isDeleted"},
	    		{ name:"gmtCreate"},
	    		{ name:"creator"},
	    		{ name:"creatorId"},
	    		{ name:"gmtModify"},
	    		{ name:"modifier"},
	    		{ name:"modifierId"}
	    		];
	}
	,getGridCm : function(){
		var sm =new Ext.grid.CheckboxSelectionModel({singleSelect : true });
		var cm =new Ext.grid.ColumnModel([ 
			new Ext.grid.RowNumberer(),
			sm,
			{header:"ID",dataIndex:'stockInId',width:60,hidden:true},
			{header : "入库编号	",dataIndex : 'stockInNo',width : 100 },
			{header : "订单id",dataIndex : 'orderId',width : 60 ,hidden:true},
			{header : "订单编号",dataIndex : 'orderNo',width : 80 },
			{header : "供应商id",dataIndex : 'supplierId',width : 70 ,hidden:true},
			{header : "供应商名称",dataIndex : 'supplierName',width : 85 },
			{header : "供应商编号",dataIndex : 'supplierNo',width : 80 },
			{header : "采购日期",dataIndex : 'purchaseDate',width : 80 },
			{header : "下单日期",dataIndex : 'ordersDate',width : 80 },
			{header : "入库日期",dataIndex : 'stockInDate',width : 80 },
			{header : "供应商负责人",dataIndex : 'supplierOfficer',width : 80 },
			{header : "采购人",dataIndex : 'purchaseOfficer',width : 80 },
			{header : "备注",dataIndex : 'remark',width : 60 }
		]);
		return cm;
	}
	/**
	 * 获取查询面板
	 */
	,getTopBar: function(){
		if(!this.tBar){
			var _this = this;
			this.stockInNoField = new Ext.form.TextField({
				fieldLabel : '入库编号',
				name : 'stockInNo',
				width: 140,scope:_this
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
			});
			this.stockInDateStartField = new Ext.form.DateField({  
	            name:"stockInDateStart",  
	            editable:false,  
	            width:100,  
	            format:"Y-m-d",  
	            emptyText:"请选择日期..."  
	        });
			this.stockInDateEndField = new Ext.form.DateField({  
	            name:"stockInDateEnd",  
	            editable:false,
	            width:100,  
	            format:"Y-m-d",  
	            emptyText:"请选择日期..."  
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
					       new Ext.form.Label({text : "入库编号："})
					      ,this.stockInNoField 
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "开始日期："})
					      ,this.stockInDateStartField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "截止日期："})
					      ,this.stockInDateEndField
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
		return {stockInNo: this.stockInNoField.getValue()
				,stockInDateStart: this.stockInDateStartField.getValue()
				,stockInDateEnd: this.stockInDateEndField.getValue()};
	},
	/**
	 * 获取信息编辑窗口
	 */
	getCardWin : function(){
		var cardWin = new MaterialStockIn.card.Win({width : 680,height: 500,title:"原料出库管理",callbackClose:true});
		cardWin.setCallback(this.searchFn);
		cardWin.setMainPanel(this);
		this.setCardWin(cardWin);
		return this.cardWin;
	}
	/**
	 * 扩展gridTbBar，包括添加新按钮到bar中
	 * 添加导出入库表单
	 */
	,getExtendGridTbBar:function(_this,tbbar){
		var exportItem={
			text : "导出入库单",
			iconCls : 'icon_excel',
			scope : _this,
			handler : function() {
				var record = this.getSelected();
				if(!record){
					return;
				}
				var _this = this;
				var idParam = {};
				idParam[_this.recordIdName] = record.get(_this.recordIdName);
				hs.MessageHelper.confirm('确定要导出？', function() {
					// 参数
					var p = Ext.urlEncode(idParam);
					window.open(Ext.getDom('root').value+_this.url.exportExcel+'?'+p);
				});
			}
		};
		tbbar.push('-',exportItem);
		return tbbar;
	}
});
