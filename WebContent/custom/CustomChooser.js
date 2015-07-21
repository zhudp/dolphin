var CustomChooser = function(config) {
	this.config = config==null?{}:config;
	this.config.queryUrl = '/custom/custom!queryPaged.do';
	this.config.record = [ 
      	 {name:"customId"}
      	,{name:"customName"}
      	,{name:"shortName"}
      	,{name:"customNo"}
      	,{name:"customType"}
      	,{name:"customStatus"}
      	,{name:"customIndustry"}
      	,{name:"address"}
      	,{name:"webSite"}
      	,{name:"contacts1"}
      	,{name:"contacts1Job"}
      	,{name:"contacts1Phone1"}
      	,{name:"contacts1Phone2"}
      	,{name:"officer"}
      	,{name:"gmtCreate"}
      	,{name:"creator"}
      	,{name:"gmtModify"}
      	,{name:"modifier"}
     ];
};
CustomChooser.prototype = {
	show : function(){
		if(!this.win){
			var filterField = new Ext.form.TextField({
				fieldLabel: '客户名称',
				name : 'customName',
				width : 110,
				listeners:{
					'specialkey' : {
						fn:function(field, e) {
							if (e.getKey() == e.ENTER) {
								this.dataGrid.load({
									customName : filterField.getValue()
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
		this.config.callBack(record.get("customId"),record.get("customName"),record.get("customNo"));
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
				     new Ext.grid.RowNumberer()
  					,sm
				    ,{header:"ID",dataIndex:'customId',width:60,hidden:true}
				    ,{header:"客户编号",dataIndex:'customNo',width:70,sortable:true}
			        ,{header:"客户名称",dataIndex:'customName',width:120,sortable:true}
			        ,{header:"客户类别",dataIndex:'customType',width:65,sortable:true,
			        	renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_TYPE"]))}
			        ,{header:"合作阶段",dataIndex:'customStatus',width:65,sortable:true,
			        	renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_STATUS"]))}
			        ,{header:"所属行业",dataIndex:'customIndustry',width:65,sortable:true,hidden:true}
			        ,{header:"联系人",dataIndex:'contacts1',width:78}
			        ,{header:"电话",dataIndex:'contacts1Phone1',width:75}
			        ,{header:"客户经理",dataIndex:'officer',width:60}
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
