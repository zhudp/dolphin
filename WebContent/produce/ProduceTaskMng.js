Ext.namespace("produce.manage");

produce.manage.url = {
	 query:'/produce/produce!queryPaged.do'
	,remove:'/produce/produce!remove.do'
	
};

produce.manage.record = [
	 {name:"taskId"}
	 ,{name:"taskName"}
	 ,{name:"customId"}
	 ,{name:"customNo"}
	 ,{name:"customName"}
	 ,{name:"orderId"}
	 ,{name:"orderNo"}
	 ,{name:"planBeginDate"}
	 ,{name:"planEndDate"}
	 ,{name:"realBeginDate"}
	 ,{name:"realEndDate"}
	 ,{name:"storeInDate"}
	 ,{name:"taskStatus"}
	 ,{name:"officer"}
	 ,{name:"remark"}
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
produce.manage.MainPanel = function(config) {
	this.config = config;
};

produce.manage.MainPanel.prototype = {
	showViewPort: function(){
		new Ext.Viewport({
				layout:"border",
				margins:'0 0 0 0',
				defaults:{
					border:false
				},
				items:[this.getTopBar(), this.getCenterGrid()]
			});
		this.searchFn();
	}	
	,getTopBar: function(){
		if(!this.tBar){
			var _this = this;
			
			this.customNameField = new Ext.form.TextField({
				 fieldLabel:'客户名称'
				,name:'customName'
				,width: 120
				,listeners:{
					'specialkey':{
						fn:function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								_this.searchFn();
							}
						}
					}
				}
			});
			this.taskNameField = new Ext.form.TextField({
				fieldLabel:'任务名称'
					,name:'taskName'
						,width: 120
						,listeners:{
							'specialkey':{
								fn:function(field, e) {
									if (e.getKey() == Ext.EventObject.ENTER) {
										_this.searchFn();
									}
								}
							}
						}
			});
			
			this.taskStatusField = new Ext.form.ComboBox({
				fieldLabel:'任务状态'
				,hiddenName:'taskStatus'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["TASK_STATUS"],true)
				,width: 80
			});
			var toolbar = new Ext.Panel({
				frame:false,
				border:false,
				region:"north",
				layout:'fit',
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
					       new Ext.form.Label({text:"客户名称："})
					      ,this.customNameField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text:"任务名称："})
					      ,this.taskNameField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text:"任务状态："})
					      ,this.taskStatusField
					      ,{xtype:'tbspacer',width:15}
					      ,{
				                text: '查询',
				                scale: 'medium',
								iconCls: 'hs-button-search',
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
			,taskName: this.taskNameField.getValue()
			,taskStatus: this.taskStatusField.getValue()
		});
		store.load({
			params:{
				start:0,
				limit:paggingBar.pageSize || PAGE_SIZE
			}
		});
	}
	,getCenterGrid: function(){
		if (!this.mngGrid) {
			var _this = this;
			var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true });
			var gridStore = hs.StoreFactory.getStore(produce.manage.url.query, produce.manage.record);
			var grid = new Ext.grid.GridPanel({
				region:"center",
				layout:'fit',
                store: gridStore,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,sm
					,{header:"任务ID",dataIndex:'taskId',width:60,hidden:true,sortable:true}
					,{header:"订单ID",dataIndex:'orderId',width:60,hidden:true,sortable:true}
					,{header:"任务名称",dataIndex:'taskName',width:90 }
					,{header:"客户ID",dataIndex:'customId',width:60,hidden:true}
					,{header:"客户",dataIndex:'customName',width:100,sortable:true}
					,{header:"订单编号",dataIndex:'orderNo',width:96,sortable:true}
					,{header:"任务状态",dataIndex:'taskStatus',width:70,sortable:true,
						renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["TASK_STATUS"]))}
					,{header:"计划开始时间",dataIndex:'planBeginDate',width:80 }
					,{header:"计划结束时间",dataIndex:'planEndDate',width:80 }
					,{header:"实际开始时间",dataIndex:'realBeginDate',width:80 }
					,{header:"实际结束时间",dataIndex:'realEndDate',width:80 }
					,{header:"入库时间",dataIndex:'storeInDate',width:80 }
					,{header:"负责人",dataIndex:'officer',width:60}
					,{header:"创建时间",dataIndex:'gmtCreate',width:70,sortable:true}
					,{header:"创建人",dataIndex:'creator',width:70,sortable:true}
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
						text:"任务详情",
						iconCls:'hs-button-view',
						scope:this,
						handler:function() {
							_this.showCard();
						}
					},'-', {
						text:"添加生产任务",
						iconCls:'hs-button-add',
						scope:this,
						handler:function() {
							_this.showAddWin();
						}
					},'-', {
						text:"删除",
						iconCls:'hs-button-remove',
						scope:this,
						handler:function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							var _this = this;
							hs.MessageHelper.confirm('确定要删除？', function() {
								hs.ActionHelper.request(
										produce.manage.url.remove
										,{taskId: record.get('taskId')}
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
			hs.MessageHelper.info({msg:'请选择记录后再进行操作！'});
			return;
		}
		if (records.length > 1) {
			hs.MessageHelper.info({msg:'请选择1条记录进行操作！'});
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
		var cardWin = new produce.card.Win({
			callback:function() {
				_this.searchFn();
			},
			callbackClose:false
		});
		cardWin.show(record.get('taskId'));
	}
	,showAddWin: function(){
		var _this = this;
		var cardWin = new produce.card.Win({
			callback:function() {
				_this.searchFn();
			},
			callbackClose:true
		});
		cardWin.show();
	}
};