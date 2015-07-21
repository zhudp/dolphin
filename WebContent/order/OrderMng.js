Ext.namespace("order.manage");

order.manage.url = {
	 query : '/order/order!queryPaged.do'
	,remove : '/order/order!remove.do'
	
};

order.manage.record = [
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

/**
 * 主面板
 * @param {} config
 * @return {}
 */
order.manage.MainPanel = function(config) {
	this.config = config;
};

order.manage.MainPanel.prototype = {
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
			this.orderNameField = new Ext.form.TextField({
				fieldLabel : '订单名称'
					,name : 'orderName'
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
			this.orderNoField = new Ext.form.TextField({
				fieldLabel : '订单编号'
				,name : 'orderName'
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
			
			this.orderTypeField = new Ext.form.ComboBox({
				fieldLabel : '订单类别'
				,hiddenName : 'orderType'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["PRODUCT_TYPE"],true)
				,width: 80
			});
			this.orderStatusField = new Ext.form.ComboBox({
				fieldLabel : '订单状态'
				,hiddenName : 'orderStatus'
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
					columns: 13,
					labelAlign:'right',
					defaults: {
						labelAlign:'right',
						scale: 'small'
					},
					items:[
					       new Ext.form.Label({text : "客户名称："})
					      ,this.customNameField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "订单名称："})
					      ,this.orderNameField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "编号："})
					      ,this.orderNoField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "订单类别："})
					      ,this.orderTypeField
					      ,{xtype:'tbspacer',width:15}
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
			,orderName: this.orderNameField.getValue()
			,orderNo: this.orderNoField.getValue()
			,orderType: this.orderTypeField.getValue()
			,orderStatus: this.orderStatusField.getValue()
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
			var gridStore = hs.StoreFactory.getStore(order.manage.url.query, order.manage.record);
			var grid = new Ext.grid.GridPanel({
				region : "center",
				layout : 'fit',
                store: gridStore,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,sm
					,{header:"订单ID",dataIndex:'orderId',width:60,hidden:true,sortable:true}
					,{header:"订单编号",dataIndex:'orderNo',width:120,sortable:true}
					,{header:"订单名称",dataIndex:'orderName',width:90,sortable:true}
					,{header:"订单状态",dataIndex:'status',width:60,sortable:true,
						renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["ORDER_STATUS"]))}
					,{header:"客户ID",dataIndex:'customId',width:60,hidden:true}
					,{header:"客户",dataIndex:'customName',width:90,sortable:true}
					,{header:"发货日期",dataIndex:'planDeliveryDate',width:80}
					,{header:"发货地址",dataIndex:'deliveryAddress',width:110}
					,{header:"订单总价",dataIndex:'orderPrice',width:80,renderer: 'cnMoney',align:'right',sortable:true}
					,{header:"经办人",dataIndex:'officer',width:60}
					//,{header:"备注",dataIndex:'remark',width:60}
					,{header:"创建时间",dataIndex:'gmtCreate',width:70,sortable:true,hidden:true}
					,{header:"创建人",dataIndex:'creator',width:70,sortable:true,hidden:true}
					,{header:"创建人ID",dataIndex:'creatorId',width:60,hidden:true}
					,{header:"更新时间",dataIndex:'gmtModify',width:70,sortable:true}
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
						text : "订单详情",
						iconCls : 'hs-button-view',
						scope : this,
						handler : function() {
							_this.showCard();
						}
					},'-', {
						text : "添加订单",
						iconCls : 'hs-button-add',
						scope : this,
						handler : function() {
							_this.showAddWin();
						}
//					},'-', {
//						text : "查看--",
//						iconCls : 'icon_form_view',
//						scope : this,
//						handler : function() {
//							// 查询企业人员
//							var record = this.getSelected();
//							if(!record){
//								return;
//							}
//							var orderId = record.get('orderId');
//							var orderName = record.get('orderName');
//							openUrlInTab("order/orderVisitMng.do?orderId="+orderId+'&orderName='+orderName,"订单拜访信息");
//						}
					},'-', {
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
										order.manage.url.remove
										,{orderId: record.get('orderId')}
										,function(){_this.searchFn();}
								);
							});
						}
					}, {
						text : "附件",
						iconCls : 'icon-attach',
						scope : this,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							var fileListPanel = new file.list.MainPanel({objectType:'order', objectId:record.get('orderId')});
							fileListPanel.getWin().show();
						}
					}
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
		var cardWin = new order.card.Win({
			callback:function() {
				_this.searchFn();
			},
			callbackClose:false
		});
		cardWin.show(record.get('orderId'));
	}
	,showAddWin: function(){
		var _this = this;
		var cardWin = new order.card.Win({
			callback:function() {
				_this.searchFn();
			},
			callbackClose:true
		});
		cardWin.show();
	}
};