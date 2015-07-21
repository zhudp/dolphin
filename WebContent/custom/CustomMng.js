Ext.namespace("custom.manage");

custom.manage.url = {
	 query : '/custom/custom!queryPaged.do'
	,remove : '/custom/custom!remove.do'
	
};

custom.manage.record = [
	 {name:"customId"}
	,{name:"customName"}
	,{name:"shortName"}
	,{name:"customNo"}
	,{name:"customType"}
	,{name:"customStatus"}
	,{name:"customIndustry"}
	,{name:"shopNumber"}
	,{name:"shopNumberPlan"}
	,{name:"address"}
	,{name:"webSite"}
	,{name:"contacts1"}
	,{name:"contacts1Job"}
	,{name:"contacts1Phone1"}
	,{name:"contacts1Phone2"}
	,{name:"contacts1Remark"}
	,{name:"contacts2"}
	,{name:"contacts2Job"}
	,{name:"contacts2Phone1"}
	,{name:"contacts2Phone2"}
	,{name:"contacts2Remark"}
	,{name:"officer"}
	,{name:"salesman"}
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
custom.manage.MainPanel = function(config) {
	this.config = config;
};

custom.manage.MainPanel.prototype = {
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
			
			this.officerField = new Ext.form.TextField({
				fieldLabel : '客户经理'
				,name : 'officer'
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
			
			this.salesmanField = new Ext.form.TextField({
				fieldLabel : '业务员'
				,name : 'salesman'
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
				,hiddenName : 'customType'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_TYPE"],true)
				,width: 100
			});
			this.customStatusField = new Ext.form.ComboBox({
				fieldLabel : '合作阶段'
				,hiddenName : 'customStatus'
				,displayField: 'text'
				,store: hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_STATUS"],true)
				,width: 100
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
					      ,new Ext.form.Label({text : "客户编号："})
					      ,this.customNoField
					      ,{xtype:'tbspacer',width:15}
					      ,new Ext.form.Label({text : "客户类别："})
					      ,this.customTypeField
					      ,{xtype:'tbspacer',width:15}
					      ,{
				                text: '查询',
				                scale: 'medium',
				                rowspan: 2,
								iconCls: 'hs-button-search',
				                cls: 'x-btn-as-arrow',
				                handler:function() {
				                	_this.searchFn();
								}
				            },
				            new Ext.form.Label({text : "客户经理："})
						      ,this.officerField
						      ,{xtype:'tbspacer',width:15}
						      ,new Ext.form.Label({text : "　业务员："})
						      ,this.salesmanField
						      ,{xtype:'tbspacer',width:15}
						      ,new Ext.form.Label({text : "合作阶段："})
						      ,this.customStatusField
						      ,{xtype:'tbspacer',width:15}
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
			,customType: this.customTypeField.getValue()
			,customStatus: this.customStatusField.getValue()
			,officer: this.officerField.getValue()
			,salesman: this.salesmanField.getValue()
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
			var gridStore = hs.StoreFactory.getStore(custom.manage.url.query, custom.manage.record);
			var grid = new Ext.grid.GridPanel({
				region : "center",
				layout : 'fit',
                store: gridStore,
                cm: new Ext.grid.ColumnModel([ 
					new Ext.grid.RowNumberer()
					,sm
					,{header:"ID",dataIndex:'customId',width:60,hidden:true}
					,{header:"客户编号",dataIndex:'customNo',width:70,sortable:true}
					,{header:"客户名称",dataIndex:'customName',width:140,sortable:true}
					//,{header:"客户简称",dataIndex:'shortName',width:60,hidden:true}
					,{header:"客户类别",dataIndex:'customType',width:65,sortable:true,
						renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_TYPE"]))}
					,{header:"合作阶段",dataIndex:'customStatus',width:65,sortable:true,
						renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_STATUS"]))}
					,{header:"所属行业",dataIndex:'customIndustry',width:65,sortable:true,
						renderer: dolphin.resource.Util.render(hs.StoreFactory.getComboStore(comboJsonData["CUSTOM_INDUSTRY"]))}
					,{header:"店铺数量",dataIndex:'shopNumber',width:70,sortable:true,align:'right'}
					,{header:"计划开店数",dataIndex:'shopNumberPlan',width:78,hidden:true}
					,{header:"地址",dataIndex:'address',width:100,hidden:true}
					,{header:"网址",dataIndex:'webSite',width:100,hidden:true}
					,{header:"联系人",dataIndex:'contacts1',width:78}
					,{header:"职务",dataIndex:'contacts1Job',width:65,hidden:true}
					,{header:"电话",dataIndex:'contacts1Phone1',width:80}
					,{header:"电话2",dataIndex:'contacts1Phone2',width:80,hidden:true}
					,{header:"备注",dataIndex:'contacts1Remark',width:100,hidden:true}
					,{header:"客户经理",dataIndex:'officer',width:90}
					,{header:"业务员",dataIndex:'salesman',width:90}
					,{header:"备注",dataIndex:'remark',width:60,hidden:true}
					,{header:"创建时间",dataIndex:'gmtCreate',width:70,sortable:true,hidden:true}
					,{header:"创建人",dataIndex:'creator',width:70,sortable:true,hidden:true}
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
						text : "客户详情",
						iconCls : 'hs-button-view',
						scope : this,
						handler : function() {
							_this.showCard();
						}
					},'-', {
						text : "添加客户",
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
										custom.manage.url.remove
										,{customId: record.get('customId')}
										,function(){_this.searchFn();}
								);
							});
						}
					},'-', {
						text : "产品",
						iconCls : 'icon_form_view',
						scope : this,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							var customId = record.get('customId');
							var customName = record.get('customName');
							var customNo = record.get('customNo');
							openUrlInTab("product/productMng.do?customId="+customId+'&customName='+customName+'&customNo='+customNo,customName+"_产品信息");
						}
					},'-', {
						text : "订单",
						iconCls : 'icon_form_view',
						scope : this,
						handler : function() {
							var record = this.getSelected();
							if(!record){
								return;
							}
							var customId = record.get('customId');
							var customName = record.get('customName');
							var customNo = record.get('customNo');
							openUrlInTab("order/orderMng.do?customId="+customId+'&customName='+customName+'&customNo='+customNo,customName+"_订单信息");
						}
					},{
						text : "拜访",
						iconCls : 'icon_form_view',
						scope : this,
						handler : function() {
							// 查询企业人员
							var record = this.getSelected();
							if(!record){
								return;
							}
							var customId = record.get('customId');
							var customName = record.get('customName');
							openUrlInTab("custom/customVisitMng.do?customId="+customId+'&customName='+customName,customName+"_拜访记录");
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
							var fileListPanel = new file.list.MainPanel({objectType:'custom', objectId:record.get('customId')});
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
		var cardWin = new custom.card.Win({
			callback:function() {
				_this.searchFn();
			},
			callbackClose:false
		});
		cardWin.show(record.get('customId'));
	}
	,showAddWin: function(){
		var _this = this;
		var cardWin = new custom.card.Win({
			callback:function() {
				_this.searchFn();
			},
			callbackClose:true
		});
		cardWin.show();
	}
};