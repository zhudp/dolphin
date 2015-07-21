var ProductChooser = function(config) {
	this.config = config==null?{}:config;
	this.config.queryUrl = '/product/product!queryPaged.do';
	this.config.record = [
		 {name:"productId"}
		,{name:"productNo"}
		,{name:"productName"}
		,{name:"productType"}
		,{name:"standard"}
		,{name:"unit"}
		,{name:"customId"}
		,{name:"customName"}
		,{name:"price"}
		,{name:"primeCost"}
		,{name:"status"}
		,{name:"productPicpath"}
		,{name:"remark"}
		,{name:"minStore"}
		,{name:"maxStore"}
		,{name:"gmtCreate"}
		,{name:"creator"}
		,{name:"creatorId"}
		,{name:"gmtModify"}
		,{name:"modifier"}
		,{name:"modifierId"}
	];
};
ProductChooser.prototype = {
	show : function(){
		if(!this.win){
			
			var searchBtn = new Ext.Button({
	    	  text : '查  询',
	    	  iconCls: 'hs-button-search',
	    	  handler : function() {
	    		  this.dataGrid.load({
	    			  customName : filterField.getValue(),
	    			  productName : filter2Field.getValue()
	    		  });
	    	  }
	        });
			
			var filterField = new Ext.form.TextField({
				fieldLabel: '客户名称',
				name : 'customName',
				width : 110,
				scope : this,
				listeners:{
					'specialkey' : {
						fn:function(field, e) {
							if (e.getKey() == e.ENTER) {
								searchBtn.handler.call(this);
							}
						},
						scope: this
					}
				}
			});
			var filter2Field = new Ext.form.TextField({
				fieldLabel: '产品名称',
				name : 'productName',
				width : 110,
				listeners:{
					'specialkey' : {
						fn:function(field, e) {
							if (e.getKey() == e.ENTER) {
								searchBtn.handler.call(this);
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
				      {text: '产品名称:'},filter2Field,
				      {
				    	  xtype : 'button',
				    	  text : '查  询',
				    	  iconCls: 'hs-button-search',
				    	  handler : function() {
				    		  this.dataGrid.load({
				    			  customName : filterField.getValue(),
				    			  productName : filter2Field.getValue()
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
		var records = this.dataGrid.getSelectionModel().getSelections();
		if (!records) {
		   hs.MessageHelper.info( {msg : '请选择记录后再进行操作！'});
		   return;
		}
		this.config.callBack(records);
		this.win.hide();
	},
	getDateGrid:function(){
		if(!this.dataGrid) {
			var gridStore = hs.StoreFactory.getStore(this.config.queryUrl, this.config.record);
			var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : this.config.singleSelect });
			this.dataGrid = new Ext.grid.GridPanel({
				region : "center",
				layout : 'fit',
				store : gridStore,
				border:false,
				cm: new Ext.grid.ColumnModel([ 
				     new Ext.grid.RowNumberer()
  					,sm
  					,{header:"产品ID",dataIndex:'productId',width:60,hidden:true,sortable:true}
					,{header:"所属客户ID",dataIndex:'customId',width:60,hidden:true}
					,{header:"所属客户",dataIndex:'customName',width:100,sortable:true}
					,{header:"产品编号",dataIndex:'productNo',width:86,sortable:true}
					,{header:"产品名称",dataIndex:'productName',width:90,sortable:true}
					,{header:"类型",dataIndex:'productType',width:80,hidden:true,sortable:true,
						renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["PRODUCT_TYPE"]))}
					,{header:"规格",dataIndex:'standard',width:100}
					,{header:"单位",dataIndex:'unit',width:50,align:'center',sortable:true}
					,{header:"单价",dataIndex:'price',width:80,renderer: 'cnMoney',align:'right',sortable:true}
					,{header:"成本价",dataIndex:'primeCost',width:60,renderer: 'cnMoney',align:'right',hidden:true,sortable:true}
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
