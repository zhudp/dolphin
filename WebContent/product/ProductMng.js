Ext.namespace("product.manage");

product.manage.url = {
	 query : '/product/product!queryPaged.do'
	,remove : '/product/product!remove.do'
	,exportExcel:'/product/product!exportExcel.do'
	,printBarcode: '/product/product!printBarcode.do'
	
};

product.manage.record = [
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

/**
 * 主面板
 * @param {} config
 * @return {}
 */
product.manage.MainPanel = function(config) {
	this.config = config;
};

product.manage.MainPanel.prototype = {
	showViewPort: function(){
		new Ext.Viewport({
				layout : "border",
				margins : '0 0 0 0',
				defaults : {
					border : false
				},
				items : [this.getTopBar(), this.getCenterGrid()]
			});
		this.searchFn();
	}	
	,getTopBar: function(){
		if(!this.tBar){
			var _this = this;
			
			this.customNameField = new Ext.form.TextField({
				 fieldLabel : '客户名称'
				,name : 'customName'
				,value:initCustomName
				,width: 120
				,listeners : {
					'specialkey' : {
						fn : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								_this.searchFn();
							}
						}
					}
				}
			});
			this.productNameField = new Ext.form.TextField({
				fieldLabel : '产品名称'
					,name : 'productName'
						,width: 120
						,listeners : {
							'specialkey' : {
								fn : function(field, e) {
									if (e.getKey() == Ext.EventObject.ENTER) {
										_this.searchFn();
									}
								}
							}
						}
			});
			this.productNoField = new Ext.form.TextField({
				fieldLabel : '产品编号'
				,name : 'productName'
				,width: 100
				,listeners : {
					'specialkey' : {
							fn : function(field, e) {
								if (e.getKey() == Ext.EventObject.ENTER) {
									_this.searchFn();
								}
							}
						}
					}
			});
			
			this.productTypeField = new Ext.form.ComboBox({
				fieldLabel : '产品类别'
				,hiddenName : 'productType'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["PRODUCT_TYPE"],true)
				,width: 80
			});
			this.productStatusField = new Ext.form.ComboBox({
				fieldLabel : '产品状态'
				,hiddenName : 'productStatus'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_STATUS"],true)
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
					columns: 10,
					labelAlign:'right',
					defaults: {
						labelAlign:'right',
						scale: 'small'
					},
					items:[
					       new Ext.form.Label({text : "客户名称："})
					      ,this.customNameField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "产品名称："})
					      ,this.productNameField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "编号："})
					      ,this.productNoField
					      ,{xtype:'tbspacer',width:15}
//					      ,new Ext.form.Label({text : "产品类别："})
//					      ,this.productTypeField
//					      ,{xtype:'tbspacer',width:15}
					      ,{
				                text: '查询',
				                scale: 'medium',
				                //rowspan: 2,
								iconCls: 'hs-button-search',
				               // cls: 'x-btn-as-arrow',
				                handler:function() {
				                	_this.searchFn();
								}
				            }
					]
				}]
			});
			
			this.tBar = toolbar;
		}
		return this.tBar;
	}
	,searchFn: function(){
		var store = this.getCenterGrid().getStore();
		var paggingBar = this.getCenterGrid().getBottomToolbar();
		hs.StoreHelper.beforeload(store, {
			 customName: this.customNameField.getValue()
			,productName: this.productNameField.getValue()
			,productNo: this.productNoField.getValue()
			//,productType: this.productTypeField.getValue()
			,productStatus: this.productStatusField.getValue()
		});
		store.load({
			params : {
				start : 0,
				limit : paggingBar.pageSize || PAGE_SIZE
			}
		});
	}
	,getCenterGrid: function(){
		if (!this.mngGrid) {
			var _this = this;
			var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true });
			var gridStore = hs.StoreFactory.getStore(product.manage.url.query, product.manage.record);
			var grid = new Ext.grid.GridPanel({
				region : "center",
				layout : 'fit',
                store: gridStore,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,sm
					,{header:"产品ID",dataIndex:'productId',width:60,hidden:true,sortable:true}
					,{header:"所属客户ID",dataIndex:'customId',width:60,hidden:true}
					,{header:"所属客户",dataIndex:'customName',width:100,sortable:true}
					,{header:"产品编号",dataIndex:'productNo',width:110,sortable:true}
					,{header:"产品名称",dataIndex:'productName',width:90,sortable:true}
					,{header:"类型",dataIndex:'productType',width:80,hidden:true,sortable:true,
						renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["PRODUCT_TYPE"]))}
					,{header:"规格",dataIndex:'standard',width:100}
					,{header:"单位",dataIndex:'unit',width:50,align:'center',sortable:true}
					,{header:"报价",dataIndex:'price',width:80,renderer: 'cnMoney',align:'right',sortable:true}
					,{header:"成本价",dataIndex:'primeCost',width:60,renderer: 'cnMoney',align:'right',hidden:true,sortable:true}
