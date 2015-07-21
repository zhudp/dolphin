Ext.namespace("MaterialStockOut.manage");
/**
 * @param {} config
 */
MaterialStockOut.manage.MainPanel = function(config) {
	MaterialStockOut.manage.MainPanel.superclass.constructor.call(this, config);
	this.url.query = '/stockout/stockout!queryPaged.do';
	this.url.remove = '/stockout/stockout!delete.do';
	this.url.get = '/stockout/stockout!getMaterialStockOut.do';
	this.url.queryPaged = '/stockout/stockout!queryPaged.do';
	this.url.exportExcel ='/stockout/stockout!exportExcel.do';
};

Ext.extend(MaterialStockOut.manage.MainPanel,Common.manage.MainPanel,{
	getGridRecord : function(){
		return [{ name:"stockOutId"},
		        { name:"stockOutNo"},
		        { name:"orderId"},
		        { name:"orderNo"},
		        { name:"departmentId"},
		        { name:"departmentName"},
		        { name:"officer"},
		        { name:"remark"},
		        { name:"stockOutDate"},
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
			{header:"ID",dataIndex:'stockOutId',width:60,hidden:true},
			{header : "出库编号",dataIndex : 'stockOutNo',width : 140 },
			{header : "订单Id",dataIndex : 'orderId',width : 60,hidden:true },
			{header : "订单编号",dataIndex : 'orderNo',width : 120 },
			{header : "部门id",dataIndex : 'departmentId',width : 60,hidden:true },
			{header : "部门",dataIndex : 'departmentName',width : 60 },
			{header : "负责人",dataIndex : 'officer',width : 60 },
			{header : "备注",dataIndex : 'remark',width : 60 },
			{header : "日期",dataIndex : 'stockOutDate',width : 100 },
			{header : "创建日期",dataIndex : 'gmtCreate',width : 60,hidden:true },
			{header : "创建人",dataIndex : 'creator',width : 60,hidden:true },
			{header : "修改日期",dataIndex : 'gmtModify',width : 60 ,hidden:true },
			{header : "修改人",dataIndex : 'modifier',width : 60,hidden:true }
		]);
		return cm;
	}
	/**
	 * 获取查询面板
	 */
	,getTopBar: function(){
		if(!this.tBar){
			var _this = this;
			this.stockOutNoField = new Ext.form.TextField({
				fieldLabel : '入库编号',
				name : 'stockOutNo',
				width: 140,scope:_this
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
			});
			this.stockOutDateStartField = new Ext.form.DateField({  
	            name:"stockOutDateStart",  
	            editable:false,  
	            width:100,  
	            format:"Y-m-d",  
	            emptyText:"请选择日期..."  
	        });
			this.stockOutDateEndField = new Ext.form.DateField({  
	            name:"stockOutDateEnd",  
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
					       new Ext.form.Label({text : "出库编号："})
					      ,this.stockOutNoField 
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "开始日期："})
					      ,this.stockOutDateStartField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "截止日期："})
					      ,this.stockOutDateEndField
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
		return {stockOutNo: this.stockOutNoField.getValue()
				,stockOutDateStart: this.stockOutDateStartField.getValue()
				,stockOutDateEnd: this.stockOutDateEndField.getValue()};
	},
	/**
	 * 获取信息编辑窗口
	 */
	getCardWin : function(){
		var cardWin = new MaterialStockOut.card.Win({width : 680,height: 500,title:"原料入库管理",callbackClose:true});
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
			text : "导出出库单",
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
					var p = Ext.urlEncode(idParam);
					window.open(Ext.getDom('root').value+_this.url.exportExcel+'?'+p);
				});
			}
		};
		tbbar.push('-',exportItem);
		return tbbar;
	}
});
