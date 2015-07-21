var MaterialChooser = function(config) {
	this.config = config==null?{}:config;
	this.config.queryUrl = '/material/material!queryPaged.do';
	this.scope = config.scope;
	this.config.record = [ 
	         		     { name:"materialId"},  
	        		     { name:"materialName"},
	        		   	 { name:"materialNo"},
	        			 { name:"standard"},
	        			 { name:"materialType"},
	        			 { name:"unit"},
	        			 { name:"weight"},
	        			 { name:"area"},
	        			 { name:"remark"},
	        			 { name:"minStore"},
	        			 { name:"maxStore"},
	        			 { name:"gmtCreate"},
	        			 { name:"creator"},
	        			 { name:"creatorId"},
	        			 { name:"gmtModify"},
	        			 { name:"modifier"},
	        			 { name:"modifierId"},
	        			 { name:"kind"},
	        			 { name:"price"}
	        			 ];
};
MaterialChooser.prototype = {
	show : function(){
		if(!this.win){
			var filterField = new Ext.form.TextField({
				fieldLabel: '原料名称',
				name : 'materialName',
				width : 110,
				listeners:{
					'specialkey' : {
						fn:function(field, e) {
							if (e.getKey() == e.ENTER) {
								this.dataGrid.load({
									materialName : filterField.getValue()
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
				      {text: '原料名称:'},filterField,
				      {
				    	  xtype : 'button',
				    	  text : '查  询',
				    	  iconCls: 'hs-button-search',
				    	  handler : function() {
				    		  this.dataGrid.load({
				    			  materialName : filterField.getValue()
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
  					 sm,
  					{header:"ID",dataIndex:'materialId',width:60,hidden:true},
  					{header : "原材料名称",dataIndex : 'materialName',width : 80 },
  					{header : "原材料编号",dataIndex : 'materialNo',width : 80 },
  					{header : "规格",dataIndex : 'standard',width : 60 },
  					{header : "物资分类",dataIndex : 'materialType',width : 80, 
  						renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_TYPE"]))},
  					{header : "物质性质",dataIndex : 'kind',width : 80, 
  							renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["MATERIAL_KIND"]))},
  					{header : "价格",dataIndex : 'price',width : 70 },
  					{header : "单位",dataIndex : 'unit',width : 60 },
  					{header : "单位重量",dataIndex : 'weight',width : 70 ,sortable:true},
  					{header : "单位面积",dataIndex : 'area',width : 70 }
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
