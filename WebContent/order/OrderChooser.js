var OrderChooser = function(config) {
	this.config = config==null?{}:config;
	this.config.queryUrl = '/order/order!queryPaged.do';
	this.scope = config.scope;
	this.config.record = [ 
	                 	 {name:"orderId"}
	                 	,{name:"orderNo"}
	                 	,{name:"orderName"}
	                 	,{name:"status"}
	                 	,{name:"customId"}
	                 	,{name:"customName"}
	                 	,{name:"planDeliveryDate"}
	                 	,{name:"deliveryAddress"}
	                 	,{name:"orderPrice"}
	                 	,{name:"officer"}
	                 	,{name:"remark"}
	                 	,{name:"gmtCreate"}
	                 	,{name:"creator"}
	                 	,{name:"creatorId"}
	                 	,{name:"gmtModify"}
	                 	,{name:"modifier"}
	                 	,{name:"modifierId"}
	                 	,{name:"customNo"}
	        			 ];
};
OrderChooser.prototype = {
	show : function(){
		if(!this.win){
			var filterField = new Ext.form.TextField({
				fieldLabel: '订单名称',
				name : 'orderName',
				width : 110,
				listeners:{
					'specialkey' : {
						fn:function(field, e) {
							if (e.getKey() == e.ENTER) {
								this.dataGrid.load({
									orderName : filterField.getValue()
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
				      {text: '订单名称:'},filterField,
				      {
				    	  xtype : 'button',
				    	  text : '查  询',
				    	  iconCls: 'hs-button-search',
				    	  handler : function() {
				    		  this.dataGrid.load({
				    			  orderName : filterField.getValue()
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
  					 sm
  					,{header:"订单ID",dataIndex:'orderId',width:60,hidden:true,sortable:true}
 					,{header:"订单编号",dataIndex:'orderNo',width:86,sortable:true}
 					,{header:"订单名称",dataIndex:'orderName',width:90,sortable:true}
 					,{header:"订单状态",dataIndex:'status',width:60,sortable:true,
 						renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["ORDER_STATUS"]))}
 					,{header:"客户ID",dataIndex:'customId',width:60,hidden:true}
 					,{header:"客户",dataIndex:'customName',width:100}
 					,{header:"发货日期",dataIndex:'planDeliveryDate',width:80}
 					,{header:"发货地址",dataIndex:'deliveryAddress',width:90,hidden:true}
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
