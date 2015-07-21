var SupplierChooser = function(config) {
	this.config = config==null?{}:config;
	this.config.queryUrl = '/supplier/supplier!queryPaged.do';
	this.scope = config.scope;
	this.config.record = [ 
		{ name:"supplierId"},
		{ name:"supplierName"},
		{ name:"supplierNo"},
		{ name:"supplyRange"},
		{ name:"supplyStatus"},
		{ name:"bankAccount"},
		{ name:"accountName"},
		{ name:"depositBank"},
		{ name:"address"},
		{ name:"officer"},
		{ name:"remark"}
     ];
};
SupplierChooser.prototype = {
	show : function(){
		if(!this.win){
			var filterField = new Ext.form.TextField({
				fieldLabel: '采购名称',
				name : 'supplierName',
				width : 110,
				listeners:{
					'specialkey' : {
						fn:function(field, e) {
							if (e.getKey() == e.ENTER) {
								this.dataGrid.load({
									supplierName : filterField.getValue()
								});
							}
						},
						scope: this
					}
				}
			});
			
		    this.win = new Ext.Window({
		    	title: '请选择...',
		    	frame : true,
				width : 600,
				height: 500,
				closeAction : 'hide',
				layout: 'border',
				items:[this.getDateGrid()],
				tbar:[
				      {text: '客户名称:'},filterField,
				      {
				    	  xtype : 'button',
				    	  text : '查  询',
				    	  iconCls: 'hs-button-search',
				    	  handler : function() {
				    		  this.dataGrid.load({
				    			  companyName : filterField.getValue()
				    		  });
				    	  },
				    	  scope : this
				      }
				 ],
				buttons: [
					{text: '确定',handler:this.doCallBack,scope: this},
				    {text: '取消',handler: function(){this.win.hide();},scope: this}
				]
			
		    });
		}
		this.win.show();
	},

	doCallBack:function(){
		var record = this.dataGrid.getSelectionModel().getSelected();
		if (!record) {
		   hs.MessageHelper.info( {msg : '请选择记录后再进行操作！'});
		   return;
		}
//		this.config.callBack(record.get("customId"),record.get("customName"),record.get("customNo"));
		this.config.callBack.call(this.scope || this,record);
		this.win.hide();
	},
	getDateGrid:function(){
		if(!this.dataGrid) {
			var gridStore = hs.StoreFactory.getStore(this.config.queryUrl, this.config.record);
			var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true });
			this.dataGrid = new Ext.grid.GridPanel({
				region : "center",
				layout : 'fit',
				store : gridStore,
				border:false,
				cm: new Ext.grid.ColumnModel([ 
  					 sm,
  					 {header : "供应商id",dataIndex : 'supplierId',width : 60 ,hidden:true},
  					 {header : "供应商名称",dataIndex : 'supplierName',width : 100 },
  					 {header : "供应商编号",dataIndex : 'supplierNo',width : 100 },
  					 {header : "供应范围",dataIndex : 'supplyRange',width : 90 },
  					 {header : "合作阶段",dataIndex : 'supplyStatus',width : 60 ,
  						 renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["SUPPLIER_TYPE"]))}//价格谈判、正在合作、过期
			     ]),
			     listeners:{
					'rowdblclick' : {fn:function(grid, rowIndex, columnIndex, e) {
						this.doCallBack();
						this.win.hide();
					},scope:this}
		         },
			     sm: sm,
			     enableColumnResize: true,
			     stripeRows:true,
			     trackMouseOver: true,
			     loadMask: {msg:'数据加载中，请稍候...'},
			     enableColLock: false,
			     bbar: new Ext.PagingToolbar({
			    	 pageSize: PAGE_SIZE,
			    	 store: gridStore,
			    	 displayInfo: true,
			    	 plugins: [new Ext.ux.plugins.PageComboResizer()]
			     }),
			     load:function(baseParams) {
			    	 hs.StoreHelper.beforeload(gridStore, baseParams);
			    	 gridStore.load( {
			    		 params : {start : 0,limit : PAGE_SIZE}
		    	 });
		      }
			});
		}
		
		return this.dataGrid;
	}
	
};
