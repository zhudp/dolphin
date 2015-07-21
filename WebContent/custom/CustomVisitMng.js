Ext.namespace("custom.visit");

custom.visit.url = {
	 query : '/custom/custom!queryPagedVisit.do'
	,remove: '/custom/custom!removeVisit.do'
	
};

custom.visit.record = [
	  {name:"visitId"}
	 ,{name:"customId"}
	 ,{name:"customName"}
	 ,{name:"customNo"}
	 ,{name:"visitDate"}
	 ,{name:"visitContext"}
	 ,{name:"visitPeople"}
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
custom.visit.MainPanel = function(config) {
	this.config = config;
};

custom.visit.MainPanel.prototype = {
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
				fieldLabel : '客户'
				,name : 'customName'
				,width: 140
				,value:initCustomName
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
			this.customNoField = new Ext.form.TextField({
				fieldLabel : '客户编号'
				,name : 'customName'
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
			
			this.customTypeField = new Ext.form.ComboBox({
				fieldLabel : '客户类别'
				,hiddenName : 'use'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_TYPE"],true)
				,width: 80
			});
			this.customStatusField = new Ext.form.ComboBox({
				fieldLabel : '合作阶段'
				,hiddenName : 'use'
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
					      ,new Ext.form.Label({text : "客户编号："})
					      ,this.customNoField
//					      ,{xtype:'tbspacer',width:15}
//					      ,new Ext.form.Label({text : "客户类别："})
//					      ,this.customTypeField
//					      ,{xtype:'tbspacer',width:15}
//					      ,new Ext.form.Label({text : "合作阶段："})
//					      ,this.customStatusField
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
			,customNo: this.customNoField.getValue()
//			,customType: this.customTypeField.getValue()
//			,customStatus: this.customStatusField.getValue()
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
			var gridStore = hs.StoreFactory.getStore(custom.visit.url.query, custom.visit.record);
			var grid = new Ext.grid.GridPanel({
				region : "center",
				layout : 'fit',
                store: gridStore,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,sm
					,{header:"客户ID",dataIndex:'customId',width:60,hidden:true}
					,{header:"记录ID",dataIndex:'visitId',width:60,hidden:true}
					,{header:"客户编号",dataIndex:'customNo',width:70,sortable:true}
					,{header:"客户名称",dataIndex:'customName',width:120,sortable:true}
					,{header:"拜访日期",dataIndex:'visitDate',width:120,sortable:true}
					,{header:"内容",dataIndex:'visitContext',width:120,sortable:true}
					,{header:"执行人",dataIndex:'visitPeople',width:120,sortable:true}
					,{header:"创建时间",dataIndex:'gmtCreate',width:70,sortable:true}
					,{header:"创建人",dataIndex:'creator',width:70,sortable:true}
					,{header:"创建人ID",dataIndex:'creatorId',width:60,hidden:true}
					,{header:"更新时间",dataIndex:'gmtModify',width:70,sortable:true}
					,{header:"更新人",dataIndex:'modifier',width:70,sortable:true}
					,{header:"更新ID",dataIndex:'modifierId',width:60,hidden:true}
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
						text : "拜访详情",
						iconCls : 'hs-button-view',
						scope : this,
						handler : function() {
							_this.showCard();
						}
					},'-', {
						text : "添加拜访记录",
						id:'010101',
						iconCls : 'hs-button-add',
						scope : this,
						handler : function() {
							_this.showAddWin();
						}
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
										custom.visit.url.remove
										,{visitId: record.get('visitId')}
										,function(){_this.searchFn();}
								);
							});
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
		var cardWin = new custom.visit.card.Win({
			callback:function() {
				_this.searchFn();
			},
			callbackClose:false
		});
		cardWin.show(record.get('visitId'));
	}
	,showAddWin: function(){
		var _this = this;
		var cardWin = new custom.visit.card.Win({
			callback:function() {
				_this.searchFn();
			},
			callbackClose:true
		});
		cardWin.show();
	}
};