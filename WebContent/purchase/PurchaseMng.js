Ext.namespace("Purchase.manage");
/**
 * @param {} config
 */
Purchase.manage.MainPanel = function(config) {
	Purchase.manage.MainPanel.superclass.constructor.call(this, config);
	this.url.query = '/purchase/purchase!queryPaged.do';
	this.url.remove = '/purchase/purchase!delete.do';
	this.url.get = '/purchase/purchase!getPurchase.do';
	this.url.queryPaged = '/purchase/purchase!queryPaged.do';
	this.url.exportExcel ='/purchase/purchase!exportExcel.do';
};

Ext.extend(Purchase.manage.MainPanel,Common.manage.MainPanel,{
	getGridRecord : function(){
		return [{ name:"purchaseId"},
		        { name:"purchaseName"},
		        { name:"purchaseType"},
		        { name:"supplierId"},
		        { name:"supplierName"},
		        { name:"orderDate"},
		        { name:"planStoreinDate"},
		        { name:"realStoreinDate"},
		        { name:"status"},
		        { name:"totalAmount"},
		        { name:"officer"},
		        { name:"remark"},
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
			{header:"ID",dataIndex:'purchaseId',width:60,hidden:true},
			{header : "采购单名称",dataIndex : 'purchaseName',width : 120 },
			{header : "采购类别",dataIndex : 'purchaseType',width : 60 ,
				renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["PURCHASE_TYPE"]))},//：定向采购、临时采购
			{header : "供应商ID",dataIndex : 'supplierId',width : 60 ,hidden:true},
			{header : "供应商名称",dataIndex : 'supplierName',width : 120 },
			{header : "采购日期",dataIndex : 'orderDate',width : 60 },
			{header : "计划入库时间",dataIndex : 'planStoreinDate',width : 140 ,hidden:true},
			{header : "实际入库时间",dataIndex : 'realStoreinDate',width : 140 ,hidden:true},
			{header : "状态",dataIndex : 'status',width : 60 ,
				renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["PURCHASE_STATUS"]))},//：待采购、采购中、已入库、中止
			{header : "总金额",dataIndex : 'totalAmount',width : 60 },
			{header : "采购负责人",dataIndex : 'officer',width : 120 },
			{header : "备注",dataIndex : 'remark',width : 60,hidden:true },
			{header : "创建时间",dataIndex : 'gmtCreate',width : 60 ,hidden:true},
			{header : "创建人",dataIndex : 'creator',width : 60 ,hidden:true},
			{header : "创建人ID",dataIndex : 'creatorId',width : 60 ,hidden:true},
			{header : "更新时间",dataIndex : 'gmtModify',width : 60,hidden:true },
			{header : "更新人",dataIndex : 'modifier',width : 60 ,hidden:true},
			{header : "更新人ID",dataIndex : 'modifierId',width : 60 ,hidden:true}
		]);
		return cm;
	}
	/**
	 * 获取查询面板
	 */
	,getTopBar: function(){
		if(!this.tBar){
			var _this = this;
			this.purchaseNoField = new Ext.form.TextField({
				fieldLabel : '入库编号',
				name : 'purchaseNo',
				width: 140,scope:_this
				,listeners : {'specialkey' : {fn : function(field, e){_this.enterHander(_this,field, e);}}}
			});
			this.purchaseDateStartField = new Ext.form.DateField({  
	            name:"purchaseDateStart",  
	            editable:false,  
	            width:100,  
	            format:"Y-m-d",  
	            emptyText:"请选择日期..."  
	        });
			this.purchaseDateEndField = new Ext.form.DateField({  
	            name:"purchaseDateEnd",  
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
					       new Ext.form.Label({text : "采购编号："})
					      ,this.purchaseNoField 
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "开始日期："})
					      ,this.purchaseDateStartField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "截止日期："})
					      ,this.purchaseDateEndField
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
		return {purchaseNo: this.purchaseNoField.getValue()
				,purchaseDateStart: this.purchaseDateStartField.getValue()
				,purchaseDateEnd: this.purchaseDateEndField.getValue()};
	},
	/**
	 * 获取信息编辑窗口
	 */
	getCardWin : function(){
		var cardWin = new Purchase.card.Win({width : 680,height: 500,title:"采购管理",callbackClose:true});
		cardWin.setCallback(this.searchFn);
		cardWin.setMainPanel(this);
		this.setCardWin(cardWin);
		return this.cardWin;
	}
	,getTaskWin :function(){
		var cardWin = new PurchaseTask.card.Win({width : 680,height: 500,title:"采购任务",callbackClose:false});
		cardWin.setCallback(this.searchFn);
		cardWin.setMainPanel(this);
		this.taskcard=cardWin;
		return this.taskcard;
	}
	/**
	 * 扩展gridTbBar，包括添加新按钮到bar中
	 * 添加导出入库表单
	 */
	,getExtendGridTbBar:function(_this,tbbar){
		var exportItem={
			text : "导出采购单",
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
		var purchaseTask={
				text : "采购任务",
				iconCls : 'hs-button-add',
				scope : _this,
				handler : function() {
					this.getTaskWin().show();
					this.getTaskWin().load();
				}
			};
		tbbar.push('-',exportItem,'-',purchaseTask);
		return tbbar;
	}
});