//					,{header:"产品照片",dataIndex:'pictrueFullUrl',width:60,
//						renderer:function(v) {
//							if(v) {
//								return "<img src='"+v+"'</img>";
//							}
//							else {
//								return '';
//							}
//							
//						}}
					//,{header:"备注",dataIndex:'remark',width:60}
					,{header:"最小库存",dataIndex:'minStore',width:70,align:'right',sortable:true}
					//,{header:"最大库存量",dataIndex:'maxStore',width:60}
					,{header:"产品状态",dataIndex:'status',width:60,sortable:true,
						renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_STATUS"]))}
					,{header:"创建时间",dataIndex:'gmtCreate',width:70,sortable:true,hidden:true}
					,{header:"创建人",dataIndex:'creator',width:70,sortable:true,hidden:true}
					,{header:"创建人ID",dataIndex:'creatorId',width:60,hidden:true}
					,{header:"更新时间",dataIndex:'gmtModify',width:76,sortable:true}
					,{header:"更新人",dataIndex:'modifier',width:70,sortable:true}
					,{header:"更新人ID",dataIndex:'modifierId',width:60,hidden:true}
				]),
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
	        	tbar:[{
						text : "产品详情",
						iconCls : 'hs-button-view',
						scope : this,
						handler : function() {
							_this.showCard();
						}
					},{
						text : "添加产品",
						iconCls : 'hs-button-add',
						scope : this,
						handler : function() {
							_this.showAddWin();
						}
					},{
						text : "删除",
						iconCls : 'hs-button-remove',
						scope : this,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							var _this = this;
							hs.MessageHelper.confirm('确定要删除？', function() {
								hs.ActionHelper.request(
										product.manage.url.remove
										,{productId: record.get('productId')}
										,function(){_this.searchFn();}
								);
							});
						}
					}, '-', {
						text : "BOM清单",
						iconCls : 'icon-bill',
						scope : this,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							var bomGrid = this.getBomGrid();
							bomGrid.show(record.get('productId'),record.get('productName'),record.get('productNo'));
						}
					}, '-', {
						text : "导出Excel",
						iconCls : 'icon_excel',
						scope : this,
						handler : function() {
							var p = Ext.urlEncode(gridStore.baseParams);
							window.open(Ext.getDom('root').value+product.manage.url.exportExcel+'?'+p);
						}
					}
					, {
						text : "附件",
						iconCls : 'icon-attach',
						scope : this,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							var fileListPanel = new file.list.MainPanel({objectType:'product', objectId:record.get('productId')});
							fileListPanel.getWin().show();
						}
					}
//					,{
//						text : "打印条码",
//						iconCls : 'hs-button-print',
//						scope : this,
//						handler : function() {
//							var record = this.getSelected();
//							if(!record){
//								return;
//							}
//							
//							var productId = record.get('productId');
//							var productNo = record.get('productNo');
//							var productName = record.get('productName');
//							var standard = record.get('standard');
//							var printer = new Ext.ux.Printer({
//								title : '打印条码__'+productNo + "  "+productName+"("+standard+")",
//								width : 700,
//								height : 460,
//								url : product.manage.url.printBarcode,
//								param : {
//									productId : productId
//								},
//								callback : function() {
//									//do nothing
//								}
//							});
//							printer.show();
//						}
//					}
	        	],
		        load: function(baseParams) {
		        	hs.StoreHelper.beforeload(gridStore, baseParams);
					gridStore.load({
						params: {
							start: 0,
							limit: PAGE_SIZE
						}
					});
				}
            });
			grid.addListener('rowdblclick', function(){_this.showCard();});
			this.mngGrid = grid;
		}
		return this.mngGrid;
	}
	,getSelected: function(){
		var records = this.getCenterGrid().getSelectionModel().getSelections();
		if (!records || records.length == 0) {
			hs.MessageHelper.info({msg : '请选择记录后再进行操作！'});
			return;
		}
		if (records.length > 1) {
			hs.MessageHelper.info({msg : '请选择1条记录进行操作！'});
			return;
		}
		return records[0];
	}
	,showCard: function(){
		var record = this.getSelected();
		if(!record){
			return;
		}
		var _this = this;
		var cardWin = new product.card.Win({
			callback:function() {
				_this.searchFn();
			},
			callbackClose:false
		});
		cardWin.show(record.get('productId'));
	}
	,showAddWin: function(){
		var _this = this;
		var cardWin = new product.card.Win({
			callback:function() {
				_this.searchFn();
			},
			callbackClose:true
		});
		cardWin.show();
	}
	,getBomGrid: function() {
		this.bomGrid = new product.bom.MainPanel();
		return this.bomGrid;
	}
};